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

import com.ejdoab.tcms.services.dto.ConferenceAbstractDTO;
import com.ejdoab.tcms.services.dto.SessionDTO;
import com.ejdoab.tcms.services.dto.page.Page;
import com.ejdoab.tcms.util.DatabaseConnectionFactory;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dbunit.Assertion;

import org.dbunit.database.IDatabaseConnection;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;

import org.dbunit.operation.DatabaseOperation;

import java.rmi.RemoteException;

import javax.naming.Context;


/**
 * ConferenceServicesBean unit tests.
 * @see ConferenceServicesBean
 * @author cjudd
 */
public class ConferenceServicesBeanTest extends TestCase {
    private static String PRESENTERS_EMAIL = "bsb@isllc.com";
    private static Log log = LogFactory.getLog(ConferenceServicesBeanTest.class);
    private Context ctx = null;
    private ConferenceServices cs = null;

    /**
     * DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public void setUp() throws Exception {
        ConferenceServicesHome csHome = ConferenceServicesUtil.getHome();
        assertNotNull("ejb.ConferenceServiceHome interface", csHome);

        cs = csHome.create();
        assertNotNull("ConferenceServices remote interface", cs);

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
    public void testGetSessions() throws Exception {
        Page page;
        SessionDTO session;

        page = cs.getSessions(0, 5);
        assertEquals("Sessions 0,5 (Valid range)", 5, page.getSize());

        for (int i = 0; page.hasNext(); i++) {
            session = (SessionDTO) page.next();
            assertEquals("Session id equals expected value in valid range", i,
                session.getSessionId());
        }

        page = cs.getSessions(0, 16);
        assertEquals("Sessions 1 through 16 (Entire range)", 16, page.getSize());

        try {
            page = cs.getSessions(-1, 5);
            fail("Invalid range (-1,5)");
        } catch (RemoteException rex) {
        }

        //todo: this should throw an exception because it is out of bounds	  
        //      	try {
        //      		page = cs.getSessions(-5,-1);
        //    		fail("Invalid range (-5,-1)");  	  
        //		} catch (RemoteException rex) {}
        //todo: Should return the 13,14,15,16 elements
        //		page = cs.getSessions(13,4);
        //		assertEquals("Sessions 13,4 (After valid range)", 4, page.getSize());
        page = cs.getSessions(20, 25);
        assertEquals("Sessions 20 through 25 (After valid range)", 0,
            page.getSize());
    }

    /**
     * DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public void testGetSessionByPresenter() throws Exception {
        Page page;
        SessionDTO session;

        page = cs.getSessionsByPresenter("cjudd@js.com", 0, 3);
        assertEquals("Number of sessions for cjudd@js.com", 3, page.getSize());
        assertEquals("Number of total sessions for cjudd@js.com", 8,
            page.getTotalSize());
    }

    /**
     * DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public void testGetAbstracts() throws Exception {
        Page page;
        ConferenceAbstractDTO ca;

        page = cs.getAbstracts(0, 5);
        assertNotNull("Page", page);
        assertEquals("Page size", 5, page.getSize());

        //todo: I thought this should return 11 since there are 11 in the abstracts table
        //		assertEquals("Page total size", 11, page.getSize());
        while (page.hasNext()) {
            ca = (ConferenceAbstractDTO) page.next();
            assertNotNull("Title", ca.getTitle());
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public void testGetAbstractsByPresenter() throws Exception {
        Page page;
        ConferenceAbstractDTO ca;

        page = cs.getAbstractsByPresenter("cjudd@js.com", 0, 5);
        assertNotNull("Page", page);
        assertEquals("Page size", 5, page.getSize());
        assertEquals("Page total size", 5, page.getSize());

        while (page.hasNext()) {
            ca = (ConferenceAbstractDTO) page.next();
            assertNotNull("Title", ca.getTitle());
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public void testSubmitAbstract() throws Exception {
        Page page;
        ConferenceAbstractDTO ca;

        page = cs.getAbstracts(0, 5);

        int totalAbstracts = page.getTotalSize();

        Page presenterPage = cs.getAbstractsByPresenter(PRESENTERS_EMAIL, 0, 2);
        assertEquals("Starting count of presenter's abstracts", 6,
            presenterPage.getTotalSize());

        int totalPresenterAbstracts = presenterPage.getTotalSize();

        String aBody = "A panel of experts discuss the future of the Java platform";

        ConferenceAbstractDTO dto = new ConferenceAbstractDTO("The future of Java",
                "PANEL", "Management", "Intermediate", aBody, PRESENTERS_EMAIL);

        boolean submitted = cs.submitAbstract(dto);
        assertTrue("Submitted abstract", submitted);

        //todo: Does not return the new abstract. I think because of the cache.
        //		page = cs.getAbstracts(0, 5);
        //		assertEquals("Cached total size", totalAbstracts + 1, page.getTotalSize());
        // try a uncached result
        page = cs.getAbstracts(0, 6);
        assertEquals("Uncached total size", totalAbstracts + 1,
            page.getTotalSize());

        presenterPage = cs.getAbstractsByPresenter(PRESENTERS_EMAIL, 0, 3);
        assertEquals("After adding abstract count of presenter's abstracts",
            totalPresenterAbstracts + 1, presenterPage.getTotalSize());

        // compair contents of database ignoring PK_ID since it is generated
        IDatabaseConnection conn = DatabaseConnectionFactory.createConnection();
        ITable actualTable = conn.createQueryTable("ABSTRACTS",
                "select TITLE,TYPE,TOPIC,LEVEL,BODY,STATUS,FK_PRESENTERID from ABSTRACTS");

        IDataSet expectedDataSet = DatabaseConnectionFactory.createDataSet(
                "AfterAbstractSubmit.xml");
        ITable expectedTable = expectedDataSet.getTable("ABSTRACTS");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    /**
     * DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public void testGetAbstract() throws Exception {
        Page page;
        ConferenceAbstractDTO ca;

        ca = cs.getAbstract(5);
        assertNotNull("Abstract", ca);
        assertEquals("Abract ID", 5, ca.getAbstractId());
        assertEquals("Title", "Performance Tuning J2EE", ca.getTitle());
        assertEquals("Level", "Beginner", ca.getLevel());
        assertEquals("Email", "cjudd@js.com", ca.getPresenterEmail());
        assertEquals("Status", "ACCEPTED", ca.getStatus());
        assertEquals("Presenter", "Christopher Judd", ca.getPresenter());
        assertEquals("Topic", "Development", ca.getTopic());
        assertEquals("Type", "REGULAR", ca.getType());
        assertEquals("Body",
            "A scalable and extensible framework in which JavaTM technology performance tuning can be automated for enterprise applications.",
            ca.getBody());
    }
}
