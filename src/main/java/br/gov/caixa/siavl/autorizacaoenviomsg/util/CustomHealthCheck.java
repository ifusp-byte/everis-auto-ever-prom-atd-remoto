package br.gov.caixa.siavl.autorizacaoenviomsg.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("all")
public class CustomHealthCheck implements HealthIndicator {
	private static final Logger LOG = Logger.getLogger(CustomHealthCheck.class.getName());
	private boolean errorDetected = false;

	@Override
	public Health health() {
		if (errorDetected) {
			return Health.down().withDetail("Erro interno na aplicação", "Exceção capturada nos logs").build();

		}
		return Health.up().build();
	}

	public void reportError(Exception e) {
		errorDetected = true;
		LOG.log(Level.SEVERE, e.getMessage());
	}

}
