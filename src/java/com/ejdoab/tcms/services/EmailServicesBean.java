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

package com.ejdoab.tcms.services;

import org.apache.log4j.Logger;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;


/**
 * @author <a href="mailto:bsbodden@integrallis.com">Brian Sam-Bodden</a>
 *
 * @ejb.bean
 *      name="EmailServices"
 *      type="Stateless"
 *      view-type="local"
 *      local-jndi-name="ejb.EmailServicesLocalHome"
 * @ejb.transaction
 *      type="Required"
 * @ejb.util
 *      generate="physical"
 */
public abstract class EmailServicesBean implements SessionBean {
    private Logger log;

    /**
    * @ejb.interface-method
    * @ejb.transaction
    *      type="NotSupported"
     * @param recipentEmail
     * @param subject
     * @param body
     * @return
    */
    public boolean sendEmail(String recipentEmail, String subject, String body) {
        return false;
    }

    /**
     * @ejb.interface-method
     * @ejb.transaction
     *      type="NotSupported"
     *
     * @param subject
     * @param body
     * @return
     */
    public boolean sendToAllAttendees(String subject, String body) {
        return false;
    }

    /**
     * @ejb.interface-method
     * @ejb.transaction
     *      type="NotSupported"
     *
     * @param subject
     * @param body
     * @return
     */
    public boolean sendToAllPresenters(String subject, String body) {
        return false;
    }

    //==========================================
    //  EJB callbacks
    //==========================================

    /**
     * @ejb.create-method
     */
    public void ejbCreate() throws CreateException {
        log = Logger.getLogger(EmailServicesBean.class);
    }

    /**
     * DOCUMENT ME!
     *
     * @throws CreateException DOCUMENT ME!
     */
    public void ejbPostCreate() throws CreateException {
    }
}
