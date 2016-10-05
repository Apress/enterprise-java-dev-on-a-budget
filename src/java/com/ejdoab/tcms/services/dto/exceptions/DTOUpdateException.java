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

import java.lang.Exception;


/**
 * @author Brian Sam-Bodden
 */
public class DTOUpdateException extends Exception {
    private Exception wex;

    /**
     * @param message the detail message.
     * @param exception the wrapped exception.
     */
    public DTOUpdateException(String message, Exception exception) {
        super(message);
        wex = exception;
    }

    /**
     * @param exception the contained (wrapped) exception.
     */
    public DTOUpdateException(Exception exception) {
        this(null, exception);
    }

    /**
     * @param message the detail message.
     */
    public DTOUpdateException(String message) {
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
        if (wex instanceof DTOUpdateException) {
            return ((DTOUpdateException) wex).getRootCause();
        }

        return (wex == null) ? this : wex;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String toString() {
        if (wex instanceof DTOUpdateException) {
            return ((DTOUpdateException) wex).toString();
        }

        return (wex == null) ? super.toString() : wex.toString();
    }
}
