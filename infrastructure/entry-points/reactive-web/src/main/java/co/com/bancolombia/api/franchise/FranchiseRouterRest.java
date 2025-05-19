package co.com.bancolombia.api.franchise;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class FranchiseRouterRest {

  @Bean
  public RouterFunction<ServerResponse> franchiseRouterFunction(FranchiseHandler handler) {
    return route(POST("/api/franchise"), handler::saveFranchise)
        .andRoute(PUT("/api/franchise"), handler::updateFranchise);
  }
}