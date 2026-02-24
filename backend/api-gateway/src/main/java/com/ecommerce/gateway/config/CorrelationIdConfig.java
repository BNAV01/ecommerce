package com.ecommerce.gateway.config;

import com.ecommerce.common.correlation.CorrelationId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.WebFilter;

@Configuration
public class CorrelationIdConfig {

    @Bean
    public WebFilter correlationIdWebFilter() {
        return (exchange, chain) -> {
            String correlationId = CorrelationId.ensure(
                    exchange.getRequest().getHeaders().getFirst(CorrelationId.HEADER)
            );

            ServerHttpRequest request = exchange.getRequest().mutate()
                    .header(CorrelationId.HEADER, correlationId)
                    .build();

            exchange.getResponse().getHeaders().set(CorrelationId.HEADER, correlationId);
            return chain.filter(exchange.mutate().request(request).build());
        };
    }
}
