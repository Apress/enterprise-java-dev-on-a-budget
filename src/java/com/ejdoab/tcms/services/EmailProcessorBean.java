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

import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;

import javax.jms.Message;
import javax.jms.MessageListener;


/**
 * @author <a href="mailto:bsbodden@integrallis.com">Brian Sam-Bodden</a>
 *
 * @ejb.bean
 *      name="EmailProcessor"
 *      acknowledge-mode="Auto-acknowledge"
 *      destination-type="javax.jms.Queue"
 *      subscription-durability="Durable"
 *      transaction-type="Container"
 * @ejb.transaction
 *      type="NotSupported"
 * @ejb.resource-ref
 *      res-ref-name="${jndi.mail.session}"
 *      res-type="javax.mail.Session"
 *      res-auth="Container"
 *
 * @jboss.destination-jndi-name
 *      name="${jboss.queue.mail}"
 * @jboss.resource-ref
 *      res-ref-name="${jndi.mail.session}"
 *      jndi-name="${jboss.mail.session}"
 */
public class EmailProcessorBean implements MessageDrivenBean, MessageListener {
    /* (non-Javadoc)
     * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
     */
    public void onMessage(Message arg0) {
        // TODO Auto-generated method stub
    }

    //=============================================
    // EJB callbacks
    //=============================================
    public void ejbCreate() {
    }

    /* (non-Javadoc)
     * @see javax.ejb.MessageDrivenBean#setMessageDrivenContext(javax.ejb.MessageDrivenContext)
     */
    public void setMessageDrivenContext(MessageDrivenContext mdc) {
    }

    /* (non-Javadoc)
     * @see javax.ejb.MessageDrivenBean#ejbRemove()
     */
    public void ejbRemove() {
    }
}
