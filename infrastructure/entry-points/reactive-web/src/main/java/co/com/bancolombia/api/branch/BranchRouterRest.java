package co.com.bancolombia.api.branch;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class BranchRouterRest {

  @Bean
  public RouterFunction<ServerResponse> branchRouterFunction(BranchHandler handler) {
    return route(POST("/api/branch"), handler::saveBranch)
        .andRoute(PUT("/api/branch"), handler::updateBranch);
  }
}
