package br.com.hd.integrationtests.swagger.v1;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;

import br.com.hd.configs.v1.TestConfigs;
import br.com.hd.integrationtests.testcontainers.v1.AbstractIntegrationTest;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext
public class SwaggerIntegrationTest extends AbstractIntegrationTest {

	@Test
	public void testDisplaySwaggerPage() {
		var content = given()
				.basePath("/swagger-ui/index.html")
				.port(TestConfigs.SERVER_PORT)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body().asString();
		
		assertTrue(content.contains("Swagger UI"));
	}
	
}
