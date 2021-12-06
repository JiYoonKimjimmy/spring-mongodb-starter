package me.jimmyberg.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class SwaggerConfig {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.OAS_30)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController::class.java))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("Mock Server API")
            .description(description())
            .build()
    }

    private fun description() = listOf(
        "## Kona Test 용 Mock Server Project",
        "- 외부 서비스 연동 필요한 API 의 고정된 요청/응답 테스트 데이터 등록하여 테스트 가능",
        "- 각 요청/응답 정보는 정해진 형식대로 JSON 파일 업로드하여 등록",
        "### Mock API 등록 방법",
        "1. **[JSON 형식]**에 맞는 `JSON` 정보 작성",
        "2. **Mock API 관리 > `POST /api/management API`** 의 `request` 부분에 작성한 `JSON` 정보를 **복사 & 붙여넣기**하여 요청",
        "#### [JSON 형식]",
        "```json",
        "[",
        "  {",
        "    \"url\": \"string\",",
        "    \"request\": {",
        "      \"key\": \"value\"",
        "    },",
        "    \"response\": {",
        "      \"key\": \"value\"",
        "    }",
        "  }",
        "]",
        "```"
    ).joinToString("\n")

}