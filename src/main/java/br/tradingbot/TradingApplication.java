package br.tradingbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@SuppressWarnings("all")
@PropertySource("classpath:application-app.properties")
public class TradingApplication {
	public static void main(String[] args) {
		SpringApplication.run(TradingApplication.class, args);
	}
}
