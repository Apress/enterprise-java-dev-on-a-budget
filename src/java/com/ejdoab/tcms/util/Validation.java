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

import org.apache.commons.validator.EmailValidator;


/**
 * @author Brian Sam-Bodden
 * package org.apache.commons.validator.EmailValidator (see)
 */
public class Validation {
    private static EmailValidator emailValidator = EmailValidator.getInstance();

    /**
     * DOCUMENT ME!
     *
     * @param email DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static boolean validateEmail(String email) {
        return emailValidator.isValid(email);
    }
}
