package com.callplan.testing.steps.api;

import com.callplan.testing.steps.TestContext;
import com.callplan.testing.utils.AllureUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.fail;

public class GetOutletSteps {
  private final RequestSpecification request;

  public GetOutletSteps() {
    this.request = TestContext.getRequest();
  }

  @When("I send POST request to get-outlet with payload:")
  public void iSendPOSTRequestToGetOutletWithPayload(String docString) throws JsonProcessingException {
    Map<String, Object> jsonPayload = new ObjectMapper().readValue(docString, Map.class);

    AllureUtils.attachJsonToAllure("Request Payload", jsonPayload);

    Response response = request
      .body(jsonPayload)
      .post("/get-outlet");
        
    TestContext.setResponse(response);
  }

  @And("the response should contain empty array of outlet data")
  public void theResponseShouldContainEmptyArrayOfOutletData() {
    Response response = TestContext.getResponse();
    response.then().body("data", hasSize(0));
  }

  @And("the response should contain array of outlet data")
  public void theResponseShouldContainArrayOfOutletData() {
    Response response = TestContext.getResponse();
    response.then().body("data", hasSize(greaterThan(0)));
  }

  @And("each outlet data should match schema")
  public void eachOutletDataShouldMatchSchema() {
    Response response = TestContext.getResponse();
    ObjectMapper mapper = new ObjectMapper();
    try {
      String jsonArray = mapper.writeValueAsString(response.jsonPath().getList("data"));
      assertThat(jsonArray, matchesJsonSchemaInClasspath("schemas/GetOutlet.json"));
    } catch (JsonProcessingException e) {
      fail("Failed to parse response data: " + e.getMessage());
    }
  }
}