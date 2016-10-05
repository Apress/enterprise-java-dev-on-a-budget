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

import com.ejdoab.tcms.services.dto.exceptions.ScheduleConflictException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 * @author Brian Sam-Bodden
 */
public class ScheduleDTO implements DTO {
    private static Log log = LogFactory.getLog(ScheduleDTO.class);
    private SortedSet entries = new TreeSet();
    private String userEmail;

    /**
     * @param userEmail
     */
    public ScheduleDTO(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * @param item
     * @return
     * @throws ScheduleConflictException
     */
    public void addScheduleEntry(ScheduleEntryDTO item)
        throws ScheduleConflictException {
        log.debug("[addScheduleEntry] Item user email =" + item.getUserEmail() +
            " userEmail =" + userEmail);

        if (!item.getUserEmail().equals(userEmail)) {
            throw new ScheduleConflictException(
                "trying to insert an item into a schedule for a different user");
        }

        boolean added = entries.add(item);

        if (!added) {
            throw new ScheduleConflictException(
                "could not add entry to the schedule, duplicate entry");
        }
    }

    /**
     * @return
     */
    public Iterator getEntries() {
        return entries.iterator();
    }

    /**
     * @param rangeLow
     * @param rangeHi
     * @return
     */
    public Iterator getScheduleEntries(Date rangeLow, Date rangeHi) {
        return entries.subSet(rangeLow, rangeHi).iterator();
    }

    /**
     * @param dateTime
     * @return
     */
    public ScheduleEntryDTO getScheduleEntry(Date dateTime) {
        SortedSet ss = entries.subSet(dateTime, dateTime);
        ScheduleEntryDTO dto = (ScheduleEntryDTO) ss.first();

        return dto;
    }

    /* (non-Javadoc)
     * @see com.ejdoab.tcms.services.dto.DTO#validate()
     */
    public DTOValidationResults validate() {
        // guaranteed to be valid
        return DTOValidationResults.EMPTY_RESULTS;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        Iterator i = entries.iterator();
        StringBuffer sb = new StringBuffer();
        sb.append("schedule for user ").append(userEmail).append('\n');

        for (int index = 0; index < entries.size(); index++) {
            sb.append(i.next()).append('\n').append(index)
              .append(" ************").append('\n');
        }

        return sb.toString();
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
    public int getEntryCount() {
        return entries.size();
    }

    /**
     * @param seId
     * @return
     */
    public boolean hasEntry(int seId) {
        boolean retVal = false;
        Iterator i = entries.iterator();

        for (int index = 0; index < entries.size(); index++) {
            ScheduleEntryDTO si = (ScheduleEntryDTO) i.next();

            if (si.getId() == seId) {
                retVal = true;

                break;
            }
        }

        return retVal;
    }

    /**
     * @param seId
     * @return
     */
    public boolean removeScheduleEntry(int seId) {
        ScheduleEntryDTO se = getScheduleEntry(seId);

        return removeScheduleEntry(se);
    }

    /**
     * @param se
     * @return
     */
    public boolean removeScheduleEntry(ScheduleEntryDTO se) {
        return entries.remove(se);
    }

    /**
     * @param seId
     * @return
     */
    public ScheduleEntryDTO getScheduleEntry(int seId) {
        ScheduleEntryDTO retVal = null;
        Iterator i = entries.iterator();

        for (int index = 0; index < entries.size(); index++) {
            ScheduleEntryDTO si = (ScheduleEntryDTO) i.next();

            if (si.getId() == seId) {
                retVal = si;

                break;
            }
        }

        return retVal;
    }
}
