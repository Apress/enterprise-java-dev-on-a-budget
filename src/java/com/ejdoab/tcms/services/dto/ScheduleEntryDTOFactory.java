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

import com.ejdoab.tcms.entities.ReminderLocal;
import com.ejdoab.tcms.entities.ScheduleEntryLocal;
import com.ejdoab.tcms.entities.ScheduleEntryLocalHome;
import com.ejdoab.tcms.entities.ScheduleEntryUtil;
import com.ejdoab.tcms.entities.SessionLocal;
import com.ejdoab.tcms.entities.SessionLocalHome;
import com.ejdoab.tcms.entities.SessionUtil;
import com.ejdoab.tcms.entities.UserLocal;
import com.ejdoab.tcms.entities.UserLocalHome;
import com.ejdoab.tcms.services.UserServicesLocal;
import com.ejdoab.tcms.services.UserServicesLocalHome;
import com.ejdoab.tcms.services.dto.exceptions.DTOCreateException;
import com.ejdoab.tcms.services.dto.exceptions.DTOUpdateException;
import com.ejdoab.tcms.services.exceptions.NoSuchUserException;
import com.ejdoab.tcms.services.locator.ServiceLocator;
import com.ejdoab.tcms.services.locator.ServiceLocatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import javax.naming.NamingException;


/**
 * @author Brian Sam-Bodden
 */
public class ScheduleEntryDTOFactory implements DTOFactory {
    private static Log log = LogFactory.getLog(ScheduleEntryDTOFactory.class);

    /* (non-Javadoc)
     * @see com.ejdoab.tcms.services.dto.DTOBuilder#getDTO(java.lang.Object)
     */
    public DTO getDTO(Object obj) throws DTOCreateException {
        if (obj instanceof ScheduleEntryLocal) {
            try {
                ScheduleEntryLocal scheduleEntry = (ScheduleEntryLocal) obj;

                return buildScheduleItemDTO(scheduleEntry);
            } catch (Exception e) {
                throw new DTOCreateException(e);
            }
        } else {
            throw new DTOCreateException(
                "invalid source object for DTO creation");
        }
    }

    /**
     * @param scheduleEntry
     * @return
     */
    private ScheduleEntryDTO buildScheduleItemDTO(
        ScheduleEntryLocal scheduleEntry) throws DTOCreateException {
        int id = -1; // schedule Item id
        int userId = -1;
        int sessionId = -1;
        String name;
        String description;
        Date dtBegin;
        Date dtEnd;
        String userEmail;
        boolean reminders = false;

        id = scheduleEntry.getId().intValue();
        name = scheduleEntry.getName();
        description = scheduleEntry.getDescription();

        reminders = !scheduleEntry.getReminders().isEmpty();

        SessionLocal session = scheduleEntry.getSession();
        sessionId = session.getId().intValue();

        dtBegin = session.getDateTimeBegin();
        dtEnd = session.getDateTimeEnd();

        UserLocal user = scheduleEntry.getUser();
        userId = user.getId().intValue();
        userEmail = user.getEmail();

        ScheduleEntryDTO dto = new ScheduleEntryDTO(id, userId, sessionId,
                name, description, dtBegin, dtEnd, userEmail, reminders);

        // get the reminder dto builder		
        ScheduleReminderDTOFactory dtoBuilder = (ScheduleReminderDTOFactory) DTOAbstractFactory.getInstance()
                                                                                               .getDTOBuilder(ScheduleReminderDTO.class);

        // add the reminders		
        Collection c = scheduleEntry.getReminders();
        Iterator items = c.iterator();

        for (int index = 0; index < c.size(); index++) {
            ReminderLocal reminder = (ReminderLocal) items.next();
            ScheduleReminderDTO reminderDTO;
            reminderDTO = (ScheduleReminderDTO) dtoBuilder.getDTO(reminder);
            dto.addReminder(reminderDTO);
        }

        return dto;
    }

    /* (non-Javadoc)
     * @see com.ejdoab.tcms.services.dto.DTOBuilder#saveDTO(com.ejdoab.tcms.services.dto.DTO)
     */
    public boolean setDTO(DTO dto) throws DTOUpdateException {
        if (dto instanceof ScheduleEntryDTO) {
            ScheduleEntryDTO scheduleEntry = (ScheduleEntryDTO) dto;

            return updateScheduleEntry(scheduleEntry);
        } else {
            throw new DTOUpdateException(
                "Attempting to update with an invalid DTO class");
        }
    }

