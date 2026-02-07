package teste.autoflex.vitorcsouza.prodmanager.infra.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI prodManagerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ProdManager API")
                        .description("API for production and raw material management")
                        .version("1.0.0")
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Project repository on GitHub")
                        .url("https://github.com/Vitor-C-Souza")
                )
                .addServersItem(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local environment")
                )
                .components(
                        new Components().addSecuritySchemes(
                                "bearerAuth",
                                new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }

    @Bean
    public OpenApiCustomizer globalApiResponsesCustomizer() {
        return openApi -> openApi.getPaths().values()
                .forEach(pathItem -> pathItem.readOperations()
                        .forEach(operation -> {
                            ApiResponses responses = operation.getResponses();
                            responses.addApiResponse("200", createApiResponse("Success"));
                            responses.addApiResponse("201", createApiResponse("Resource created successfully"));
                            responses.addApiResponse("204", createApiResponse("Resource deleted successfully"));
                            responses.addApiResponse("400", createApiResponse("Bad request"));
                            responses.addApiResponse("404", createApiResponse("Resource not found"));
                            responses.addApiResponse("409", createApiResponse("Business rule violation"));
                            responses.addApiResponse("500", createApiResponse("Internal server error"));
                        })
                );
    }

    private ApiResponse createApiResponse(String description) {
        return new ApiResponse().description(description);
    }
}
