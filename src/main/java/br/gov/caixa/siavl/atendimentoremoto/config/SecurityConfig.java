package br.gov.caixa.siavl.atendimentoremoto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.gov.caixa.siavl.atendimentoremoto.filter.JwtAuthenticationFilter;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private TokenUtils tokenUtils;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .cors().and()
        .authorizeRequests()
        .antMatchers(
            "/v2/api-docs",
            "/configuration/ui/",
            "/swagger-resources/**",
            "/configuration/security/",
            "/webjars/**",
            "/swagger-ui/**",
            "/swagger-config/**",
            "/atendimentoremoto-contract/**",
            "/public/**",
            "/atendimentoremoto-contract.html",
            "/atendimentoremoto-contract")
        .permitAll()
        .anyRequest().authenticated()
        .and()
        .addFilterBefore(new JwtAuthenticationFilter(authenticationManagerBean(), tokenUtils),
            UsernamePasswordAuthenticationFilter.class)
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
