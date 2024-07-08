package stepdefinations;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.testng.AssertJUnit.assertEquals;


public class stepdefination extends Utils {

    RequestSpecification res;
    ResponseSpecification resspec;

    Response response;

    static String place_id;

    TestDataBuild data = new TestDataBuild();

    @Given("Add Place Payload with {string} {string} {string}")
    public void add_place_payload_with(String name, String language, String address)  throws IOException {
        res = RestAssured.given().spec(requestSpecification())
        .body(data.addPlacePayLoad(name,language,address));

    }
    @When("user calls {string} with {string} Http request")
    public void user_calls_with_post_http_request(String resource, String httpMethod) {
        // Write code here that turns the phrase above into concrete actions
        APIResources resourceAPI =APIResources.valueOf(resource);
        System.out.println(resourceAPI.getResource());

        resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        if(httpMethod.equalsIgnoreCase("POST"))
        response = res.when().post(resourceAPI.getResource());
        else if(httpMethod.equalsIgnoreCase("GET"))
        response = res.when().get(resourceAPI.getResource());

    }
    @Then("the API call got success with status code {int}")
    public void the_api_call_got_success_with_status_code(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody().toString());
        assertEquals(200,response.getStatusCode());
    }
    @Then("{string} in response body is {string}")
    public void in_response_body_is(String keyValue, String actualValue) {
        assertEquals(getJsonPath(response,keyValue),actualValue);
    }

    @Then("verify place id created maps to {string} using {string}")
    public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
        // Write code here that turns the phrase above into concrete actions
        place_id=getJsonPath(response,"place_id");
        res = RestAssured.given().spec(requestSpecification()).queryParam("place_id",place_id);
        user_calls_with_post_http_request(resource,"GET");
        String actualName=getJsonPath(response,"name");
        System.out.println("Actual name is " + actualName  + "  " + "And expected name is" + "  "+expectedName);
        assertEquals(expectedName,actualName);
    }

    @Given("DeletePlace Paylod")
    public void delete_place_paylod() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        res = RestAssured.given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));

    }

}
