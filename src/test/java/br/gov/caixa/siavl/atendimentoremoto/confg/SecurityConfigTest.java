package br.gov.caixa.siavl.atendimentoremoto.confg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.gov.caixa.siavl.atendimentoremoto.filter.JwtAuthenticationFilter;

@TestConfiguration
@SuppressWarnings("all")
public class SecurityConfigTest {

	@Lazy
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/v1/atendimento-remoto/**", "/actuator/**", "/v2/api-docs",
								"/configuration/ui/", "/swagger-resources/**", "/configuration/security/",
								"/webjars/**", "/swagger-ui/**", "/swagger-config/**", "/atendimentoremoto-contract/**",
								"/public/**", "/atendimentoremoto-contract.html", "/atendimentoremoto-contract")
						.permitAll().anyRequest().authenticated())

				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
	}

}
