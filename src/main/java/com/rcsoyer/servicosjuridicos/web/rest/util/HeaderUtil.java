package com.rcsoyer.servicosjuridicos.web.rest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

/**
 * Utility class for HTTP headers creation.
 */
public final class HeaderUtil {

  private static final Logger log = LoggerFactory.getLogger(HeaderUtil.class);

  private static final String APPLICATION_NAME = "servicosJuridicosApp";

  private HeaderUtil() {}

  public static HttpHeaders createAlert(String message, String param) {
    String headerAlert = "X-" + APPLICATION_NAME + "-alert";
    String headerParams = "X-" + APPLICATION_NAME + "-params";
    HttpHeaders headers = new HttpHeaders();
    headers.add(headerAlert, message);
    headers.add(headerParams, param);
    return headers;
  }

  public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
    String message = APPLICATION_NAME + "." + entityName + ".created";
    return createAlert(message, param);
  }

  public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
    String message = APPLICATION_NAME + "." + entityName + ".updated";
    return createAlert(message, param);
  }

  public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
    String message = APPLICATION_NAME + "." + entityName + ".deleted";
    return createAlert(message, param);
  }

  public static HttpHeaders createFailureAlert(String entityName, String errorKey,
      String defaultMessage) {
    log.error("Entity processing failed, {}", defaultMessage);
    String headerError = "X-" + APPLICATION_NAME + "-error";
    String headerParams = "X-" + APPLICATION_NAME + "-params";
    String headerErrorValue = "error." + errorKey;
    HttpHeaders headers = new HttpHeaders();
    headers.add(headerError, headerErrorValue);
    headers.add(headerParams, entityName);
    return headers;
  }
}
