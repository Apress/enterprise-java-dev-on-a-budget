package com.ejboab.tcms.midp;

import java.util.*;
import javax.microedition.lcdui.*;

/** Displays an individual News Item */
public class NewsItemDisplayable extends Form implements CommandListener {
  StringItem date;
  StringItem body;
  Displayable returnTo;
  Hashtable item = null;

  /**
   * Display a news item
   * @param item News Item to display.
   */
  public NewsItemDisplayable(Hashtable item, Displayable prev) {
    super((String)item.get("title"));

    this.item = item;
    returnTo = prev;

    try {
      init();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void init() throws Exception {

    String sDate = ((String)item.get("date")).substring(0,10);

    date = new StringItem("Date:", sDate);
    body = new StringItem("", (String)item.get("body"));
    date.setLabel("Date");
    setCommandListener(this);
    addCommand(new Command("Back", Command.BACK, 1));
    this.append(date);
    this.append(body);
  }

  /**Handle events*/
  public void commandAction(Command command, Displayable displayable) {
    if(command.getCommandType() == Command.BACK) {
      TCMSMidlet.setCurrent(returnTo);
    }
  }

}
