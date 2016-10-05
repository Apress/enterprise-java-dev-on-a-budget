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

import com.ejdoab.tcms.services.dto.page.Page;

import junit.framework.TestCase;

import java.util.*;


/**
 * Page Unit Test
 * @see Page
 * @author cjudd
 */
public class PageTest extends TestCase {
    private static final int SIZE = 10;
    private Collection list = new ArrayList();

    /**
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        for (int i = 1; i <= SIZE; i++) {
            list.add(new Integer(i));
        }
    }

    /**
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        list.clear();
    }

    /**
     * Test getSize, getTotalSize and getIndex
     */
    public void testGetSize() {
        Page page;

        page = new Page(list, 0, 10);
        assertEquals("Get Size of entire collection.", SIZE, page.getSize());
        assertEquals("Get Total Size of entire collection.", SIZE,
            page.getTotalSize());
        assertEquals("Get Index on entire collection", 0, page.getIndex());

        page = new Page(list, 2, 4);
        assertEquals("Get Size of index 2 and size 4.", 4, page.getSize());
        assertEquals("Get Total Size of index 2 and size 4.", SIZE,
            page.getTotalSize());
        assertEquals("Get Index of index 2 and size 4", 2, page.getIndex());
    }

    /**
     * Test Page boundries.
     */
    public void testBoundries() {
        Page page;

        try {
            page = new Page(list, -5, 2);
            fail("Invalid index -5,2");
        } catch (ArrayIndexOutOfBoundsException abex) {
            // expected
        }

        try {
            page = new Page(list, -1, 2);
            fail("Invalid index -1,2");
        } catch (ArrayIndexOutOfBoundsException abex) {
            // expected
        }

        page = new Page(list, 9, 1);
        assertEquals("Valid range 9,1", 1, page.getSize());

        //todo: fix Array out of bounds error
        //		page = new Page(list, 9, 2);
        //		assertEquals("Valid range 9,1", 1, page.getSize());
    }

    /**
     * Test for hasNext and next.
     */
    public void testNext() {
        Page page;

        page = new Page(list, 0, 10);

        for (int i = 1; page.hasNext(); i++) {
            Integer item = (Integer) page.next();
            assertEquals("Page element 0,10", i, item.intValue());
        }

        page = new Page(list, 2, 4);

        for (int i = 3; page.hasNext(); i++) {
            Integer item = (Integer) page.next();
            assertEquals("Page element 2,4", i, item.intValue());
        }
    }

    /**
     * Test for getNextPageStartIndex, getPreviousPageStartIndex,
     * hasMoreDataAhead and hasMoreDataBefore
     */
    public void testHasMoreData() {
        Page page;

        page = new Page(list, 0, 4);
        assertEquals("Next page start:1", 4, page.getNextPageStartIndex());
        assertEquals("Prev page start:1", 0, page.getPreviousPageStartIndex());
        assertEquals("Has more ahead: 1", true, page.hasMoreDataAhead());
        assertEquals("Has more before:1", false, page.hasMoreDataBefore());

        page = new Page(list, page.getNextPageStartIndex(), 4);
        assertEquals("Next Page Start:2", 8, page.getNextPageStartIndex());
        assertEquals("Prev Page Start:2", 0, page.getPreviousPageStartIndex());
        assertEquals("Has more ahead: 2", true, page.hasMoreDataAhead());
        assertEquals("Has more before:2", true, page.hasMoreDataBefore());

        //todo: fix Array out of bounds exception
        //		page = new Page(list, page.getNextPageStartIndex(), 4);
        //		assertEquals("Next Page Start:3", 12, page.getNextPageStartIndex());
        //		assertEquals("Prev Page Start:3", 8, page.getPreviousPageStartIndex());	
        //		assertEquals("Has more ahead: 3", true, page.hasMoreDataAhead());
        //		assertEquals("Has more before:3", true, page.hasMoreDataBefore());
    }
}
