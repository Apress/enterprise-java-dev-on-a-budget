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

import java.util.HashMap;
import java.util.Map;


/**
 * @author Brian Sam-Bodden
 */
public class DTOAbstractFactory {
    private static DTOAbstractFactory instance = new DTOAbstractFactory();

    static {
        instance.dtoBuilders.put(UserProfileDTO.class,
            new UserProfileDTOFactory());
        instance.dtoBuilders.put(SessionDTO.class, new SessionDTOFactory());
        instance.dtoBuilders.put(ConferenceAbstractDTO.class,
            new ConferenceAbstractDTOFactory());
        instance.dtoBuilders.put(ScheduleDTO.class, new ScheduleDTOFactory());
        instance.dtoBuilders.put(ScheduleEntryDTO.class,
            new ScheduleEntryDTOFactory());
        instance.dtoBuilders.put(ScheduleReminderDTO.class,
            new ScheduleReminderDTOFactory());
        instance.dtoBuilders.put(NewsItemDTO.class, new NewsItemDTOFactory());
    }

    private Map dtoBuilders = new HashMap();

    /**
     * DOCUMENT ME!
     *
     * @param clazz DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public DTOFactory getDTOBuilder(Class clazz) {
        return (DTOFactory) dtoBuilders.get(clazz);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static DTOAbstractFactory getInstance() {
        return instance;
    }
}
