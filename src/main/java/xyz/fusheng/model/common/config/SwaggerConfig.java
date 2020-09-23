/**
 * @FileName: SwaggerConfig
 * @Author: code-fusheng
 * @Date: 2020/4/29 10:21
 * Description: Swagger配置类
 */
package xyz.fusheng.model.common.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    // http://localhost:9999/doc.html
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("个人论坛接口API")
                .contact(new Contact("code-fusheng", "https:fusheng.xyz", "2561035977@qq.com"))
                .description("Api").version("1.0.1")
                .termsOfServiceUrl("sv").build();
    }
}
