package co.com.bancolombia.api.product;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.PATCH;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import co.com.bancolombia.api.config.EndpointsPathsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class ProductRouterRest {

  private final EndpointsPathsConfig basePath;

  @Bean
  public RouterFunction<ServerResponse> routerFunction(ProductHandler handler) {
    return route(GET(basePath.product()), handler::getProductsMaxStock)
        .andRoute(POST(basePath.product()), handler::saveProduct)
        .andRoute(PATCH(basePath.product().concat("/name/{id}")), handler::updateProductName)
        .andRoute(DELETE(basePath.product().concat("/{id}")), handler::deleteProductById)
        .and(route(PATCH(basePath.product().concat("/stock/{id}")), handler::updateProductStock));
  }
}
