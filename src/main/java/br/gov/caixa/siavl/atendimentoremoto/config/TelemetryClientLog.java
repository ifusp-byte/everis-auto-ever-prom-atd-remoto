package br.gov.caixa.siavl.atendimentoremoto.config;

import com.microsoft.applicationinsights.TelemetryClient;

@SuppressWarnings("all")
public class TelemetryClientLog {

	public static void logErrorException(Exception ex) {
		TelemetryClient telemetryClient = new TelemetryClient();
		telemetryClient.trackEvent("Evento cCustomizado");
		telemetryClient.trackException(ex);
	}

	public static void logErrorException(Exception ex, String texto) {
		TelemetryClient telemetryClient = new TelemetryClient();
		telemetryClient.trackEvent(texto);
		telemetryClient.trackException(ex);
	}
}