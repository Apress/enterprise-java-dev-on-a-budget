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
public class DuplicateEmailException extends Exception {
    /**
     * Constructor DuplicateEmailException.
     * @param string
     */
    public DuplicateEmailException(String message) {
        super(message);
    }
}
