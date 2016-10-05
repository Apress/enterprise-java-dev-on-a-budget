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

import junit.framework.TestCase;

import java.util.*;


/**
 * PageCache unit tests.
 * @see PageCache
 * @author cjudd
 */
public class PageCacheTest extends TestCase {
    private static final String NAME1 = "Name 1";
    private static final String NAME2 = "Name 2";
    private static final String NAME3 = "Name 3";
    private PageCache pc = PageCache.getInstance();
    private Page page1;
    private Page page2;
    private Page page3;
    private Collection list;

    /**
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        list = new ArrayList();
        list.add("One");
        list.add("Two");
        list.add("Three");
        page1 = new Page(list, 0, 3);
        page2 = new Page(list, 0, 2);
        page3 = new Page(list, 0, 1);
    }

    /**
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        pc.cleanCache();
    }

    /**
     * DOCUMENT ME!
     */
    public void testAddPage() {
        pc.addPage(NAME1, page1, 1);
        assertEquals("NAME 1", page1, pc.getPage(NAME1));
        pc.addPage(NAME2, page2, 1);
        assertEquals("NAME 2", page2, pc.getPage(NAME2));
        pc.addPage(NAME3, page3, 1);
        assertEquals("NAME 3", page3, pc.getPage(NAME3));
    }

    /**
     * DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public void testCleanCache() throws Exception {
        pc.addPage(NAME1, page1, 1);
        assertEquals("NAME 1", page1, pc.getPage(NAME1));
        pc.addPage(NAME2, page2, 5);
        pc.addPage(NAME3, page3, 1);
        assertEquals("NAME 3", page3, pc.getPage(NAME3));

        Thread.sleep(70000); // just over a 1 minute delay

        assertNull("NAME 1", pc.getPage(NAME1));
        assertNotNull("NAME 2", pc.getPage(NAME2));
        assertNull("NAME 3", pc.getPage(NAME3));
    }
}
