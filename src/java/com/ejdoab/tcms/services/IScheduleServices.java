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

package com.ejdoab.tcms.services;

import com.ejdoab.tcms.services.dto.ScheduleDTO;
import com.ejdoab.tcms.services.exceptions.NoSuchUserException;


/**
 * TODO document me!
 * @author bms29
 */
public interface IScheduleServices {
    ScheduleDTO getUserSchedule(String email) throws NoSuchUserException;

    boolean setUserSchedule(ScheduleDTO dto);

    boolean mailSchedule(String userEmail, String recepientEmail);

    boolean mailSchedule(String userEmail);
}
