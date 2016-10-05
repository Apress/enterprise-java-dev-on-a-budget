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

package com.ejdoab.tcms.services.dto.exceptions;


/**
 * @author Brian Sam-Bodden
 */
public class DTOCreateException extends Exception {
    private Exception wex;

    /**
     * @param message the detail message.
     * @param exception the wrapped exception.
     */
    public DTOCreateException(String message, Exception exception) {
        super(message);
        wex = exception;
    }

    /**
     * @param exception the contained (wrapped) exception.
     */
    public DTOCreateException(Exception exception) {
        this(null, exception);
    }

    /**
     * @param message the detail message.
     */
    public DTOCreateException(String message) {
        this(message, null);
    }

    /**
     * @return the wrapped exception.
     */
    public Exception getException() {
        return wex;
    }

    /**
     * @return
     */
    public Exception getRootCause() {
        if (wex instanceof DTOCreateException) {
            return ((DTOCreateException) wex).getRootCause();
        }

        return (wex == null) ? this : wex;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String toString() {
        if (wex instanceof DTOCreateException) {
            return ((DTOCreateException) wex).toString();
        }

        return (wex == null) ? super.toString() : wex.toString();
    }
}
