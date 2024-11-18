package com.callplan.testing.steps.api;

import com.callplan.testing.steps.TestContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

public class GetBestTimeVisitSteps {
  private final Response response;

  public GetBestTimeVisitSteps() throws IOException {
    this.response = TestContext.getResponse();
  }

  @And("the response should contain array of best time visit data")
  public void theResponseShouldContainArrayOfBestTimeVisitData() {
    List<Map<String, Object>> bestTimeVisit = response.jsonPath().getList("data");
    assertNotNull("Best time visit data should not be null", bestTimeVisit);
    assertFalse("Best time visit should not be empty", bestTimeVisit.isEmpty());
  }

  @And("each best time visit data should match schema")
  public void eachBestTimeVisitDataShouldMatchSchema() {
     ObjectMapper mapper = new ObjectMapper();
     try {
       String jsonArray = mapper.writeValueAsString(response.jsonPath().getList("data"));
       assertThat(jsonArray, matchesJsonSchemaInClasspath("schemas/GetBestTimeVisit.json"));
     } catch (JsonProcessingException e) {
       fail("Failed to parse response data: " + e.getMessage());
     }
  }

  @And("each best time visit data should match the payload:")
  public void eachBestTimeVisitDataShouldMatchThePayload(Map<String, String> payload) {
    List<Map<String, Object>> bestTimeVisit = response.jsonPath().getList("data");
    String expectedRayonCode = payload.get("RAYON_CODE");
    Integer expectedPersonId = Integer.parseInt(payload.get("PERSON_ID"));
    String expectedPersonCode = payload.get("PERSON_CODE");
    boolean allMatch = true;
    StringBuilder errorMessages = new StringBuilder();

    for (Map<String, Object> visit : bestTimeVisit) {
      if (!expectedRayonCode.equals(visit.get("RAYON_CODE"))) {
        errorMessages.append("Expected RAYON_CODE to be ").append(expectedRayonCode).append(" but got ").append(visit.get("RAYON_CODE")).append("\n");
        allMatch = false;
      }
      if (!expectedPersonId.equals(visit.get("PERSON_ID"))) {
        errorMessages.append("Expected PERSON_ID to be ").append(expectedPersonId).append(" but got ").append(visit.get("PERSON_ID")).append("\n");
        allMatch = false;
      }
      if (!expectedPersonCode.equals(visit.get("PERSON_CODE"))) {
        errorMessages.append("Expected PERSON_CODE to be ").append(expectedPersonCode).append(" but got ").append(visit.get("PERSON_CODE")).append("\n");
        allMatch = false;
      }
    }
    if (!allMatch) {
      fail(errorMessages.toString());
    }
  }

  @And("the response should contain empty array of best time visit data")
  public void theResponseShouldContainEmptyArrayOfBestTimeVisitData() {
    response.then().body("data", hasSize(0));
  }
}