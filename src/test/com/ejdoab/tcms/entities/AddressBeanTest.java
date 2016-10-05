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

package com.ejdoab.tcms.entities;

import org.apache.cactus.ServletTestCase;

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Collection;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.rmi.PortableRemoteObject;


/**
 * Unit test for AddressBean
 * @author cjudd
 */
public class AddressBeanTest extends ServletTestCase {
    private static final String ADDR1 = "ADDR1";
    private static final String APT1 = "APPT1";
    private static final String CITY1 = "CITY1";
    private static final String STATE1 = "STATE1";
    private static final String ZIP1 = "ZIP1";
    private static final String ADDR2 = "ADDR2";
    private static final String APT2 = "APPT2";
    private static final String CITY2 = "CITY2";
    private static final String STATE2 = "STATE2";
    private static final String ZIP2 = "ZIP2";
    private static final String ADDR3 = "ADDR3";
    private static final String APT3 = "APPT3";
    private static final String CITY3 = "CITY3";
    private static final String STATE3 = "STATE3";
    private static final String ZIP3 = "ZIP3";
    private static final String ADDR4 = "ADDR4";
    private static final String APT4 = "APPT4";
    private static final String CITY4 = "CITY4";
    private static final String STATE4 = "STATE4";
    private static final String ZIP4 = "ZIP4";
    private Context ctx;
    private AddressLocalHome home;

    /**
     * Constructor for AddressBeanTest.
     * @param arg0
     */
    public AddressBeanTest(String arg0) {
        super(arg0);
    }

    /**
     * DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public void setUp() throws Exception {
        ctx = new InitialContext();

        // look up the home interface
        Object obj = ctx.lookup("ejb.AddressLocalHome");
        assertNotNull("ejb.AddressLocalHome lookup", obj);

        // cast and narrow
        home = (AddressLocalHome) PortableRemoteObject.narrow(obj,
                AddressLocalHome.class);
        assertNotNull("ejb.AddressBeanHome interface", home);
    }

    /**
     * DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public void testCreateAddress() throws Exception {
        Statement stmt = null;
        ResultSet rs = null;
        Collection c = null;

        c = home.findAll();
        assertEquals("Addresses count before Create", 5, c.size());

        AddressLocal bean = home.create(ADDR1, APT1, CITY1, STATE1, ZIP1);
        assertNotNull("AddressBean interface", bean);

        c = home.findAll();
        assertEquals("Addresses count after Create", 6, c.size());

        c = home.findByStreetAddress(ADDR1);
        assertEquals("Address count after address find", 1, c.size());

        bean = (AddressLocal) c.toArray()[0];
        assertNotNull(bean);

        assertEquals("Street Address", ADDR1, bean.getStreetAddress());
        assertEquals("Apt", APT1, bean.getAptNumber());
        assertEquals("City", CITY1, bean.getCity());
        assertEquals("State", STATE1, bean.getState());
        assertEquals("Zip", ZIP1, bean.getZipCode());

        //home.remove(bean);
        bean.setStreetAddress(ADDR2);
        bean.setAptNumber(APT2);
        bean.setCity(CITY2);
        bean.setState(STATE2);
        bean.setZipCode(ZIP2);

        c = home.findByStreetAddress(ADDR1);
        assertEquals("Search for ADDR1 changing address values", 0, c.size());

        c = home.findByStreetAddress(ADDR2);
        assertEquals("Search for ADDR2 changing address values", 1, c.size());

        bean = (AddressLocal) c.toArray()[0];
        assertNotNull(bean);

        assertEquals("Street Address", ADDR2, bean.getStreetAddress());
        assertEquals("Apt", APT2, bean.getAptNumber());
        assertEquals("City", CITY2, bean.getCity());
        assertEquals("State", STATE2, bean.getState());
        assertEquals("Zip", ZIP2, bean.getZipCode());
    }
}
