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

  private static final String BASE_PATH = "/api/franchise";

  @Bean
  public RouterFunction<ServerResponse> franchiseRouterFunction(FranchiseHandler handler) {
    return route(POST(BASE_PATH.concat("/save")), handler::saveFranchise)
        .andRoute(PUT(BASE_PATH.concat("/update")), handler::updateFranchise);
  }
}
