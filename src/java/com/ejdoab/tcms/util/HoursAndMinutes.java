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

import java.io.Serializable;


/**
 * Simple Value Object that Encapsulates a time representation in Hours and
 * Minutes
 *
 * @author Brian Sam-Bodden
 */
public class HoursAndMinutes implements Serializable {
    private long hours;
    private long minutes;

    /**
     * Creates a new HoursAndMinutes object.
     *
     * @param hours A long representing the hours
     * @param minutes A long representing the minutes
     */
    public HoursAndMinutes(long hours, long minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    /**
     * @return the hours value
     */
    public long getHours() {
        return hours;
    }

    /**
     * @return the minutes value
     */
    public long getMinutes() {
        return minutes;
    }

    /**
     * Returns in milliseconds the value of the hours and
     * minutes represented by this object
     *
     * @return long the hours and minutes in milliseconds
     */
    public long toMillis() {
        return (hours * Dates.MILLIS_PER_HOUR) +
        (minutes * Dates.MILLIS_PER_MINUTE);
    }

    /**
     * Returns a string representation of this object
     * in the format h H : m M where h represents the
     * hours and m represents the minutes
     *
     * @return a String representing the hours and minutes
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();

        if (hours > 0) {
            sb.append(hours).append(" H").append(" : ");
        }

        return sb.append(minutes).append(" M").toString();
    }
}
