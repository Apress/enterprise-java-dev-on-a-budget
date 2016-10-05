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

import java.io.Serializable;

import java.util.*;


/**
 * Collection subset used to page through large collections.
 * @author Brian Sam-Bodden
 */
public class Page implements Iterator, Serializable {
    /** DOCUMENT ME! */
    public static final Page EMPTY_PAGE = new Page(Collections.EMPTY_LIST, 0, 0);
    private ArrayList contents;
    private transient Iterator i;
    private int index;
    private int totalSize;
    private int size;
    private transient String toStringValue;

    /**
     * Consturctor
     * @param c source collection
     * @param index index of the collection to start
     * @param size number of elements to be placed in Page
     */
    public Page(Collection c, int index, int size) {
        Object[] oa = new Object[size];
        System.arraycopy(c.toArray(), index, oa, 0, size);
        contents = new ArrayList(Arrays.asList(oa));
        this.index = index;
        this.size = size;
        totalSize = c.size();
    }

    /**
     * Consturctor
     * @param c source collection
     * @param index index of the collection to start
     * @param size number of elements to be placed in Page
     * @param totalSize of collection
     */
    public Page(Collection c, int index, int size, int totalSize) {
        Object[] oa = new Object[size];
        System.arraycopy(c.toArray(), 0, oa, 0, size);
        contents = new ArrayList(Arrays.asList(oa));
        this.index = index;
        this.size = size;
        this.totalSize = totalSize;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean hasNext() {
        checkIterator();

        boolean hasNext = i.hasNext();

        if (!hasNext) {
            i = contents.iterator();
        }

        return hasNext;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Object next() {
        checkIterator();

        return i.next();
    }

    /**
     * DOCUMENT ME!
     */
    public void remove() {
        throw new UnsupportedOperationException(
            "Operation is not supported on a read-only list");
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getIndex() {
        return index;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean hasMoreDataAhead() {
        return (index + size) < totalSize;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean hasMoreDataBefore() {
        return index > 0;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getNextPageStartIndex() {
        return index + size;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getPreviousPageStartIndex() {
        return Math.max(index - size, 0);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getSize() {
        return size;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getTotalSize() {
        return totalSize;
    }

    /**
     * DOCUMENT ME!
     */
    private void checkIterator() {
        if (i == null) {
            i = contents.iterator();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String toString() {
        if (toStringValue == null) {
            StringBuffer sb = new StringBuffer();
            checkIterator();

            for (int index = 0, n = contents.size(); index < n; index++) {
                sb.append(i.next().toString()).append('\n');
            }

            toStringValue = sb.toString();
        }

        return toStringValue;
    }
}
