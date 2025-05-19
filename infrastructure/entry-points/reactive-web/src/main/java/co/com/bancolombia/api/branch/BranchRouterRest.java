package co.com.bancolombia.api.branch;

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
public class BranchRouterRest {

  private final EndpointsPathsConfig basePath;

  @Bean
  public RouterFunction<ServerResponse> branchRouterFunction(BranchHandler handler) {
    return route(POST(basePath.branch()), handler::saveBranch)
        .andRoute(PUT(basePath.branch()), handler::updateBranch);
  }
}
