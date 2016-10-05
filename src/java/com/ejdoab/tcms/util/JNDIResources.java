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
public class JNDIResources {
    /** DOCUMENT ME! */
    public static final String QUEUE_CONNECTION_FACTORY = "java:comp/env/jms/tcms/QueueConnectionFactory";

    /** DOCUMENT ME! */
    public static final String EMAIL_SERVICE_QUEUE = "java:comp/env/jms/queue/tcms/mail";

    /** DOCUMENT ME! */
    public static final String EMAIL_SESSION = "java:comp/env/mail/tcms/MailSession";

    /** DOCUMENT ME! */
    public static final String DATASOURCE = "java:comp/env/jdbc/tcms";
}
