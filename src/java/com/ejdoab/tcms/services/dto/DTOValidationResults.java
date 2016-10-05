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

package com.ejdoab.tcms.services.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * @author Brian Sam-Bodden
 */
public class DTOValidationResults {
    /** DOCUMENT ME! */
    public final static DTOValidationResults EMPTY_RESULTS = new DTOValidationResults() {
            public void addValidationError(String error) {
                // no-op
            }

            public void addValidationWarning(String warning) {
                // no-op			
            }
        };

    protected List validationErrors = Collections.EMPTY_LIST;
    protected List validationWarnings = Collections.EMPTY_LIST;

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isValid() {
        return validationErrors.isEmpty();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Iterator getValidationErrors() {
        return validationErrors.iterator();
    }

    /**
     * DOCUMENT ME!
     *
     * @param error DOCUMENT ME!
     */
    public void addValidationError(String error) {
        if ((validationErrors == null) ||
                (validationErrors == Collections.EMPTY_LIST)) {
            validationErrors = new ArrayList();
        }

        validationErrors.add(error);
    }

    /**
     * DOCUMENT ME!
     *
     * @param warning DOCUMENT ME!
     */
    public void addValidationWarning(String warning) {
        if ((validationWarnings == null) ||
                (validationWarnings == Collections.EMPTY_LIST)) {
            validationWarnings = new ArrayList();
        }

        validationWarnings.add(warning);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getErrorCount() {
        return validationErrors.size();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getWarningCount() {
        return validationWarnings.size();
    }
}
