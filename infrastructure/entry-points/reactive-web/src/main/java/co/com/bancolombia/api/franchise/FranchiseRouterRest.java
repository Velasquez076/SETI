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

  private static final String PATH = "/api/franchise";

  @Bean
  public RouterFunction<ServerResponse> franchiseRouterFunction(FranchiseHandler handler) {
    return route(POST(PATH), handler::saveFranchise)
        .andRoute(PUT(PATH), handler::updateFranchise);
  }
}
