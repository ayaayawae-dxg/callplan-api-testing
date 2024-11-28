package com.callplan.testing.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Allure;

public class AllureUtils {
  private static final ObjectMapper mapper = new ObjectMapper();

  /**
   * Attaches a pretty-printed JSON to Allure report
   *
   * @param title  The title of the attachment
   * @param object The object to be converted to JSON
   */
  public static void attachJsonToAllure(String title, Object object) {
    try {
      String prettyJson = mapper
        .writerWithDefaultPrettyPrinter()
        .writeValueAsString(object);

      Allure.addAttachment(title, "application/json", prettyJson);
    } catch (JsonProcessingException e) {
      Allure.addAttachment(
        "Error formatting JSON for " + title,
        "application/json",
        "Failed to format JSON: " + e.getMessage()
      );
    }
  }
} 