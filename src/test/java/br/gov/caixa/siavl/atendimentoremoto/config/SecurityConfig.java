package br.gov.caixa.siavl.atendimentoremoto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;


@Configuration
@EnableWebSecurity
@SuppressWarnings("all")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private TokenUtils tokenUtils;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().permitAll());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
