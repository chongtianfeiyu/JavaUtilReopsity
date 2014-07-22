package com.yang.base.web;
 
import javax.servlet.http.HttpServletRequest;

//import com.yang.base.date.DateUtil;

public class ReqUtil
{
  private HttpServletRequest req;

  public ReqUtil()
  {
  }

  public ReqUtil(HttpServletRequest req)
  {
    this.req = req;
  }

  public String getString(String paraName) {
    String value = "";
    if ((this.req.getParameter(paraName) != null) && (this.req.getParameter(paraName).trim().length() > 0)) {
      value = this.req.getParameter(paraName).trim();
    }
    return value;
  }

  public String getString(String paraName, String defaultValue) {
    String value = "";
    if ((this.req.getParameter(paraName) != null) && (this.req.getParameter(paraName).trim().length() > 0))
      value = this.req.getParameter(paraName).trim();
    else {
      value = defaultValue;
    }
    return value;
  }

  public Integer getInt(String paraName) {
    int value = 0;
    if ((this.req.getParameter(paraName) != null) && (this.req.getParameter(paraName).trim().length() > 0)) {
      value = Integer.parseInt(this.req.getParameter(paraName));
    }
    return new Integer(value);
  }

  public Integer getInt(String paraName, int defaultValue) {
    int value = 0;
    if ((this.req.getParameter(paraName) != null) && (this.req.getParameter(paraName).trim().length() > 0))
      value = Integer.parseInt(this.req.getParameter(paraName));
    else {
      value = defaultValue;
    }
    return Integer.valueOf(value);
  }

  public Double getDobule(String paraName) {
    double value = 0.0D;
    if ((this.req.getParameter(paraName) != null) && (this.req.getParameter(paraName).trim().length() > 0)) {
      value = Double.parseDouble(this.req.getParameter(paraName));
    }
    return Double.valueOf(value);
  }

  public Double getDobule(String paraName, double defaultValue) {
    double value = 0.0D;
    if ((this.req.getParameter(paraName) != null) && (this.req.getParameter(paraName).trim().length() > 0))
      value = Double.parseDouble(this.req.getParameter(paraName));
    else {
      value = defaultValue;
    }
    return Double.valueOf(value);
  }

  public Float getFloat(String paraName) {
    float value = 0.0F;
    if ((this.req.getParameter(paraName) != null) && (this.req.getParameter(paraName).trim().length() > 0)) {
      value = Float.parseFloat(this.req.getParameter(paraName));
    }
    return Float.valueOf(value);
  }

  public Float getFloat(String paraName, float defaultValue) {
    float value = 0.0F;
    if ((this.req.getParameter(paraName) != null) && (this.req.getParameter(paraName).trim().length() > 0))
      value = Float.parseFloat(this.req.getParameter(paraName));
    else {
      value = defaultValue;
    }
    return Float.valueOf(value);
  }

  public Long getLong(String paraName, long defaultValue)
  {
    long value = 0L;
    if ((this.req.getParameter(paraName) != null) && (this.req.getParameter(paraName).trim().length() > 0))
      value = Long.parseLong(this.req.getParameter(paraName));
    else {
      value = defaultValue;
    }
    return Long.valueOf(value);
  }

  public Long getLong(String paraName) {
    long value = 0L;
    if ((this.req.getParameter(paraName) != null) && (this.req.getParameter(paraName).trim().length() > 0)) {
      value = Long.parseLong(this.req.getParameter(paraName));
    }
    return Long.valueOf(value);
  }

  public Boolean getBoolean(String paraName, Boolean defaultValue) {
    String booleanValue = this.req.getParameter(paraName);
    if ((booleanValue == null) || (booleanValue.trim().length() == 0)) {
      return ((defaultValue == null) ? null : defaultValue);
    }
    return Boolean.valueOf(Boolean.parseBoolean(booleanValue));
  }

  public Boolean getBoolean(String paraName)
  {
    return getBoolean(paraName, null);
  }
/*
  public java.util.Date getDate(String paraName) {
    java.util.Date value = new java.util.Date();
    if ((this.req.getParameter(paraName) != null) && (this.req.getParameter(paraName).trim().length() > 0)) {
      try {
        value = DateUtil.getdate1(this.req.getParameter(paraName));
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    return value;
  }
*/
  public java.util.Date getDate(String paraName, java.util.Date defaultValue) {
    java.util.Date value = new java.util.Date();
    if ((this.req.getParameter(paraName) != null) && (this.req.getParameter(paraName).trim().length() > 0))
      value = java.sql.Date.valueOf(this.req.getParameter(paraName));
    else {
      value = defaultValue;
    }
    return value;
  }
}

/* Location:           F:\myProject\WorkProject\jiankong2\workBench\MonitorWeb\WebContent\WEB-INF\lib\BDBase.jar
* Qualified Name:     com.dc.bd.frame.base.util.ReqUtil
* Java Class Version: 5 (49.0)
* JD-Core Version:    0.5.3
*/