package com.EcommerceProject.Config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme bearerScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("JWT Bearer Token");

        SecurityRequirement bearerRequirement = new SecurityRequirement()
                .addList("Bearer Authentication");

        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot Ecommerce API")
                        .version("1.0")
                        .description("This is a Spring Boot Project for Ecommerce")
                        .license(new License().name("Apache 2.0").url("http://embarkx.com"))
                        .contact(new Contact()
                                .name("Sipu Padhiari")
                                .email("sipupadhiari6146@gmail.com")
                                .url("https://github.com/sipu45"))
                )

                .externalDocs(new ExternalDocumentation()
                        .description("Project Documentation")
                        .url("https://emabarkx.com"))
                .components(new Components()
                .addSecuritySchemes("Bearer Authentication", bearerScheme))
                .addSecurityItem(bearerRequirement);
    }
}




// definition and explanation

//Swagger and OpenAPI solve one core problem: they auto-generate interactive, always-up-to-date API documentation
// directly from your Spring Boot code — so instead of manually writing docs (which go stale fast), your endpoints,
// request/response formats, and models document themselves.
//
//OpenAPI vs Swagger — quick clarification:
// OpenAPI Specification (OAS) = the actual standard/format (a JSON/YAML spec)
// that describes your API — paths, parameters, request bodies, response schemas, etc.


//Swagger = the toolset built around that spec —
// Swagger UI (the interactive webpage), Swagger Editor, Swagger Codegen.
// People often say "Swagger" loosely to mean the whole ecosystem, but technically Swagger implements* OpenAPI.
//
//Why you'd actually use it in a Spring Boot project:
//1. Auto-generated, interactive docs** — a webpage listing every endpoint,
// its parameters, and expected request/response shapes, generated straight from your controller code.
//2.Try-it-out testing — you can call your API directly from the browser (like Postman, but built into your app)without writing a separate test client.
//3.Frontend-backend collaboration — your React frontend dev(or you, wearing that hat) can see exactly what a `POST /api/products` expects and returns,
// without asking you or reading the controller code.
//4. Contract-first development — some teams write the OpenAPI YAML *first*, then generate server/client code from it.
