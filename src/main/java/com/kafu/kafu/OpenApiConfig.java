package com.kafu.kafu;

import io.swagger.v3.oas.models.security.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Value("${keycloak.auth-server-url}")
    String authServerUrl;
    @Value("${keycloak.realm}")
    String realm;
    @Value("${keycloak.client-id}")
    String clientId;

    private static final String OAUTH_SCHEME_NAME = "my_oAuth_security_schema";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().components(new Components()
                        .addSecuritySchemes(OAUTH_SCHEME_NAME, createOAuthScheme()))
                .addSecurityItem(new SecurityRequirement().addList(OAUTH_SCHEME_NAME))
                .info(new Info().title("kafu")
                        .description("kafu")
                        .version("1.0"));
    }

    private SecurityScheme createOAuthScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.OAUTH2)
                .flows(createOAuthFlows());
    }

    private OAuthFlows createOAuthFlows() {
        return new OAuthFlows().implicit(createAuthorizationCodeFlow());
    }

    private OAuthFlow createAuthorizationCodeFlow() {
        return new OAuthFlow()
                .authorizationUrl(authServerUrl + "/realms/" + realm + "/protocol/openid-connect/auth")
                .tokenUrl(authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token")
                .refreshUrl(authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token")
                .scopes(new Scopes()
                        .addString("read_access", "read data")
                        .addString("write_access", "modify data")
                );
    }
}
