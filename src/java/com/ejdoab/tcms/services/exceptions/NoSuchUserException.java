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

package com.ejdoab.tcms.services.exceptions;


/**
 * @author Brian Sam-Bodden
 */
public class NoSuchUserException extends Exception {
    private Exception rootCause;

    /**
     * Constructor NoSuchUserException.
     * @param string
     */
    public NoSuchUserException(String message) {
        super(message);
    }

    /**
     * Constructor NoSuchUserException.
     * @param e
     * @param string
     */
    public NoSuchUserException(Exception e, String message) {
        this(message);
        rootCause = e;
    }
}
