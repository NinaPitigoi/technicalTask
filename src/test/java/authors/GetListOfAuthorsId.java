package authors;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static assertions.CustomAssertions.assertResponseBody;
import static assertions.CustomAssertions.assertThatStatusCodeIs;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

public class GetListOfAuthorsId {
    private RequestSpecification requestSpecification;

    @Rule
    public WireMockRule getListOfAuthorsService = new WireMockRule();

    @Before
    public void createRequestSpecification(){
        requestSpecification = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(8080).
                build();
    }

    public void setupStubGetListOfAuthorsById(){
        configureFor("localhost", 8080);
        stubFor(get(urlEqualTo("/api/v1/Authors/2"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile("get-list-of-authors-by-id-response.json")));
    }

    @Test
    public void testGetListOfAuthorsById(){
        setupStubGetListOfAuthorsById();
        Response response =
                given()
                        .spec(requestSpecification)
                        .get("/api/v1/Authors/2");

        assertAll(
                () -> assertResponseBody("firstName", is("First Name 2"), response),
                () -> assertResponseBody("lastName", is("Last Name 2"), response ),
                () -> assertResponseBody("idBook", is(1), response ),
                () -> assertResponseBody("id", is(2), response ),
                () -> assertThatStatusCodeIs(response, is(200))
        );
    }
}
