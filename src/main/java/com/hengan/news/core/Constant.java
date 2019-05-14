package com.hengan.news.core;

/**
 * @author cyq
 */
public class Constant {
  /**
   *   安全密匙
   */
  public static String SECRET_KEY = "Q1lRTkJDWVFOQkNZUU5CJTIx";

  public interface ERROR_CODE{
      String UN_KNOWN = "CEC-000001";
      String NOT_AUTHKEY = "CEC-000002";
      String LOGIN_TIMEOUT = "CEC-000003";
  }

}