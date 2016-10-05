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
public class NameValue {
    String _name;
    String _value;

    /**
     * Creates a new NameValue object.
     *
     * @param name DOCUMENT ME!
     * @param value DOCUMENT ME!
     */
    public NameValue(String name, String value) {
        _name = name;
        _value = value;
    }

    /**
     * @return
     */
    public String getName() {
        return _name;
    }

    /**
     * @return
     */
    public String getValue() {
        return _value;
    }

    /**
     * @param string
     */
    public void setName(String string) {
        _name = string;
    }

    /**
     * @param string
     */
    public void setValue(String string) {
        _value = string;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String toString() {
        return _name + " = " + _value;
    }
}
