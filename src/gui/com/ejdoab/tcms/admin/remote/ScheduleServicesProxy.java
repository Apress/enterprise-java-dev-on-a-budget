package com.ejdoab.tcms.admin.remote;

import com.ejdoab.tcms.services.IScheduleServices;
import com.ejdoab.tcms.services.dto.ScheduleDTO;
import com.ejdoab.tcms.services.exceptions.NoSuchUserException;

/**
 * @author Brian Sam-Bodden
 */
public class ScheduleServicesProxy implements IScheduleServices {

	/**
	 * 
	 */
	public ScheduleServicesProxy() {
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.ejdoab.tcms.services.IScheduleServices#getUserSchedule(java.lang.String)
	 */
	public ScheduleDTO getUserSchedule(String email)
		throws NoSuchUserException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ejdoab.tcms.services.IScheduleServices#setUserSchedule(com.ejdoab.tcms.services.dto.ScheduleDTO)
	 */
	public boolean setUserSchedule(ScheduleDTO dto) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.ejdoab.tcms.services.IScheduleServices#mailSchedule(java.lang.String, java.lang.String)
	 */
	public boolean mailSchedule(String userEmail, String recepientEmail) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.ejdoab.tcms.services.IScheduleServices#mailSchedule(java.lang.String)
	 */
	public boolean mailSchedule(String userEmail) {
		// TODO Auto-generated method stub
		return false;
	}

}
