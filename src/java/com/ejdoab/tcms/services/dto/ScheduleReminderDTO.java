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

import com.ejdoab.tcms.util.HoursAndMinutes;

import java.util.Date;


/**
 * @author Brian Sam-Bodden
 */
public class ScheduleReminderDTO implements DTO {
    private int id = -1;
    private int entryId = -1;
    private String message;
    private Date when;
    private HoursAndMinutes hmBefore;

    /**
     * @param message
     * @param when
     */
    public ScheduleReminderDTO(String message, HoursAndMinutes hmBefore) {
        this.message = message;
        this.hmBefore = hmBefore;
    }

    /**
     * @param id
     * @param entryId
     * @param message
     * @param when
     */
    ScheduleReminderDTO(int id, int entryId, String message, Date when) {
        this.id = id;
        this.entryId = entryId;
        this.message = message;
        this.when = when;
    }

    /* (non-Javadoc)
     * @see com.ejdoab.tcms.services.dto.DTO#validate()
     */
    public DTOValidationResults validate() {
        // check that Date is before the Schedule Entry Id
        return DTOValidationResults.EMPTY_RESULTS;
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
    public String getMessage() {
        return message;
    }

    /**
     * @return
     */
    public Date getWhen() {
        return when;
    }

    /**
     * @return
     */
    public int getEntryId() {
        return entryId;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String toString() {
        String ts = "";

        if (when != null) {
            ts = when.toString();
        } else {
            ts = hmBefore.toString() + " before";
        }

        return new StringBuffer().append("id = ").append(id).append('\n')
                                 .append("for schedule entry = ").append(entryId)
                                 .append('\n').append("when = ").append(ts)
                                 .append('\n').append("reminder message = ")
                                 .append(message).append('\n').toString();
    }

    /**
     * @param i
     */
    void setEntryId(int i) {
        entryId = i;
    }

    /**
     * @return
     */
    public HoursAndMinutes getHmBefore() {
        return hmBefore;
    }
}
