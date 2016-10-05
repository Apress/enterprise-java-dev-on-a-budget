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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;


/**
 * @author Brian Sam-Bodden
 */
public class Dates {
    /** DOCUMENT ME! */
    public static final int MILLIS_PER_HOUR = 1000 * 60 * 60;

    /** DOCUMENT ME! */
    public static final int MILLIS_PER_MINUTE = 1000 * 60;
    private static Log log = LogFactory.getLog(Dates.class);

    /** DOCUMENT ME! */
    public static final String DATE_MMddyyyy = "MM-dd-yyyy";

    /**
     * DOCUMENT ME!
     *
     * @param dtBegin DOCUMENT ME!
     * @param dtEnd DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static HoursAndMinutes calculateHoursAndMinutes(Date dtBegin,
        Date dtEnd) {
        long hours = 0;
        long minutes = 0;
        log.debug("[calculateHoursAndMinutes] dtBegin: " + dtBegin +
            " dtEnd: " + dtEnd);

        if ((dtBegin != null) && (dtEnd != null)) {
            long diffInMillis = dtEnd.getTime() - dtBegin.getTime();
            hours = diffInMillis / MILLIS_PER_HOUR;
            minutes = (diffInMillis % MILLIS_PER_HOUR) * MILLIS_PER_MINUTE;
        }

        return new HoursAndMinutes(hours, minutes);
    }

    /**
     * DOCUMENT ME!
     *
     * @param baseDate DOCUMENT ME!
     * @param offset DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Date getBefore(Date baseDate, HoursAndMinutes offset) {
        long dateInMillis = baseDate.getTime() - offset.toMillis();
        Date date = new Date(dateInMillis);

        return date;
    }

    /**
     * DOCUMENT ME!
     *
     * @param baseDate DOCUMENT ME!
     * @param offset DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Date getAfter(Date baseDate, HoursAndMinutes offset) {
        long dateInMillis = baseDate.getTime() + offset.toMillis();
        Date date = new Date(dateInMillis);

        return date;
    }

    /**
     * Format a date/time into a specific pattern.
     * @param date the date to format expressed in milliseconds.
     * @param pattern the pattern to use to format the date.
     * @return the formatted date.
     */
    public static String format(long date, String pattern) {
        return format(new Date(date), pattern);
    }

    /**
     * Format a date/time into a specific pattern.
     * @param date the date to format expressed in milliseconds.
     * @param pattern the pattern to use to format the date.
     * @return the formatted date.
     */
    public static String format(Date date, String pattern) {
        DateFormat df = createDateFormat(pattern);

        return df.format(date);
    }

    /**
     * return a lenient date format
     * @param pattern the pattern used for date/time formatting.
     * @return the configured format for this pattern.
     */
    public static DateFormat createDateFormat(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        return sdf;
    }

    /**
     * Method getDate
     *
     * Calendar is the preferred way to manipulate day and times in JDK1.2.X
     * and greater. This functions uses a Calendar object to generate a valid
     * Date object.
     * Sets the values for the fields year, month, and date.
     * Previous values of other fields are retained.  If this is not desired,
     * call <code>clear</code> first.
     *
     * @see <code>Calendar.set(int, int, int)</code>
     * @param day
     * @param year the value used to set the YEAR time field.
     * @param month the value used to set the MONTH time field.
     * @return a <code>Date</code> object for the parameters given
     */
    public static Date getDate(int month, int day, int year) {
        --month; // Month value is 0-based. e.g., 0 for January.

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, month, day);

        return cal.getTime();
    }

    /**
     * Method getDate
     *
     * Calendar is the preferred way to manipulate day and times in JDK1.2.X
     * and greater. This functions uses a Calendar object to generate a valid
     * Date object.
     * Sets the values for the fields year, month, and date.
     * Previous values of other fields are retained.  If this is not desired,
     * call <code>clear</code> first.
     *     month - the month between 1-12.
     * date - the day of the month between 1-31.
     * hrs - the hours between 0-23.
     * min - the minutes between 0-59.
     * sec - the seconds between 0-59.
     *
     * @see <code>Calendar.set(int, int, int)</code>
     * @param day
     * @param year the value used to set the YEAR time field.
     * @param month the value used to set the MONTH time field.
     * @param hour
     * @param minute
     * @param second
     * @return a <code>Date</code> object for the parameters given
     */
    public static Date getDate(int month, int day, int year, int hour,
        int minute, int second) {
        --month; // Month value is 0-based. e.g., 0 for January.

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);

        return cal.getTime();
    }

    /**
     * @param dateStr
     * @param format
     * @return
     */
    public static Date getDate(String dateStr, String format) {
        DateFormat df = createDateFormat(format);
        Date date = null;

        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            // do nothing null implies failure
        }

        return date;
    }

    /**
     * Method isInFormat
     * @param date
     * @param formatString
     * @return
     */
    public static boolean isInFormat(String date, String formatString) {
        try {
            DateFormat format = new SimpleDateFormat(formatString);
            format.setLenient(false);
            format.parse(date);

            return (date.length() == formatString.length());
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * @param dateStr1
     * @param dateStr2
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static boolean before(String dateStr1, String dateStr2,
        String pattern) throws ParseException {
        DateFormat format = Dates.createDateFormat(pattern);
        Date date1 = format.parse(dateStr1);
        Date date2 = format.parse(dateStr2);

        return date1.before(date2);
    }
}
