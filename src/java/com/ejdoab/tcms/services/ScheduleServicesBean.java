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

import com.ejdoab.tcms.entities.UserLocal;
import com.ejdoab.tcms.entities.UserLocalHome;
import com.ejdoab.tcms.services.dto.DTOAbstractFactory;
import com.ejdoab.tcms.services.dto.ScheduleDTO;
import com.ejdoab.tcms.services.dto.ScheduleDTOFactory;
import com.ejdoab.tcms.services.dto.exceptions.DTOCreateException;
import com.ejdoab.tcms.services.dto.exceptions.DTOUpdateException;
import com.ejdoab.tcms.services.exceptions.NoSuchUserException;
import com.ejdoab.tcms.services.locator.ServiceLocator;
import com.ejdoab.tcms.services.locator.ServiceLocatorException;

import org.apache.log4j.Logger;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;


/**
 * @author <a href="mailto:bsbodden@integrallis.com">Brian Sam-Bodden</a>
 *
 * @ejb.bean
 *      name="ScheduleServices"
 *      type="Stateless"
 *      view-type="both"
 *      jndi-name="ejb.ScheduleServicesHome"
 *      local-jndi-name="ejb.ScheduleServicesLocalHome"
 * @ejb.transaction
 *      type="Required"
 * @ejb.util
 *      generate="physical"
 */
public abstract class ScheduleServicesBean implements SessionBean {
    private Logger log;

    /**
     * @ejb.interface-method
     *
         * @param email
         * @return
         * @throws NoSuchUserException
         */
    public ScheduleDTO getUserSchedule(String email) throws NoSuchUserException {
        Object schedule = null;
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

        UserLocal user = us.findUserByEmail(email);

        ScheduleDTOFactory dtoBuilder = (ScheduleDTOFactory) DTOAbstractFactory.getInstance()
                                                                               .getDTOBuilder(ScheduleDTO.class);

        ScheduleDTO dto = null;

        try {
            dto = (ScheduleDTO) dtoBuilder.getDTO(user);
        } catch (DTOCreateException dce) {
            throw new EJBException("Error retrieving schedule for user " +
                email, dce);
        }

        return dto;
    }

    /**
     * @ejb.interface-method
     *
         * @param dto
         * @return
         */
    public boolean setUserSchedule(ScheduleDTO dto) {
        ScheduleDTOFactory dtoBuilder = (ScheduleDTOFactory) DTOAbstractFactory.getInstance()
                                                                               .getDTOBuilder(ScheduleDTO.class);

        try {
            return dtoBuilder.setDTO(dto);
        } catch (DTOUpdateException due) {
            throw new EJBException("Error saving schedule information", due);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.transaction
     *      type="NotSupported"
     *
     * @param userEmail
     * @param recepientEmail
     * @return
     */
    public boolean mailSchedule(String userEmail, String recepientEmail) {
        return false;
    }

    /**
     * @ejb.interface-method
     * @ejb.transaction
     *      type="NotSupported"
     *
     * @param userEmail
     * @return
     */
    public boolean mailSchedule(String userEmail) {
        return mailSchedule(userEmail, userEmail);
    }

    //==========================================
    //  EJB callbacks
    //==========================================

    /**
     * @ejb.create-method
     */
    public void ejbCreate() throws CreateException {
        log = Logger.getLogger(ScheduleServicesBean.class);
    }

    /**
     * DOCUMENT ME!
     *
     * @throws CreateException DOCUMENT ME!
     */
    public void ejbPostCreate() throws CreateException {
    }
}
