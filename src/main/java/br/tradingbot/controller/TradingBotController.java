package br.tradingbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.tradingbot.service.TradingBotService;

@Controller
public class TradingBotController {

    @Autowired
    private TradingBotService bot;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("capitalInicial",  bot.getCapitalInicial());
        model.addAttribute("capitalAtual",    bot.getCapitalAtual());
        model.addAttribute("lucroDia",        bot.getLucroDia());
        model.addAttribute("lucroPercentual", bot.getLucroPercentual());
        model.addAttribute("orders",          bot.getOrders());
        return "dashboard";
    }
}
