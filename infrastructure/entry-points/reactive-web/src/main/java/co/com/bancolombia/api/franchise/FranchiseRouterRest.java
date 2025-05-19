package co.com.bancolombia.api.franchise;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import co.com.bancolombia.api.config.EndpointsPathsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class FranchiseRouterRest {

  private final EndpointsPathsConfig basePath;

  @Bean
  public RouterFunction<ServerResponse> franchiseRouterFunction(FranchiseHandler handler) {
    return route(POST(basePath.franchise()), handler::saveFranchise)
        .andRoute(PUT(basePath.franchise()), handler::updateFranchise);
  }
}