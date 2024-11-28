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
    ObjectMapper mapper = new ObjectMapper();
    try {
      String jsonArray = mapper.writeValueAsString(response.jsonPath().getList("data"));
      assertThat(jsonArray, matchesJsonSchemaInClasspath("schemas/GetMasterList.json"));
    } catch (JsonProcessingException e) {
      fail("Failed to parse response data: " + e.getMessage());
    }
  }

  @And("the response should contain empty array of master list data")
  public void theResponseShouldContainEmptyArrayOfMasterListData() {
    Response response = TestContext.getResponse();
    List<Map<String, Object>> masterList = response.jsonPath().getList("data");
    assertTrue("Master list should be empty", masterList.isEmpty());
  }
}