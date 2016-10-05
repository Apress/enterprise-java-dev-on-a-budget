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

import com.ejdoab.tcms.entities.ScheduleEntryLocal;
import com.ejdoab.tcms.entities.UserLocal;
import com.ejdoab.tcms.entities.UserLocalHome;
import com.ejdoab.tcms.services.UserServicesLocal;
import com.ejdoab.tcms.services.UserServicesLocalHome;
import com.ejdoab.tcms.services.dto.exceptions.DTOCreateException;
import com.ejdoab.tcms.services.dto.exceptions.DTOUpdateException;
import com.ejdoab.tcms.services.dto.exceptions.ScheduleConflictException;
import com.ejdoab.tcms.services.exceptions.NoSuchUserException;
import com.ejdoab.tcms.services.locator.ServiceLocator;
import com.ejdoab.tcms.services.locator.ServiceLocatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.RemoveException;


/**
 * @author Brian Sam-Bodden
 */
public class ScheduleDTOFactory implements DTOFactory {
    private static Log log = LogFactory.getLog(ScheduleDTOFactory.class);

    /* (non-Javadoc)
     * @see com.ejdoab.tcms.services.dto.DTOBuilder#getDTO(java.lang.Object)
     */
    public DTO getDTO(Object obj) throws DTOCreateException {
        if (obj instanceof UserLocal) {
            try {
                UserLocal user = (UserLocal) obj;

                return buildScheduleDTO(user);
            } catch (Exception e) {
                e.printStackTrace();
                throw new DTOCreateException(e);
            }
        } else {
            throw new DTOCreateException(
                "invalid source object for DTO creation");
        }
    }

    /* (non-Javadoc)
     * @see com.ejdoab.tcms.services.dto.DTOBuilder#saveDTO(com.ejdoab.tcms.services.dto.DTO)
     */
    public boolean setDTO(DTO dto) throws DTOUpdateException {
        if (dto instanceof ScheduleDTO) {
            ScheduleDTO schedule = (ScheduleDTO) dto;

            return updateSchedule(schedule);
        } else {
            throw new DTOUpdateException(
                "Attempting to update with an invalid DTO class");
        }
    }

    /**
     * @param user
     * @return
     */
    private ScheduleDTO buildScheduleDTO(UserLocal user) {
        String email = user.getEmail();
        ScheduleDTO dto = new ScheduleDTO(email);
        Collection scheduleItems = user.getScheduleEntries();

        ScheduleEntryDTOFactory dtoBuilder = (ScheduleEntryDTOFactory) DTOAbstractFactory.getInstance()
                                                                                         .getDTOBuilder(ScheduleEntryDTO.class);

        Iterator i = scheduleItems.iterator();

        for (int index = 0; index < scheduleItems.size(); index++) {
            ScheduleEntryLocal sel = (ScheduleEntryLocal) i.next();

            try {
                ScheduleEntryDTO item = (ScheduleEntryDTO) dtoBuilder.getDTO(sel);
                dto.addScheduleEntry(item);
            } catch (DTOCreateException dce) {
                throw new EJBException(
                    "Error retrieving schedule items for user " + email, dce);
            } catch (ScheduleConflictException sce) {
                throw new EJBException("Schedule information for user " +
                    email + "is corrupted", sce);
            }
        }

        return dto;
    }

    /**
     * @param schedule
     * @return
     */
    private boolean updateSchedule(ScheduleDTO dto) throws DTOUpdateException {
        boolean retValue = true;
        UserLocal user = null;
        UserLocalHome ulh = null;
        UserServicesLocal us = null;
        ServiceLocator sl;

        try {
            sl = ServiceLocator.getInstance();

            UserServicesLocalHome usHome = (UserServicesLocalHome) sl.getLocalHome(
                    "ejb.UserServicesLocalHome");

            us = usHome.create();
        } catch (ServiceLocatorException e) {
            new EJBException("could not access user services", e);
        } catch (CreateException e) {
            new EJBException("could not access user services", e);
        }

        // find the user
        String email = dto.getUserEmail();

        try {
            user = us.findUserByEmail(email);
        } catch (NoSuchUserException nsue) {
            throw new DTOUpdateException("There is no user with email " +
                email, nsue);
        }

        ScheduleEntryDTOFactory dtoBuilder = (ScheduleEntryDTOFactory) DTOAbstractFactory.getInstance()
                                                                                         .getDTOBuilder(ScheduleEntryDTO.class);

        if (user != null) {
            // we need to do a comparison of the items in the schedule and those
            // in the database and remove any items that are in the database but 
            // not in the schedule			
            Collection c = user.getScheduleEntries();
            Iterator items = c.iterator();

            for (int index = 0; index < c.size(); index++) {
                ScheduleEntryLocal se = (ScheduleEntryLocal) items.next();
                int seId = se.getId().intValue();

                if (!dto.hasEntry(seId)) {
                    try {
                        log.debug(
                            "[updateSchedule] Removing deleted entry with id =" +
                            seId + " from the database");
                        se.remove();
                    } catch (RemoveException re) {
                        throw new DTOUpdateException(
                            "Could not remove schedule item with id" + seId, re);
                    }
                }
            }

            // loop through the schedule items
            Iterator dtos = dto.getEntries();

            for (int index = 0; index < dto.getEntryCount(); index++) {
                DTO item = (DTO) dtos.next();
                retValue = retValue && dtoBuilder.setDTO(item);
            }
        }

        return retValue;
    }

    /* (non-Javadoc)
     * @see com.ejdoab.tcms.services.dto.DTOFactory#removeDTO(com.ejdoab.tcms.services.dto.DTO)
     */
    public boolean removeDTO(DTO dto) throws DTOUpdateException {
        // TODO Auto-generated method stub
        return false;
    }
}
