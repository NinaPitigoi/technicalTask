package authors;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.*;

import static assertions.CustomAssertions.assertResponseBody;
import static assertions.CustomAssertions.assertThatStatusCodeIs;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

public class GetListOfAuthorsIdBook {
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

    public void setupStubGetListOfAuthorsByIdBook(){
        configureFor("localhost", 8080);
        stubFor(get(urlEqualTo("/api/v1/Authors/authors/books/5"))
                        .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile("get-list-of-authors-by-idBook-response.json")));
    }

    @Test
    public void testGetListOfAuthorsByIdBook(){
        setupStubGetListOfAuthorsByIdBook();
        Response response =
                given()
                        .spec(requestSpecification)
                        .get("/api/v1/Authors/authors/books/5");

        assertAll(
                () -> assertResponseBody("firstName[0]", is("First Name 13"), response),
                () -> assertResponseBody("lastName[0]", is("Last Name 13"), response ),
                () -> assertResponseBody("idBook[0]", is(5), response ),
                () -> assertResponseBody("id[0]", is(13), response ),
                () -> assertResponseBody("firstName[1]", is("First Name 14"), response),
                () -> assertResponseBody("lastName[1]", is("Last Name 14"), response ),
                () -> assertResponseBody("idBook[1]", is(5), response ),
                () -> assertResponseBody("id[1]", is(14), response ),
                () -> assertThatStatusCodeIs(response, is(200))
        );
    }
}
