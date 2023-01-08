package com.votehaeduo.config;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.SslProvider;

import java.time.Duration;
import java.util.Collections;

@Slf4j
@Component
public class DefaultWebClientCustomizer implements WebClientCustomizer {
    private final Duration readTimeout = Duration.ofSeconds(5);
    private final Duration writeTimeout = Duration.ofSeconds(5);
    private final Duration connectionTimeout = Duration.ofSeconds(5);
    private final Duration sslHandshakeTimeout = Duration.ofSeconds(1);

    @Override
    public void customize(WebClient.Builder webClientBuilder) {
        webClientBuilder.defaultHeaders(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                }).exchangeStrategies(getExchangeStrategies())
                .filter(loggingRequestFilter())
                .filter(loggingResponseFilter());

        HttpClient httpClient = HttpClient.create()
                .secure(spec -> spec.sslContext(SslContextBuilder.forClient())
                        .defaultConfiguration(SslProvider.DefaultConfigurationType.TCP)
                        .handshakeTimeout(sslHandshakeTimeout)
                        .closeNotifyFlushTimeout(Duration.ofSeconds(1))
                        .closeNotifyReadTimeout(Duration.ofSeconds(1)))
                .tcpConfiguration(tcpClient ->
                        tcpClient.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) connectionTimeout.toMillis())
                                .doOnConnected(c1 -> c1
                                        .addHandlerLast(new ReadTimeoutHandler((int) readTimeout.getSeconds()))
                                        .addHandlerLast(new WriteTimeoutHandler((int) writeTimeout.getSeconds()))));
        webClientBuilder.clientConnector(new ReactorClientHttpConnector(httpClient));
    }

    private ExchangeStrategies getExchangeStrategies() {
        return ExchangeStrategies.builder()
                .codecs(clientDefaultCodecsConfigurer -> {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.findAndRegisterModules();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    mapper.registerModule(new JavaTimeModule());
                    clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(mapper, MediaType.APPLICATION_JSON));
                    clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(mapper, MediaType.APPLICATION_JSON));
                    clientDefaultCodecsConfigurer.defaultCodecs().maxInMemorySize(-1);
                })
                .build();
    }

    private ExchangeFilterFunction loggingRequestFilter() {
        return ExchangeFilterFunction.ofRequestProcessor(request -> {
            log.info("[{}] {} {} [[ headers={}, body={} ]]", request.logPrefix(), request.method(), request.url(), request.headers(), request.body());
            return Mono.just(request);
        });
    }

    private ExchangeFilterFunction loggingResponseFilter() {
        return ExchangeFilterFunction.ofResponseProcessor(response -> {
            log.info("[{}] httpStatus={}", response.logPrefix(), response.statusCode());
            return Mono.just(response);
        });
    }
}
