package co.com.bancolombia.api.product;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.PATCH;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProductRouterRest {

  private static final String PATH = "/api/products";

  @Bean
  public RouterFunction<ServerResponse> routerFunction(ProductHandler handler) {
    return route(GET(PATH), handler::getProductsMaxStock)
        .andRoute(POST(PATH), handler::saveProduct)
        .andRoute(PATCH(PATH.concat("/name/{id}")), handler::updateProductName)
        .andRoute(DELETE(PATH.concat("/{id}")), handler::deleteProductById)
        .and(route(PATCH(PATH.concat("/stock/{id}")), handler::updateProductStock));
  }
}
