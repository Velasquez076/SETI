package co.com.bancolombia.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api.path")
public record EndpointsPathsConfig(
    String franchise,
    String product,
    String branch) {

}
