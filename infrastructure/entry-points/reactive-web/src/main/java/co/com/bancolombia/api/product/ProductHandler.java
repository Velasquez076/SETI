package co.com.bancolombia.api.product;

import static co.com.bancolombia.api.dto.WrapperResponse.buildResponse;

import co.com.bancolombia.api.dto.ProductDto;
import co.com.bancolombia.api.util.Messages;
import co.com.bancolombia.api.util.ResponseErrorHandler;
import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.products.Product;
import co.com.bancolombia.usecase.branchproductstock.BranchProductStockUseCase;
import co.com.bancolombia.usecase.products.ProductUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Log4j2
@Component
@RequiredArgsConstructor
public class ProductHandler extends ResponseErrorHandler {

  private final BranchProductStockUseCase branchProductStockUseCase;
  private final ProductUseCase productUseCase;

  public Mono<ServerResponse> saveProduct(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(ProductDto.class)
        .flatMap(productDto -> productUseCase.saveProduct(
                new Product(productDto.idBranch(), productDto.name(), productDto.price(),
                    productDto.stock()))
            .doOnNext(f -> log.info("Saved Product: id={}, name={}", f.getId(), f.getName()))
            .flatMap(product -> buildResponse(product, Messages.CREATE.getCode()))
            .doOnSuccess(sr -> log.info("Product saved successfully! {}", sr.statusCode()))
            .onErrorResume(this::errorHandler));
  }

  public Mono<ServerResponse> updateProductName(ServerRequest serverRequest) {
    String id = serverRequest.pathVariable("id");
    return serverRequest.bodyToMono(ProductDto.class)
        .flatMap(productDto -> productUseCase.updateProductName(
            new Product(Long.parseLong(id), productDto.name())))
        .doOnNext(f -> log.info("Updated ProductName: id={}, new name={}", f.getId(), f.getName()))
        .flatMap(product -> buildResponse(product, Messages.OK.getCode()))
        .doOnSuccess(sr -> log.info("Product updated successfully! {}", sr.statusCode()))
        .onErrorResume(this::errorHandler);
  }

  public Mono<ServerResponse> updateProductStock(ServerRequest serverRequest) {
    String id = serverRequest.pathVariable("id");
    return serverRequest.bodyToMono(ProductDto.class)
        .flatMap(productDto -> productUseCase.updateProductStock(
            new Product(Long.parseLong(id), productDto.stock())))
        .doOnNext(
            f -> log.info("Updated ProductStock: id={}, name={}, new stock={}", f.getId(),
                f.getName(),
                f.getStock()))
        .flatMap(product -> buildResponse(product, Messages.OK.getCode()))
        .doOnSuccess(sr -> log.info("Product Stock updated successfully! {}", sr.statusCode()))
        .onErrorResume(this::errorHandler);
  }

  public Mono<ServerResponse> deleteProductById(ServerRequest serverRequest) {
    String id = serverRequest.pathVariable("id");
    return productUseCase.deleteProductById(new Product(Long.parseLong(id)))
        .then(buildResponse("Deleted successfully!", Messages.OK.getCode()))
        .doOnSuccess(sr -> log.info("Product deleted successfully!"))
        .onErrorResume(this::errorHandler);
  }

  public Mono<ServerResponse> getProductsMaxStock(ServerRequest serverRequest) {
    var id = serverRequest.queryParam("id-franchise");
    assert id.orElse(null) != null;
    return branchProductStockUseCase.findTopStockProductsByFranchise(
            Long.parseLong(id.orElse(null)))
        .collectList()
        .flatMap(branchProductStock -> buildResponse(branchProductStock, Messages.OK.getCode()))
        .doOnNext(serverResponse -> log.info("Lis found"))
        .onErrorResume(this::errorHandler);
  }
}
