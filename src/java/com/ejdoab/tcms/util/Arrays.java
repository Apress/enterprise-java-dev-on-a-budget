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

package com.ejdoab.tcms.util;


/**
 * @author Brian Sam-Bodden
 */
public class Arrays {
    /** DOCUMENT ME! */
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[] {  };

    private Arrays() {
    }

    /**
     * DOCUMENT ME!
     *
     * @param array DOCUMENT ME!
     * @param element DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static int getElementIndexIgnoreCase(String[] array, String element) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equalsIgnoreCase(element)) {
                return i;
            }
        }

        return -1;
    }
}
