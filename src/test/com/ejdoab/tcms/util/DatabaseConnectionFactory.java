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

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;

import org.dbunit.dataset.*;
import org.dbunit.dataset.xml.*;

import java.io.*;

import java.sql.*;

import java.util.*;


/**
 * Utility Factory for create DbUnit Database Connections
 * @author cjudd
 */
public class DatabaseConnectionFactory {
    /**
     * Creates a DbUnit database connection.
     * @return test database connection
     */
    public static IDatabaseConnection createConnection()
        throws Exception {
        Properties prop = new Properties();
        InputStream is = DatabaseConnectionFactory.class.getResourceAsStream(
                "/db.properties");
        prop.load(is);

        Class driverClass = Class.forName(prop.getProperty("db.driver"));
        Connection conn = DriverManager.getConnection(prop.getProperty("db.url"),
                prop.getProperty("db.user"), prop.getProperty("db.password"));

        return new DatabaseConnection(conn);
    }

    /**
     * Creates a DbUnit dataset based on a xml file.
     * @param xmlFile path to an xml file containing the dataset
     * @return requested dataset
     */
    public static IDataSet createDataSet(String xmlFile)
        throws Exception {
        InputStream is = DatabaseConnectionFactory.class.getClass()
                                                        .getResourceAsStream("/data/" +
                xmlFile);
        Reader r = new InputStreamReader(is);

        return new XmlDataSet(r);
    }

    /**
     * Create a DbUnit dataset representing the base dataset for all database tests.
     * @return base database set
     */
    public static IDataSet createBaseDataSet() throws Exception {
        return createDataSet("BaseDataSet.xml");
    }
}
