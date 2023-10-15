package management.sttock.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(@Value("v1.0") String appVersion) {

        Info info = new Info().title("STTOCK API Docs").version(appVersion)
                .description("일상용품 재고관리")
                .contact(new Contact().name("STTOCK").url("https://github.com/Team-Sttock").email("been0822@ajou.ac.kr"))
                .license(new License().name("Apache License Version 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"));

        Server apiServer = new Server().description("Server API").url("https://api.sttock.co.kr");
        Server localServer = new Server().description("Local API").url("http://localhost:8080");

        List<Server> servers = new ArrayList<>();
        servers.add(apiServer);
        servers.add(localServer);

        /** 인증 토큰 **/
        String jwtSchemeName = "jwtAuth";
        // API 요청헤더에 인증정보 포함
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        // SecuritySchemes 등록
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP) // HTTP 방식
                        .scheme("bearer")
                        .bearerFormat("JWT"))
                ;
        return new OpenAPI()
                .servers(servers)
                .addSecurityItem(securityRequirement)
                .components(components)
                .info(info);
    }
}
