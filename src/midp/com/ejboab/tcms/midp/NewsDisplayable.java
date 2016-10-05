package com.ejboab.tcms.midp;

import javax.microedition.lcdui.*;
import java.util.*;

/**
 * Displays a list of news items
 */
public class NewsDisplayable extends List implements CommandListener {

  public static Command getCommand = new Command("Get", Command.SCREEN, 1);
  public static Command selectCommand = new Command("Select", Command.ITEM, 1);
  public static Command exitCommand = new Command("Exit", Command.EXIT, 3);

  private Vector news = null;

  /** Constructor */
  public NewsDisplayable() {
    super("Conference News", List.IMPLICIT);
    try {
      init();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void init() throws Exception {
    setCommandListener(this);
    addCommand(getCommand);
    addCommand(selectCommand);
    addCommand(exitCommand);
    listNews();
  }

  /** Handle events*/
  public void commandAction(Command command, Displayable displayable) {
    if (command == exitCommand) {
      TCMSMidlet.quitApp();
    } else if (command == getCommand) {
      news = SyncAgent.getAgent().getNews();
      listNews();
    } else if (command == selectCommand) {
      if(news != null) {
        Hashtable item = (Hashtable)news.elementAt(getSelectedIndex());
        NewsItemDisplayable nid = new NewsItemDisplayable(item, this);
        TCMSMidlet.setCurrent(nid);
      }
    }
  }

  private void listNews() {
    Hashtable item = null;
    if(news != null) {
      for (Enumeration e = news.elements() ; e.hasMoreElements() ;) {
        item = (Hashtable)e.nextElement();
        append((String)item.get("title"), null);
      }
    }
  }

}
