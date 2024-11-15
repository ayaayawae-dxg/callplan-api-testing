package com.callplan.testing.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.util.Map;
import static org.junit.Assert.*;

public class CommonSteps {
  private Response response;
  private RequestSpecification request;

  @Before
  public void setup() throws IOException {
    new TestContext();
    this.request = TestContext.getRequest();
    this.response = TestContext.getResponse();
  }

  @When("I send GET request to {string} with payload:")
  public void iSendGETRequestToWithPayload(String endpoint, DataTable dataTable) {
    Map<String, String> queryParams = dataTable.asMap(String.class, String.class);
    response = request
      .queryParams(queryParams)
      .get(endpoint);
    TestContext.setResponse(response);
  }

  @When("I send POST request to {string} with payload:")
  public void iSendPOSTRequestToWithPayload(String endpoint, DataTable dataTable) {
    Map<String, String> payload = dataTable.asMap(String.class, String.class);
    response = request
      .body(payload)
      .post(endpoint);
    TestContext.setResponse(response);
  }

  @Then("the response status code should be {int}")
  public void theResponseStatusCodeShouldBe(final int statusCode) {
    assertEquals(statusCode, response.getStatusCode());
  }

  @And("the response should contain {string} with value {string}")
  public void theResponseShouldContainWithValue(String field, String value) {
    assertEquals(value, response.jsonPath().getString(field));
  }

  @And("the response should contain error message")
  public void theResponseShouldContainErrorMessage() {
    assertNotNull(response.jsonPath().getString("message"));
  }
}
