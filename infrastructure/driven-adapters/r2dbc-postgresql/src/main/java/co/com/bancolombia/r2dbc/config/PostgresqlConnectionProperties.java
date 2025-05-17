package co.com.bancolombia.r2dbc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "postgres.r2dbc")
public record PostgresqlConnectionProperties(
    String host,
    Integer port,
    String database,
    String schema,
    String username,
    String password) {
}
