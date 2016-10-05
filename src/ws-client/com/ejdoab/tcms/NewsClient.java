package com.ejdoab.tcms;

import localhost.tcms.services.News.*;

import com.ejdoab.tcms.services.*;

/**
 * Simple News Web Services Client
 * @author cjudd
 */
public class NewsClient {

  public static void main(String[] args) throws Exception {
    NewsServiceLocator locator = new NewsServiceLocator();
    News news = locator.getNews();
    NewsItemDTO[] items = news.getNews();

    System.out.println("Conference News");

    for(int i = 0; i < items.length; i++) {
      System.out.println("*** " + items[i].getTitle() + " ***");
      System.out.println(items[i].getBody());
    }
  }
  
}
