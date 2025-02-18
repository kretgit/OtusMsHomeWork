package ru.otus.ms.userservice.security;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthMetricsFilter extends OncePerRequestFilter {

    private final Counter successAuthCounter;
    private final Counter failedAuthCounter;

    public AuthMetricsFilter(MeterRegistry registry) {
        this.successAuthCounter = Counter.builder("otus.auth.requests.success")
                .description("Number of successful authentication requests")
                .register(registry);

        this.failedAuthCounter = Counter.builder("otus.auth.requests.failed")
                .description("Number of failed authentication requests")
                .register(registry);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        filterChain.doFilter(request, response);
        if (response.getStatus() == HttpServletResponse.SC_UNAUTHORIZED) {
            failedAuthCounter.increment();
        } else {
            successAuthCounter.increment();
        }

    }
}
