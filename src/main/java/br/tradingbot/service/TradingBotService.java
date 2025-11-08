package br.tradingbot.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TradingBotService {

    @Autowired
    private BinanceService binanceService;

    @Value("${trading.symbol}")
    private String symbol;
    @Value("${trading.capital}")
    private BigDecimal capitalInicial;
    @Value("${trading.daily-target}")
    private BigDecimal metaDiaria;
    @Value("${trading.stop-loss}")
    private BigDecimal stopLoss;
    @Value("${trading.trail-percent}")
    private BigDecimal trailPercent;
    @Value("${trading.grid-levels}")
    private int gridLevels;

    private BigDecimal capitalAtual;
    private BigDecimal lucroDia;
    private BigDecimal positionQty = BigDecimal.ZERO;
    private BigDecimal lastBuyPrice;
    private final List<TradeOrder> orders = new CopyOnWriteArrayList<>();

    /** roda a cada 15s: checa se compra ou vende */
    @Scheduled(fixedDelay = 1_000)
    public void executar() {
        if (capitalAtual == null) {
            capitalAtual = capitalInicial;
            lucroDia = BigDecimal.ZERO;
        }

        BigDecimal price = binanceService.getPrecoAtual(symbol);

        // 1) meta diária
        if (lucroDia.compareTo(capitalInicial.multiply(metaDiaria)) >= 0) {
            System.out.println("Meta diária atingida.");
            return;
        }
        // 2) stop loss diário
        if (capitalInicial.subtract(capitalAtual)
                .compareTo(capitalInicial.multiply(stopLoss)) >= 0) {
            System.out.println("Stop loss atingido.");
            return;
        }

        // 3) se não tenho posição, compro R$50
        if (positionQty.compareTo(BigDecimal.ZERO) == 0) {
            BigDecimal rawQty = BigDecimal.valueOf(50)
                .divide(price, 8, RoundingMode.HALF_UP);
            binanceService.colocarOrdem(symbol, "BUY", rawQty, price);
            positionQty = rawQty;
            lastBuyPrice = price;
            orders.add(new TradeOrder(LocalDateTime.now(), "BUY", rawQty, price));
            System.out.println("Ordem de compra: " + rawQty + " a " + price);
            return;
        }

        // 4) se tenho posição e preço subiu + trailPercent => vende
        BigDecimal targetSellPrice = lastBuyPrice.multiply(
                BigDecimal.ONE.add(trailPercent));
        if (price.compareTo(targetSellPrice) >= 0) {
            binanceService.colocarOrdem(symbol, "SELL", positionQty, price);
            orders.add(new TradeOrder(LocalDateTime.now(), "SELL", positionQty, price));
            BigDecimal profit = positionQty.multiply(price.subtract(lastBuyPrice));
            lucroDia = lucroDia.add(profit);
            capitalAtual = capitalAtual.add(profit);
            System.out.println("Ordem de venda: " + positionQty + " a " + price +
                               " → lucro " + profit);
            // zera a posição
            positionQty = BigDecimal.ZERO;
            lastBuyPrice = null;
        }
    }

    /*
    /** reseta no início de cada minuto (cron “segundo minuto hora ...”) */
    /*
    @Scheduled(cron = "0 0/1 * * * *")
    public void resetarDia() {
        capitalAtual = capitalInicial;
        lucroDia = BigDecimal.ZERO;
        positionQty = BigDecimal.ZERO;
        lastBuyPrice = null;
        System.out.println("=== Início de novo dia: capital e lucro resetados ===");
    }
    */

    public BigDecimal getCapitalInicial() { return capitalInicial; }
    public BigDecimal getCapitalAtual()   { return capitalAtual != null ? capitalAtual : capitalInicial; }
    public BigDecimal getLucroDia()       { return lucroDia != null ? lucroDia : BigDecimal.ZERO; }

    /** lucroDia / capitalInicial * 100 */
    public BigDecimal getLucroPercentual() {
        if (capitalInicial == null || lucroDia == null) {
            return BigDecimal.ZERO;
        }
        return lucroDia
            .divide(capitalInicial, 4, RoundingMode.HALF_UP)
            .multiply(new BigDecimal("100"));
    }

    /** histórico imutável de ordens para exibir no Thymeleaf */
    public List<TradeOrder> getOrders() {
        return Collections.unmodifiableList(orders);
    }

    /** DTO simples para passar ao template */
    public record TradeOrder(LocalDateTime time, String side,
                             BigDecimal quantity, BigDecimal price) {}
}
