package com.fatec.glab.config.springdoc;

import com.fatec.glab.dto.error.ErrorResponseDTO;
import com.fatec.glab.dto.error.ValidationErrorResponseDTO;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.info.Info;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        Components components = new Components();

        ModelConverters.getInstance().read(ErrorResponseDTO.class).forEach(components::addSchemas);
        ModelConverters.getInstance().read(ValidationErrorResponseDTO.class).forEach(components::addSchemas);

        return new OpenAPI()
                .components(components)
                .info(new Info()
                        .title("GLAB API")
                        .version("1.0")
                        .description("API para gerenciamento de reservas de salas"));
    }

    @Bean
    public OpenApiCustomizer globalOpenApiCustomizer() {
        return openApi -> {

            // Garante que components não será null
            Components components = openApi.getComponents();
            if (components == null) {
                components = new Components();
                openApi.setComponents(components);
            }

            // Registra schemas dos DTOs de erro
            ModelConverters.getInstance().read(ErrorResponseDTO.class).forEach(components::addSchemas);
            ModelConverters.getInstance().read(ValidationErrorResponseDTO.class).forEach(components::addSchemas);

            openApi.getPaths().forEach((path, pathItem) -> {
                pathItem.readOperationsMap().forEach((httpMethod, operation) -> {

                    ApiResponses responses = operation.getResponses();

                    boolean isListGet =
                            httpMethod == io.swagger.v3.oas.models.PathItem.HttpMethod.GET
                                    && !path.matches(".+\\{.+\\}.*");

                    boolean isGetById =
                            httpMethod == io.swagger.v3.oas.models.PathItem.HttpMethod.GET
                                    && path.matches(".+\\{.+\\}.*");

                    // POST e PUT → erros padrão
                    if (httpMethod == io.swagger.v3.oas.models.PathItem.HttpMethod.POST ||
                            httpMethod == io.swagger.v3.oas.models.PathItem.HttpMethod.PUT) {

                        responses.addApiResponse("400",
                                createArrayResponse("Erro de validação", "ValidationErrorResponseDTO"));

                        responses.addApiResponse("404",
                                createObjectResponse("Recurso não encontrado", "ErrorResponseDTO"));

                        responses.addApiResponse("500",
                                createObjectResponse("Erro interno", "ErrorResponseDTO"));
                    }

                    // DELETE
                    if (httpMethod == io.swagger.v3.oas.models.PathItem.HttpMethod.DELETE) {
                        responses.addApiResponse("404",
                                createObjectResponse("Recurso não encontrado", "ErrorResponseDTO"));

                        responses.addApiResponse("500",
                                createObjectResponse("Erro interno", "ErrorResponseDTO"));
                    }

                    // GET de lista (sem ID)
                    if (isListGet) {
                        responses.remove("404"); // lista nunca retorna 404
                        responses.addApiResponse("500",
                                createObjectResponse("Erro interno", "ErrorResponseDTO"));
                    }

                    // GET com ID
                    if (isGetById) {
                        responses.addApiResponse("404",
                                createObjectResponse("Recurso não encontrado", "ErrorResponseDTO"));

                        responses.addApiResponse("500",
                                createObjectResponse("Erro interno", "ErrorResponseDTO"));
                    }

                    operation.getResponses().forEach((status, response) -> {

                        if (!status.equals("200") &&
                                !status.equals("201") &&
                                !status.equals("204"))
                            return;

                        if (response.getContent() != null &&
                                response.getContent().containsKey("*/*")) {

                            var schema = response.getContent().get("*/*").getSchema();

                            response.getContent().remove("*/*");

                            response.getContent().addMediaType(
                                    "application/json",
                                    new MediaType().schema(schema)
                            );
                        }
                    });
                });
            });
        };
    }

    private ApiResponse createObjectResponse(String desc, String schemaName) {
        return new ApiResponse()
                .description(desc)
                .content(new Content().addMediaType("application/json",
                        new MediaType().schema(
                                new Schema<>().$ref("#/components/schemas/" + schemaName)
                        )));
    }

    private ApiResponse createArrayResponse(String desc, String schemaName) {
        return new ApiResponse()
                .description(desc)
                .content(new Content().addMediaType("application/json",
                        new MediaType().schema(
                                new ArraySchema().items(
                                        new Schema<>().$ref("#/components/schemas/" + schemaName)
                                )
                        )));
    }

}
