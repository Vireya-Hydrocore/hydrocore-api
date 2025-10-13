package org.example.hydrocore.security;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final StringRedisTemplate redisTemplate;
    private static final String EMAIL_HEADER = "X-User-Email";
    private static final String REDIS_TOKEN_PREFIX = "token:";

    public TokenAuthenticationFilter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String email = request.getHeader(EMAIL_HEADER);
        String authHeader = request.getHeader("Authorization");
        String tokenEnviado = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            tokenEnviado = authHeader.substring(7);
        }

        if (email != null && tokenEnviado != null) {

            String redisKey = REDIS_TOKEN_PREFIX + email;

            try {
                String tokenEsperado = redisTemplate.opsForValue().get(redisKey);

                if (tokenEsperado != null && tokenEsperado.equals(tokenEnviado)) {

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            Collections.emptyList()
                    );
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.info("TokenAuthenticationFilter: User authenticated via Email and Token comparison: " + email);

                } else {
                    logger.warn("TokenAuthenticationFilter: Token/Email invalid or expired: " + email);
                }
            } catch (Exception e) {
                logger.error("TokenAuthenticationFilter: Erro ao conectar/consultar o Redis. Mensagem: " + e.getMessage(), e);
            }
        } else {
            logger.warn("TokenAuthenticationFilter: Missing required headers (Authorization or " + EMAIL_HEADER + ")");
        }

        filterChain.doFilter(request, response);
    }
}