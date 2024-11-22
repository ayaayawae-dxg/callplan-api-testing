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
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

public class GetExistingCallRealization {
  private final RequestSpecification request;

  public GetExistingCallRealization() {
    this.request = TestContext.getRequest();
  }

  @When("I send POST request to get-existing-call-realization with payload:")
  public void iSendPOSTRequestToGetExistingCallRealizationWithPayload(String docString) throws JsonProcessingException {
    Map<String, Object> jsonPayload = new ObjectMapper().readValue(docString, Map.class);

    AllureUtils.attachJsonToAllure("Request Payload", jsonPayload);

    Response response = request
      .body(jsonPayload)
      .post("/get-existing-call-realization");
        
    TestContext.setResponse(response);
  }

  @And("the response should contain existing call realization data")
  public void theResponseShouldContainExistingCallRealizationData() {
    Response response = TestContext.getResponse();
    response.then().body("data.currentPeriod", hasSize(greaterThanOrEqualTo(0)),
        "data.nextPeriod", hasSize(greaterThanOrEqualTo(0)));
  }

  @And("each existing call realization data should match schema")
  public void eachExistingCallRealizationDataShouldMatchSchema() {
    Response response = TestContext.getResponse();
    ObjectMapper mapper = new ObjectMapper();
    try {
      String jsonArray = mapper.writeValueAsString(response.jsonPath().getMap("data"));
      assertThat(jsonArray, matchesJsonSchemaInClasspath("schemas/GetExistingCallRealization.json"));
    } catch (JsonProcessingException e) {
      fail("Failed to parse response data: " + e.getMessage());
    }
  }
}