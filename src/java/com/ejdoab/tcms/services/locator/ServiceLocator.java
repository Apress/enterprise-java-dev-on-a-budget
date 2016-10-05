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

package com.ejdoab.tcms.services.locator;

import java.net.*;

import java.util.*;

import javax.ejb.*;

import javax.jms.*;

import javax.naming.*;

import javax.rmi.PortableRemoteObject;

import javax.sql.DataSource;


/**
 * @author Brian Sam-Bodden
 */
public class ServiceLocator {
    private static ServiceLocator instance;
    private InitialContext ic;
    private Map dataSourceCache = null;
    private Map queueConnectionFactoryCache = null;
    private Map queueCache = null;
    private Map localHomeCache = null;

    /**
     * Constructor for ServiceLocator.
     */
    private ServiceLocator() throws ServiceLocatorException {
        try {
            ic = new InitialContext();
            dataSourceCache = new HashMap();
            queueConnectionFactoryCache = new HashMap();
            queueCache = new HashMap();
            localHomeCache = new HashMap();
        } catch (NamingException ne) {
            throw new ServiceLocatorException(ne);
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }
    }

    /**
     * Constructor for ServiceLocator passing environment configuration
     * information.
     */
    public ServiceLocator(Hashtable env) throws ServiceLocatorException {
        try {
            ic = new InitialContext(env);
            dataSourceCache = new HashMap();
            queueConnectionFactoryCache = new HashMap();
            queueCache = new HashMap();
            localHomeCache = new HashMap();
        } catch (NamingException ne) {
            throw new ServiceLocatorException(ne);
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }
    }

    /**
     * The ServiceLocator is implemented as a Singleton.  The getInstance()
     * method will return the static reference to the ServiceLocator stored
     * inside of the ServiceLocator Class.
     *
     * @return
     * @throws ServiceLocatorException
     */
    public static ServiceLocator getInstance() throws ServiceLocatorException {
        if (instance == null) {
            instance = new ServiceLocator();
        }

        return instance;
    }

    /**
     * will get the ejb Remote home factory.
     * clients need to cast to the type of EJBHome they desire
     * @param jndiHomeName
     * @return the EJB Home corresponding to the homeName
     * @throws ServiceLocatorException
     */
    public EJBLocalHome getLocalHome(String jndiHomeName)
        throws ServiceLocatorException {
        EJBLocalHome home = null;

        try {
            Object obj = localHomeCache.get(jndiHomeName);

            if (obj == null) {
                obj = ic.lookup(jndiHomeName);
                localHomeCache.put(jndiHomeName, obj);
            }

            home = (EJBLocalHome) obj;
        } catch (NamingException ne) {
            throw new ServiceLocatorException(ne);
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }

        return home;
    }

    /**
     * will get the ejb Remote home factory.
     * clients need to cast to the type of EJBHome they desire
     *
     * @param jndiHomeName
     * @param className
     * @return the EJB Home corresponding to the homeName*
     * @throws ServiceLocatorException
     */
    public EJBHome getRemoteHome(String jndiHomeName, Class className)
        throws ServiceLocatorException {
        EJBHome home = null;

        try {
            Object objref = ic.lookup(jndiHomeName);
            Object obj = PortableRemoteObject.narrow(objref, className);
            home = (EJBHome) obj;
        } catch (NamingException ne) {
            throw new ServiceLocatorException(ne);
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }

        return home;
    }

    /**
     * @param qConnFactoryName
     * @return the factory for the factory to get queue connections from
     * @throws ServiceLocatorException
     */
    public QueueConnectionFactory getQueueConnectionFactory(
        String qConnFactoryName) throws ServiceLocatorException {
        QueueConnectionFactory factory = null;

        try {
            factory = (QueueConnectionFactory) ic.lookup(qConnFactoryName);
        } catch (NamingException ne) {
            throw new ServiceLocatorException(ne);
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }

        return factory;
    }

    /**
     * @param queueName
     * @return the Queue Destination to send messages to
     * @throws ServiceLocatorException
     */
    public Queue getQueue(String queueName) throws ServiceLocatorException {
        Queue queue = null;

        try {
            queue = (Queue) ic.lookup(queueName);
        } catch (NamingException ne) {
            throw new ServiceLocatorException(ne);
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }

        return queue;
    }

    /**
     * This method helps in obtaining the topic factory
     *
     * @param topicConnFactoryName
     * @return the factory for the factory to get topic connections from
     * @throws ServiceLocatorException
     */
    public TopicConnectionFactory getTopicConnectionFactory(
        String topicConnFactoryName) throws ServiceLocatorException {
        TopicConnectionFactory factory = null;

        try {
            factory = (TopicConnectionFactory) ic.lookup(topicConnFactoryName);
        } catch (NamingException ne) {
            throw new ServiceLocatorException(ne);
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }

        return factory;
    }

    /**
     * This method obtains the topc itself for a caller
     *
     * @param topicName
     * @return the Topic Destination to send messages to
     * @throws ServiceLocatorException*
     */
    public Topic getTopic(String topicName) throws ServiceLocatorException {
        Topic topic = null;

        try {
            topic = (Topic) ic.lookup(topicName);
        } catch (NamingException ne) {
            throw new ServiceLocatorException(ne);
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }

        return topic;
    }

    /**
     * This method obtains the datasource itself for a caller
     *
     * @param dataSourceName
     * @return the DataSource corresponding to the name parameter
     * @throws ServiceLocatorException
     */
    public DataSource getDataSource(String dataSourceName)
        throws ServiceLocatorException {
        DataSource dataSource = null;

        try {
            dataSource = (DataSource) ic.lookup(dataSourceName);
        } catch (NamingException ne) {
            throw new ServiceLocatorException(ne);
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }

        return dataSource;
    }

    /**
     * @param envName
     * @return the URL value corresponding to the env entry name.
     * @throws ServiceLocatorException
     */
    public URL getUrl(String envName) throws ServiceLocatorException {
        URL url = null;

        try {
            url = (URL) ic.lookup(envName);
        } catch (NamingException ne) {
            throw new ServiceLocatorException(ne);
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }

        return url;
    }

    /**
     * @param envName
     * @return the boolean value corresponding to the env entry such as SEND_CONFIRMATION_MAIL property.
     * @throws ServiceLocatorException
     */
    public boolean getBoolean(String envName) throws ServiceLocatorException {
        Boolean bool = null;

        try {
            bool = (Boolean) ic.lookup(envName);
        } catch (NamingException ne) {
            throw new ServiceLocatorException(ne);
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }

        return bool.booleanValue();
    }

    /**
     * @param envName
     * @return the String value corresponding
     * to the env entry name.
     * @throws ServiceLocatorException
     */
    public String getString(String envName) throws ServiceLocatorException {
        String envEntry = null;

        try {
            envEntry = (String) ic.lookup(envName);
        } catch (NamingException ne) {
            throw new ServiceLocatorException(ne);
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }

        return envEntry;
    }
}
