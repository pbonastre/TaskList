package com.mimacom.taskList.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;


import java.net.URI;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class SwaggerWebFluxConfig {

    @Bean
    public RouterFunction<ServerResponse> indexRouter() {
        return route(GET("/"), req ->
                ServerResponse.temporaryRedirect(URI.create("swagger-ui.html")).build());

    }
}
