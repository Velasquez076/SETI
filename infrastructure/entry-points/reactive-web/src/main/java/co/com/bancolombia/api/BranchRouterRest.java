package co.com.bancolombia.api;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class BranchRouterRest {

  private static final String BASE_PATH = "/api/branch";
  @Bean
  public RouterFunction<ServerResponse> branchRouterFunction(BranchHandler handler) {
    return route(GET("/api/usecase/path"), handler::listenGETUseCase)
        .andRoute(POST("/api/usecase/otherpath"), handler::listenPOSTUseCase)
        .and(route(GET("/api/otherusercase/path"), handler::listenGETOtherUseCase));
  }
}
