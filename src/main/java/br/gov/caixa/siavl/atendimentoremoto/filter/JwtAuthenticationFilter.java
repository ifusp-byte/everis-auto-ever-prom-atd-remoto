package br.gov.caixa.siavl.atendimentoremoto.filter;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

	private final TokenUtils tokenUtils;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, TokenUtils tokenUtils) {
		super(authenticationManager);
		this.tokenUtils = tokenUtils;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader("Authorization");

		if (header == null || !header.startsWith("Bearer ")) {
			chain.doFilter(request, response);
			return;
		}

		String token = header.replace("Bearer ", "");

		// Verifica a validade do certificado/token
		if (!tokenUtils.certificadoValido(token)) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(
					"{ \"error\": \"Acesso negado: Para acessar a Plataforma.CAIXA utilize o Certificado Digital.\" }");
			return;
		}

		// Extrai o username do token
		String username = tokenUtils.getMatriculaFromToken(token);
		Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,
				Collections.emptyList());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		chain.doFilter(request, response);
	}
}