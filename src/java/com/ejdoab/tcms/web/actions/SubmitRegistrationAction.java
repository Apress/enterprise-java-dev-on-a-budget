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

package com.ejdoab.tcms.web.actions;

import com.ejdoab.tcms.services.*;
import com.ejdoab.tcms.services.dto.*;
import com.ejdoab.tcms.services.exceptions.*;
import com.ejdoab.tcms.web.forms.*;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author cjudd
 */
public class SubmitRegistrationAction extends Action {
    /* (non-Javadoc)
     * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        ActionErrors errors = new ActionErrors();
        RegistrationForm rf = (RegistrationForm) form;

        UserServicesHome uslh = UserServicesUtil.getHome();
        UserServices us = uslh.create();

        UserProfileDTO attendee = new UserProfileDTO();

        try {
            attendee.setUserType(UserProfileDTO.UserType.ATTENDEE);
            attendee.setPassword(rf.getPassword());
            attendee.setFirstName(rf.getFirstname());
            attendee.setLastName(rf.getLastname());
            attendee.setEmail(rf.getEmail());
            attendee.setHomePhone(rf.getHomephone());
            attendee.setWorkPhone(rf.getWorkphone());
            attendee.setFax(rf.getFax());

            attendee.setAddressStreet(rf.getStreet());
            attendee.setAddressCity(rf.getCity());
            attendee.setAddressState(rf.getState());
            attendee.setAddressZipCode(rf.getZip());

            us.registerUser(attendee);
        } catch (DuplicateEmailException ex) {
            errors.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("error.register.duplicate"));
        }

        if (errors.isEmpty()) {
            return (mapping.findForward("success"));
        } else {
            request.setAttribute(Globals.ERROR_KEY, errors);

            return (mapping.findForward("failure"));
        }
    }
}
