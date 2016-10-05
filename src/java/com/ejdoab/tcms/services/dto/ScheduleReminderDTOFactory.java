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
import com.ejdoab.tcms.entities.ReminderLocalHome;
import com.ejdoab.tcms.entities.ReminderUtil;
import com.ejdoab.tcms.entities.ScheduleEntryLocal;
import com.ejdoab.tcms.entities.ScheduleEntryLocalHome;
import com.ejdoab.tcms.entities.ScheduleEntryUtil;
import com.ejdoab.tcms.entities.UserLocal;
import com.ejdoab.tcms.services.dto.exceptions.DTOCreateException;
import com.ejdoab.tcms.services.dto.exceptions.DTOUpdateException;
import com.ejdoab.tcms.util.Dates;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import javax.naming.NamingException;


/**
 * @author Brian Sam-Bodden
 */
public class ScheduleReminderDTOFactory implements DTOFactory {
    private static Log log = LogFactory.getLog(ScheduleReminderDTOFactory.class);

    /* (non-Javadoc)
     * @see com.ejdoab.tcms.services.dto.DTOBuilder#getDTO(java.lang.Object)
     */
    public DTO getDTO(Object obj) throws DTOCreateException {
        if (obj instanceof ReminderLocal) {
            try {
                ReminderLocal reminder = (ReminderLocal) obj;

                return buildScheduleReminderDTO(reminder);
            } catch (Exception e) {
                throw new DTOCreateException(e);
            }
        } else {
            throw new DTOCreateException(
                "invalid source object for DTO creation");
        }
    }

    /**
     * @param reminder
     * @return
     */
    private ScheduleReminderDTO buildScheduleReminderDTO(ReminderLocal reminder) {
        int id = reminder.getId().intValue();
        Date when = reminder.getDateAndTime();
        int entryId = reminder.getScheduleEntry().getId().intValue();
        String message = reminder.getMessage();

        return new ScheduleReminderDTO(id, entryId, message, when);
    }

    /* (non-Javadoc)
     * @see com.ejdoab.tcms.services.dto.DTOBuilder#saveDTO(com.ejdoab.tcms.services.dto.DTO)
     */
    public boolean setDTO(DTO dto) throws DTOUpdateException {
        if (dto instanceof ScheduleReminderDTO) {
            ScheduleReminderDTO reminder = (ScheduleReminderDTO) dto;

            return updateScheduleReminder(reminder);
        } else {
            throw new DTOUpdateException(
                "Attempting to update with an invalid DTO class");
        }
    }

    /**
     * @param reminder
     * @return
     */
    private boolean updateScheduleReminder(ScheduleReminderDTO dto)
        throws DTOUpdateException {
        boolean retValue = true;
        log.trace("[updateScheduleReminder]");

        ReminderLocal reminder = null;
        ScheduleEntryLocalHome selh = null;
        ReminderLocalHome rlh = null;

        try {
            rlh = ReminderUtil.getLocalHome();
        } catch (NamingException ne) {
            throw new DTOUpdateException("Error retrieving schedule reminder Information",
                ne);
        }

        try {
            selh = ScheduleEntryUtil.getLocalHome();
        } catch (NamingException ne) {
            throw new DTOUpdateException("Error retrieving schedule Information",
                ne);
        }

        Integer id = new Integer(dto.getId());
        Integer scheduleEntryId = new Integer(dto.getEntryId());
        String message = dto.getMessage();
        Date when = dto.getWhen();

        if (id.intValue() == -1) {
            ScheduleEntryLocal entry = null;

            try {
                entry = selh.findByPrimaryKey(scheduleEntryId);
            } catch (FinderException fe) {
                throw new DTOUpdateException(
                    "There is no schedule entry with id =" + scheduleEntryId, fe);
            }

            UserLocal user = entry.getUser();

            if (user == null) {
                throw new DTOUpdateException(
                    "There is no user associated with schedule entry= " +
                    scheduleEntryId);
            }

            try {
                if (when == null) {
                    when = Dates.getBefore(entry.getSession().getDateTimeBegin(),
                            dto.getHmBefore());
                }

                reminder = rlh.create(message, when, user, entry);
            } catch (CreateException ce) {
                throw new DTOUpdateException("Could not create schedule reminder",
                    ce);
            }
        } else {
            try {
                reminder = rlh.findByPrimaryKey(id);
            } catch (FinderException fe) {
                throw new DTOUpdateException("There is no reminder with id=" +
                    id, fe);
            }
        }

        if (reminder != null) {
            reminder.setDateAndTime(when);
            reminder.setMessage(message);
        } else {
            retValue = false;
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
