package com.ejboab.tcms.midp;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class TCMSMidlet extends MIDlet {
  private static TCMSMidlet instance;
  private NewsDisplayable displayable = new NewsDisplayable();

  public TCMSMidlet() {
    instance = this;
  }

  /** Start method */
  public void startApp() {
    setCurrent(displayable);
  }

  /** Handle pausing */
  public void pauseApp() {
  }

  /** Clean up */
  public void destroyApp(boolean unconditional) {
  }

  /** Exit */
  public static void quitApp() {
    instance.destroyApp(true);
    instance.notifyDestroyed();
    instance = null;
  }

  public static void setCurrent(Displayable next) {
    Display.getDisplay(instance).setCurrent(next);
  }

  public static void setCurrent(Alert alert, Displayable next) {
    Display.getDisplay(instance).setCurrent(alert, next);
  }

}
