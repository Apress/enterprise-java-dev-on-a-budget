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

import com.ejdoab.tcms.services.dto.*;
import com.ejdoab.tcms.services.exceptions.*;
import com.ejdoab.tcms.util.*;

import junit.framework.*;

import org.dbunit.*;

import org.dbunit.database.*;

import org.dbunit.dataset.*;

import org.dbunit.operation.*;


/**
 * @author cjudd
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class UserServiceBeanTest extends TestCase {
    private static String USER_EMAIL = "bsb@isllc.com";
    private UserServices us;

    /**
     * DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public void setUp() throws Exception {
        UserServicesHome uslh = UserServicesUtil.getHome();
        us = uslh.create();

        // set up datebase
        IDatabaseConnection conn = DatabaseConnectionFactory.createConnection();

        try {
            DatabaseOperation.CLEAN_INSERT.execute(conn,
                DatabaseConnectionFactory.createBaseDataSet());
        } finally {
            conn.close();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public void testAuthenticate() throws Exception {
        boolean authenticated = us.authenticate(USER_EMAIL, "pswd");
        assertTrue("Authenticated valid user", authenticated);

        authenticated = us.authenticate(USER_EMAIL, "invalid");
        assertFalse("Authenticated invalid password", authenticated);

        try {
            us.authenticate("invalid", "invalid");
            fail("Invalid user");
        } catch (NoSuchUserException nsue) {
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public void testRegisterUser() throws Exception {
        UserProfileDTO attendee = new UserProfileDTO();

        attendee.setUserType(UserProfileDTO.UserType.ATTENDEE);
        attendee.setPassword("abc");
        attendee.setFirstName("Larry");
        attendee.setLastName("Ellison");
        attendee.setEmail("larry@oracle.com");
        attendee.setHomePhone("555.555.6661");
        attendee.setWorkPhone("555.555.6662");
        attendee.setFax("555.555.6663");

        boolean registered = us.registerUser(attendee);
        assertTrue("Registered new attendee", registered);

        UserProfileDTO newAttendee = us.getUserProfile("larry@oracle.com");
        assertNotNull("New attendee", newAttendee);
        assertEquals("New attendee email", "larry@oracle.com",
            newAttendee.getEmail());
        assertEquals("New attendee first name", "Larry",
            newAttendee.getFirstName());

        // compair contents of database ignoring PK_ID since it is generated
        IDatabaseConnection conn = DatabaseConnectionFactory.createConnection();
        ITable actualTable = conn.createQueryTable("USERS",
                "select PASSWORD,FIRSTNAME,LASTNAME,EMAIL,HOMEPHONE,WORKPHONE,FAX from USERS");

        IDataSet expectedDataSet = DatabaseConnectionFactory.createDataSet(
                "AfterUserSubmit.xml");
        ITable expectedTable = expectedDataSet.getTable("USERS");

        Assertion.assertEquals(expectedTable, actualTable);

        try {
            us.registerUser(attendee);
            fail("Reregister email address");
        } catch (DuplicateEmailException dee) {
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public void testRegisterPresenter() throws Exception {
        UserProfileDTO presenter = new UserProfileDTO();

        presenter.setUserType(UserProfileDTO.UserType.PRESENTER);
        presenter.setPassword("edf");
        presenter.setFirstName("Bill");
        presenter.setLastName("Gates");
        presenter.setEmail("billy@ms.com");
        presenter.setHomePhone("555.523.6661");
        presenter.setWorkPhone("555.523.6662");
        presenter.setFax("555.523.6663");

        boolean registered = us.registerUser(presenter);
        assertTrue("Registered new presenter", registered);

        UserProfileDTO newPresenter = us.getUserProfile("billy@ms.com");
        assertNotNull("New presenter", newPresenter);
        assertEquals("New presenter email", "billy@ms.com",
            newPresenter.getEmail());
        assertEquals("New presenter first name", "Bill",
            newPresenter.getFirstName());

        // compair contents of database ignoring PK_ID since it is generated
        IDatabaseConnection conn = DatabaseConnectionFactory.createConnection();
        ITable actualTable = conn.createQueryTable("USERS",
                "select PASSWORD,FIRSTNAME,LASTNAME,EMAIL,HOMEPHONE,WORKPHONE,FAX from USERS");

        IDataSet expectedDataSet = DatabaseConnectionFactory.createDataSet(
                "AfterPresenterSubmit.xml");
        ITable expectedTable = expectedDataSet.getTable("USERS");

        Assertion.assertEquals(expectedTable, actualTable);

        try {
            us.registerUser(presenter);
            fail("Reregister email address");
        } catch (DuplicateEmailException dee) {
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public void testSetUserProfile() throws Exception {
        UserProfileDTO user = us.getUserProfile("bsb@isllc.com");

        assertNotNull("Got user", user);

        user.setFirstName("first");
        user.setFax("fax");

        us.setUserProfile(user);

        user = us.getUserProfile("bsb@isllc.com");
        assertEquals("Check first", "first", user.getFirstName());
        assertEquals("Check fax", "fax", user.getFax());
    }
}
