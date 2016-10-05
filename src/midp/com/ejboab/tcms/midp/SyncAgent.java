package com.ejboab.tcms.midp;

import java.util.*;

import org.ksoap.SoapObject;
import org.ksoap.SoapFault;
import org.ksoap.transport.HttpTransport;
import org.kobjects.serialization.*;
import org.ksoap.ClassMap;
import org.ksoap.*;

/**
 * Wraps SOAP calls
 */
public class SyncAgent {

  private static SyncAgent agent = null;

  private static final String SERVER = "localhost:8080";

  private SyncAgent() {}

  public static SyncAgent getAgent() {
    if(agent == null) {
      agent = new SyncAgent();
    }
    return agent;
  }

  /**
   * Retrieves Current News Items
   * @return Collection of news items as a Hashtable
   */
  public Vector getNews() {

    HttpTransport ht = null;
    String method = "getNews";
    Vector requests = null;

    try {
      ht = createTransport(method);

      SoapObject request = createObject(method);
      Vector result = (Vector)ht.call(request);

      requests = mapSoapToHash(result);
    } catch (SoapFault sf) {
      System.out.println(sf.faultcode + " - " + sf.faultstring);
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }

    return requests;
  }

  /**
   * Converts Soap objects to Hashtables
   * @param requests Collection of soap objects to convert
   * @return Collection of objects as a Hashtable
   */
  private Vector mapSoapToHash (Vector requests) {

    Vector v = new Vector(requests.size());
    Hashtable ht = null;
    Object value = null;
    PropertyInfo pi = new PropertyInfo();

    for (Enumeration e = requests.elements(); e.hasMoreElements();) {
      Object item = e.nextElement();
      if(item instanceof SoapObject) {
        SoapObject so = (SoapObject)item;
        ht = new Hashtable();

        ht.put("class", so.getName());

        for (int i = 0; i < so.getPropertyCount(); i++) {
          so.getPropertyInfo(i, pi);
          value = so.getProperty(i);
          if(value instanceof SoapPrimitive) {
            value = ((SoapPrimitive)value).toString();
          }

          ht.put(pi.name, value);
        }

        v.addElement(ht);
      }
    }
    return v;

  }

  /** Factory method for creating new HttpTransport intances with url to prefered server.
   * @param action SOAP action placed in HTTP header (typically remote method name)
   * @return new HttpTransport instance
   * @throws Exception Unable to determine server from preferences
   */
  private static HttpTransport createTransport(String action) throws Exception {
    HttpTransport ht;

    ht = new HttpTransport("http://" + SERVER + "/tcms/services/News", action);
    ht.debug = true;
    return ht;
  }

  /** Factory method for creating new SoapObjects with the appropriate user name
   * and password.
   * @return new SoapObject instance
   * @param name name of soap object
   * @throws Exception
   */
  private static SoapObject createObject(String name) throws Exception {
    SoapObject so = new SoapObject("", name);
    return so;
  }

}