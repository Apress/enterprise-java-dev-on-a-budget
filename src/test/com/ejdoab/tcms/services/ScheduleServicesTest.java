/* ========================================================================= *
 *                                                                           *
 *                   Enterprise Java Development on a Budget                 *
 *                   Technology Conference Management System                 *
 *                              www.ejdoab.com                               *
 *            Licensed under The Apache Software License, Version 1.1        *
 *                                                                           *
 *            Copyright (c) 2003 Brian Sam-Bodden, Christopher M. Judd       *
 *                          All rights reserved.                             *
 *                                                                           *
 * ========================================================================= */

package com.ejdoab.tcms.services;

import com.ejdoab.tcms.services.dto.ScheduleDTO;
import com.ejdoab.tcms.services.dto.ScheduleEntryDTO;
import com.ejdoab.tcms.services.dto.ScheduleReminderDTO;

//import com.ejdoab.tcms.services.dto.exceptions.ScheduleConflictException;
import com.ejdoab.tcms.services.exceptions.NoSuchUserException;
import com.ejdoab.tcms.util.HoursAndMinutes;

import junit.framework.*;

import java.rmi.RemoteException;

import java.util.Hashtable;

import javax.ejb.CreateException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.rmi.PortableRemoteObject;


/**
 * @author Brian Sam-Bodden
 *
 */
public class ScheduleServicesTest extends TestCase {
    private static final String ICF = "org.jnp.interfaces.NamingContextFactory";
    private static final String SERVER_URI = "localhost:1099";
    private static final String PKG_PREFIXES = "org.jboss.naming:org.jnp.interfaces";

    /**
     * DOCUMENT ME!
     */
    public void testXXX() {
        // dummy test to repress JUnit failure
    }

    /**
     * DOCUMENT ME!
     *
     * @param args DOCUMENT ME!
     */
    public static void main(String[] args) {
        Context ctx;
        ScheduleServicesHome home;
        ScheduleServices service;

        // initial context JBossNS configuration
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, ICF);
        env.put(Context.PROVIDER_URL, SERVER_URI);
        env.put(Context.URL_PKG_PREFIXES, PKG_PREFIXES);

        try {
            // ----------
            // JNDI Stuff
            // ----------
            ctx = new InitialContext(env);

            // look up the home interface
            System.out.println(
                "[jndi lookup] Looking Up Schedule Services Remote Home Interface");

            Object obj = ctx.lookup("ejb.ScheduleServicesHome");

            // cast and narrow
            home = (ScheduleServicesHome) PortableRemoteObject.narrow(obj,
                    ScheduleServicesHome.class);
            service = home.create();

            // ----------
            // Tests begin
            // ----------
            System.out.println("[test for getSchedule] ");

            ScheduleDTO schedule = null;

            try {
                schedule = service.getUserSchedule("bsb@isllc.com");
                System.out.println("[schedule] \n" + schedule);
            } catch (NoSuchUserException nsue) {
                System.out.println("[getSchedule] " + nsue.getMessage());
            }

            boolean writeTest = false;

            if ((schedule != null) && (writeTest)) {
                ScheduleEntryDTO entry = schedule.getScheduleEntry(20);

                //ScheduleItemDTO entry = new ScheduleItemDTO(5, "bsb@isllc.com", "applies to current project");
                ScheduleReminderDTO reminder = new ScheduleReminderDTO("my second reminder",
                        new HoursAndMinutes(5, 45));
                entry.addReminder(reminder);

                //				try {
                //schedule.addScheduleEntry(entry);
                System.out.println("[schedule local] \n" + schedule);
                service.setUserSchedule(schedule);

                //				} catch (ScheduleConflictException sce) {
                //					System.out.println("[addScheduleEntry] scheduling conflict exception: " + sce.getMessage());
                //				}								
            }
        } catch (CreateException ce) {
            System.out.println("[ejb] create exception: " + ce.getMessage());
        } catch (RemoteException re) {
            System.out.println("[rmi] remote exception: " + re.getMessage());
        } catch (NamingException ne) {
            System.out.println("[naming] naming exception: " + ne.getMessage());
        }
    }
}