    private boolean updateScheduleEntry(ScheduleEntryDTO dto)
        throws DTOUpdateException {
        boolean retValue = true;
        log.debug("[updateScheduleEntry] Updating " + dto);

        ScheduleEntryLocal scheduleEntry = null;
        ScheduleEntryLocalHome selh = null;

        try {
            selh = ScheduleEntryUtil.getLocalHome();
        } catch (NamingException ne) {
            throw new DTOUpdateException("Error schedule entry Information", ne);
        }

        int dtoId = dto.getId();
        Integer id = new Integer(dtoId);
        Integer sessionId = new Integer(dto.getSessionId());
        String email = dto.getUserEmail();

        String name = dto.getName();
        String description = dto.getDescription();

        UserLocal user = null;
        SessionLocal session = null;

        if (dtoId == -1) {
            log.debug("[updateScheduleEntry] NEW schedule item with id " +
                dtoId);

            // --------------------
            // schedule item is new
            // --------------------
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
            try {
                user = us.findUserByEmail(email);
            } catch (NoSuchUserException nsue) {
                throw new DTOUpdateException("There is no user with email " +
                    email, nsue);
            }

            if (user != null) {
                // find the session
                try {
                    SessionLocalHome slh = SessionUtil.getLocalHome();
                    session = slh.findByPrimaryKey(sessionId);
                } catch (NamingException ne) {
                    throw new DTOUpdateException("Error accessing Session Information",
                        ne);
                } catch (FinderException fe) {
                    throw new DTOUpdateException("No session found with id =" +
                        sessionId, fe);
                }

                if (session != null) {
                    if (name == null) {
                        name = session.getPresentation().getConferenceAbstract()
                                      .getTitle();
                    }

                    try {
                        scheduleEntry = selh.create(name, description, session,
                                user);
                    } catch (CreateException ce) {
                        throw new DTOUpdateException("Error creating schedule entry",
                            ce);
                    }
                } else {
                    retValue = false;
                }
            } else {
                retValue = false;
            }
        } else {
            // schedule item already exists
            // only things you can update are the name and description
            System.out.println(
                "[ScheduleItemDTOBuilder] EXISTING schedule item with id " +
                dtoId);

            try {
                scheduleEntry = selh.findByPrimaryKey(id);
            } catch (FinderException fe) {
                throw new DTOUpdateException("Schedule Item with id =" + id +
                    " does not exist", fe);
            }

            if (scheduleEntry != null) {
                if (!name.equals(scheduleEntry.getName())) {
                    scheduleEntry.setName(name);
                }

                if (!description.equals(scheduleEntry.getDescription())) {
                    scheduleEntry.setDescription(description);
                }
            } else {
                retValue = false;
            }
        }

        if (retValue) {
            // we need to do a comparison of the items in the schedule and those
            // in the database and remove any items that are in the database but 
            // not in the schedule	
            System.out.println(
                "[ScheduleItemDTOBuilder] retValue before reminder section is " +
                retValue);

            Collection c = scheduleEntry.getReminders();
            Iterator items = c.iterator();

            for (int index = 0; index < c.size(); index++) {
                ReminderLocal reminder = (ReminderLocal) items.next();
                int reminderId = reminder.getId().intValue();

                if (!dto.hasReminder(reminderId)) {
                    try {
                        reminder.remove();
                    } catch (RemoveException re) {
                        throw new DTOUpdateException(
                            "Could not remove schedule reminder with id" +
                            reminderId, re);
                    }
                }
            }

            ScheduleReminderDTOFactory dtoBuilder = (ScheduleReminderDTOFactory) DTOAbstractFactory.getInstance()
                                                                                                   .getDTOBuilder(ScheduleReminderDTO.class);

            // loop through the reminder and save them
            System.out.println(
                "[ScheduleItemDTOBuilder] before saving reminders");

            Iterator dtos = dto.getRemindersIterator();
            System.out.println("[ScheduleItemDTOBuilder] reminders count =" +
                dto.getRemindersCount());

            for (int index = 0; index < dto.getRemindersCount(); index++) {
                ScheduleReminderDTO reminderDTO = (ScheduleReminderDTO) dtos.next();

                if (reminderDTO.getEntryId() == -1) {
                    reminderDTO.setEntryId(scheduleEntry.getId().intValue());
                }

                retValue = retValue && dtoBuilder.setDTO(reminderDTO);
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
