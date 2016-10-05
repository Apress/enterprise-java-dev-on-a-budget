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

package com.ejdoab.tcms.services.dto.page;

import com.ejdoab.tcms.entities.*;
import com.ejdoab.tcms.services.dto.*;

import org.apache.cactus.*;

import java.util.*;


/**
 * PageFactory unit tests.
 * @see PageFactory
 * @author cjudd
 */
public class PageFactoryTest extends ServletTestCase {
    private SessionDTOFactory dtoFactory = null;

    /**
     * Creates a new PageFactoryTest object.
     *
     * @param arg0 DOCUMENT ME!
     */
    public PageFactoryTest(String arg0) {
        super(arg0);
    }

    /**
     * DOCUMENT ME!
     */
    public void setUp() {
        dtoFactory = (SessionDTOFactory) DTOAbstractFactory.getInstance()
                                                           .getDTOBuilder(SessionDTO.class);
    }

    /**
     * DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public void testBuildPage() throws Exception {
        SessionLocalHome slh;

        slh = SessionUtil.getLocalHome();

        Collection c = slh.findAll();

        Page page = PageFactory.buildPage(c, 0, 5, dtoFactory);
        assertNotNull("Page object", page);
        assertEquals("Page size", 5, page.getSize());
        assertEquals("Page total size", 16, page.getTotalSize());

        for (; page.hasNext();) {
            Object obj = page.next();
            assertTrue("Instance of Session", obj instanceof SessionDTO);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public void testEmptyCollection() throws Exception {
        Page page;
        page = PageFactory.buildPage(new ArrayList(), 0, 5, dtoFactory);
        assertEquals("Page size", 0, page.getSize());
        assertEquals("Page total size", 0, page.getTotalSize());
        assertEquals("Page.EMPTY_PAGE", Page.EMPTY_PAGE, page);
    }
}
