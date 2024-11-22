package com.callplan.testing.steps.api;

import com.callplan.testing.steps.TestContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetMasterListSteps {

  public GetMasterListSteps() throws IOException {
  }

  @And("the response should contain array of master list data")
  public void theResponseShouldContainArrayOfMasterListData() {
    Response response = TestContext.getResponse();
    List<Map<String, Object>> masterList = response.jsonPath().getList("data");
    assertNotNull("Master list data should not be null", masterList);
    assertFalse("Master list should not be empty", masterList.isEmpty());
  }

  @And("each master list data should match schema")
  public void eachMasterListDataShouldMatchSchema() throws JsonProcessingException {
    Response response = TestContext.getResponse();
    List<Map<String, Object>> masterList = response.jsonPath().getList("data");
    StringBuilder errorMessages = new StringBuilder();
    ObjectMapper objectMapper = new ObjectMapper();
    boolean allMatchSchema = true;
    int index = 0;

    for (Map<String, Object> master : masterList) {
      try {
        String masterJson = objectMapper.writeValueAsString(master);
        assertThat(masterJson, matchesJsonSchemaInClasspath("schemas/GetMasterList.json"));
      } catch (AssertionError e) {
        allMatchSchema = false;
        errorMessages.append(String.format("Schema validation failed for item at index %d: %s%n", 
            index, e.getMessage()));
      }
      index++;
    }

    if (!allMatchSchema) {
      fail("Schema validation failed with the following errors:\n" + errorMessages.toString());
    }
  }

  @And("the response should contain empty array of master list data")
  public void theResponseShouldContainEmptyArrayOfMasterListData() {
    Response response = TestContext.getResponse();
    List<Map<String, Object>> masterList = response.jsonPath().getList("data");
    assertTrue("Master list should be empty", masterList.isEmpty());
  }
}