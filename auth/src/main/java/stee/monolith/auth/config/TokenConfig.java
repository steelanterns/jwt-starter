package stee.monolith.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties( prefix="token" )
public record TokenConfig(Long tokenValidity) {
}
