package br.gov.caixa.siavl.atendimentoremoto.filter;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    private TokenUtils tokenUtils;

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

		//System.out.println("Válido: " + tokenUtils.certificadoValido(token));

        if (!tokenUtils.certificadoValido(token)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(
                    "{ \"error\": \"Acesso negado: Para acessar a Plataforma.CAIXA utilize o Certificado Digital.\" }");
            return;
        }

        String username = tokenUtils.getMatriculaFromToken(token);
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }
}
