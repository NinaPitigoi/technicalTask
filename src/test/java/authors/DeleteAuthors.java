package authors;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static assertions.CustomAssertions.assertResponseBody;
import static assertions.CustomAssertions.assertThatStatusCodeIs;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

public class DeleteAuthors {
    private RequestSpecification requestSpecification;

    @Rule
    public WireMockRule getListOfAuthorsService = new WireMockRule(1000);

    @Before
    public void createRequestSpecification() {
        requestSpecification = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(1000).
                build();
    }

    public void setupDeleteAuthors() {
        configureFor("localhost", 1000);
        stubFor(delete(urlEqualTo("/api/v1/Authors/5"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("Success")));
    }

    @Test
    public void testDeleteAuthors() {
        setupDeleteAuthors();

        given()
                .spec(requestSpecification)
                .when()
                .delete("/api/v1/Authors/5")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body(Matchers.equalTo("Success"));

    }
}
