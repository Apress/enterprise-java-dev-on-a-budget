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

package com.ejdoab.tcms.services.dto.page;

import com.ejdoab.tcms.services.dto.DTOFactory;
import com.ejdoab.tcms.services.dto.exceptions.DTOCreateException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Factory for building Pages.
 * @author Brian Sam-Bodden
 */
public class PageFactory {
    private static Map methodCache = new HashMap();

    /** An empty class array */
    public static final Class[] EMPTY_CLASS_ARRAY = new Class[0];

    /** DOCUMENT ME! */
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    private static Log log = LogFactory.getLog(PageFactory.class);

    /**
     * DOCUMENT ME!
     *
     * @param collection DOCUMENT ME!
     * @param start DOCUMENT ME!
     * @param size DOCUMENT ME!
     * @param dtoBuilder DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DTOCreateException DOCUMENT ME!
     */
    public static Page buildPage(Collection collection, int start, int size,
        DTOFactory dtoBuilder) throws DTOCreateException {
        Page retVal = Page.EMPTY_PAGE;
        int totalSize = collection.size();
        log.debug("[buildPage] Collection size = " + totalSize);

        if ((size == 0) || (totalSize == 0)) {
            return retVal;
        } else {
            int returnArraySize = Math.min(size, totalSize - start);

            if (returnArraySize > 0) {
                log.debug("[buildPage] Return size is " + returnArraySize);

                List dataObjects = new ArrayList(returnArraySize);
                Object[] oa = new Object[returnArraySize];

                System.arraycopy(collection.toArray(), start, oa, 0,
                    returnArraySize);

                log.debug("[buildPage] oa.lenght = " + oa.length);

                for (int i = 0; i < oa.length; i++) {
                    Object data;
                    data = dtoBuilder.getDTO(oa[i]);
                    dataObjects.add(data);
                }

                log.trace("[buildPage] Before building return page");
                retVal = new Page(dataObjects, start, returnArraySize, totalSize);
                log.trace("[buildPage] Finished building page");
            }

            return retVal;
        }
    }
}
