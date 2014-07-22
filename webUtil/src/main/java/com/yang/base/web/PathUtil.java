package com.yang.base.web;
 
import javax.servlet.ServletContext;

public class PathUtil
{
  private static String webRootPath = null;

  public static void init(ServletContext context) {
    webRootPath = context.getRealPath("/");
  }

  public static String getWebRoot()
    throws IllegalAccessException
  {
    return webRootPath;
  }
}

/* Location:           F:\myProject\WorkProject\jiankong2\workBench\MonitorWeb\WebContent\WEB-INF\lib\BDBase.jar
* Qualified Name:     com.dc.bd.frame.base.util.PathUtil
* Java Class Version: 5 (49.0)
* JD-Core Version:    0.5.3
*/