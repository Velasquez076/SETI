package co.com.bancolombia.api.branch;

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
    return route(POST(BASE_PATH.concat("/save")), handler::saveBranch);
//        .andRoute(POST("/api/usecase/otherpath"), handler::listenPOSTUseCase)
//        .and(route(GET("/api/otherusercase/path"), handler::listenGETOtherUseCase));
  }
}
