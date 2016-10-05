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

import com.ejdoab.tcms.util.Dates;
import com.ejdoab.tcms.util.Validation;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * @author Brian Sam-Bodden
 */
public class ScheduleEntryDTO implements DTO, Comparable {
    private int id = -1; // schedule Item id
    private int userId = -1;
    private int sessionId = -1;
    private String name;
    private String description;
    private Date dtBegin = new Date(Long.MAX_VALUE);
    private Date dtEnd = new Date(Long.MAX_VALUE);
    private String userEmail;
    private List reminders = new ArrayList();

    /**
     * @param id
     * @param userId
     * @param sessionId
     * @param name
     * @param description
     * @param dtBegin
     * @param dtEnd
     * @param userEmail
     * @param reminders
     */
    ScheduleEntryDTO(int id, int userId, int sessionId, String name,
        String description, Date dtBegin, Date dtEnd, String userEmail,
        boolean reminders) {
        this.id = id;
        this.userId = userId;
        this.sessionId = sessionId;
        this.name = name;
        this.description = description;
        this.dtBegin = dtBegin;
        this.dtEnd = dtEnd;
        this.userEmail = userEmail;
    }

    /**
     * @param sessionId
     * @param name
     * @param description
     * @param userEmail
     */
    public ScheduleEntryDTO(int sessionId, String name, String description,
        String userEmail) {
        this.sessionId = sessionId;
        this.name = name;
        this.description = description;
        this.userEmail = userEmail;
    }

    /**
     * @param sessionId
     * @param name
     * @param description
     * @param userEmail
     */
    public ScheduleEntryDTO(int sessionId, String userEmail, String description) {
        this.sessionId = sessionId;
        this.userEmail = userEmail;
        this.description = description;
    }

    /* (non-Javadoc)
     * @see com.ejdoab.tcms.services.dto.DTO#validate()
     */
    public DTOValidationResults validate() {
        DTOValidationResults results = new DTOValidationResults();

        if (dtEnd.before(dtBegin)) {
            results.addValidationError(
                "end date-time must be after beginning date-time");
        }

        if (dtEnd.equals(dtBegin)) {
            results.addValidationError(
                "end date-time and beginning date-time can not be equal");
        }

        if (Validation.validateEmail(userEmail)) {
            results.addValidationError("email " + userEmail +
                " is not a valid email");
        }

        if (sessionId == -1) {
            results.addValidationError(
                "a schedule item must be associated with a session");
        }

        return results;
    }

    /**
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Returns 0 if the schedule items overlap in time (however slightly)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object obj) {
        if (obj instanceof ScheduleEntryDTO) {
            ScheduleEntryDTO compareTo = (ScheduleEntryDTO) obj;

            if (compareTo.getSessionId() == sessionId) {
                return 0;
            } else if (compareTo.dtBegin.after(this.dtEnd)) {
                return -1;
            } else if (compareTo.dtEnd.before(this.dtBegin)) {
                return 1;
            } else {
                return 0;
            }
        } else if (obj instanceof Date) {
            Date compareTo = (Date) obj;

            if (compareTo.after(this.dtEnd)) {
                return -1;
            } else if (compareTo.before(this.dtBegin)) {
                return 1;
            } else {
                return 0;
            }
        } else {
            throw new ClassCastException();
        }
    }

    /**
     * @return
     */
    public Date getDtBegin() {
        return dtBegin;
    }

    /**
     * @return
     */
    public Date getDtEnd() {
        return dtEnd;
    }

    /**
     * @return
     */
    public int getSessionId() {
        return sessionId;
    }

    /**
     * @return
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * @return
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param i
     */
    public void setSessionId(int i) {
        sessionId = i;
    }

    /**
     * @param string
     */
    public void setUserEmail(String string) {
        userEmail = string;
    }

    /**
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param string
     */
    public void setDescription(String string) {
        description = string;
    }

    /**
     * @param string
     */
    public void setName(String string) {
        name = string;
    }

    /**
     * @param low
     * @param high
     * @return
     */
    public boolean isInRange(Date low, Date high) {
        return !(dtEnd.before(low) || dtBegin.after(high));
    }

    /**
     * @param reminder
     * @return
     */
    public boolean addReminder(ScheduleReminderDTO reminder) {
        int entryId = reminder.getEntryId();

        if (id != -1) {
            if (entryId == -1) {
                reminder.setEntryId(this.id);
            } else if (entryId != id) {
                return false; // should we throw and exception instead?
            }
        } else {
            if (entryId != -1) {
                return false; // should we throw and exception instead?
            }
        }

        return reminders.add(reminder);
    }

    /**
     * @return
     */
    public Iterator getRemindersIterator() {
        return reminders.iterator();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new StringBuffer().append("id = ").append(id).append('\n')
                                 .append("name = ").append(name).append('\n')
                                 .append("session id = ").append(sessionId)
                                 .append('\n').append("description = ")
                                 .append(description).append('\n')
                                 .append("user = ").append(userEmail)
                                 .append('\n').append("dtBegin = ")
                                 .append(dtBegin).append('\n').append("dtEnd = ")
                                 .append(dtEnd).append('\n')
                                 .append("duration = ")
                                 .append(Dates.calculateHoursAndMinutes(
                dtBegin, dtEnd)).append('\n').append("reminders = \n")
                                 .append(getRemindersAsString()).append('\n')
                                 .toString();
    }

    /**
     * @param reminderId
     * @return
     */
    public boolean hasReminder(int reminderId) {
        boolean retVal = false;
        Iterator i = reminders.iterator();

        for (int index = 0; index < reminders.size(); index++) {
            ScheduleReminderDTO reminder = (ScheduleReminderDTO) i.next();

            if (reminder.getId() == reminderId) {
                retVal = true;

                break;
            }
        }

        return retVal;
    }

    /**
     * @return
     */
    public int getRemindersCount() {
        return reminders.size();
    }

    private String getRemindersAsString() {
        StringBuffer sb = new StringBuffer();
        Iterator i = reminders.iterator();

        for (int index = 0; index < reminders.size(); index++) {
            ScheduleReminderDTO reminder = (ScheduleReminderDTO) i.next();
            sb.append(reminder);
        }

        return sb.toString();
    }
}
