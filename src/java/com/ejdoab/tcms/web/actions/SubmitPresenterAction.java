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

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author cjudd
 */
public class SubmitPresenterAction extends Action {
    /* (non-Javadoc)
     * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        ActionErrors errors = new ActionErrors();
        DynaActionForm df = (DynaActionForm) form;

        UserServicesHome uslh = UserServicesUtil.getHome();
        UserServices us = uslh.create();

        UserProfileDTO presenter = new UserProfileDTO();

        try {
            presenter.setUserType(UserProfileDTO.UserType.PRESENTER);
            presenter.setPassword((String) df.get("password"));
            presenter.setFirstName((String) df.get("firstname"));
            presenter.setLastName((String) df.get("lastname"));
            presenter.setEmail((String) df.get("email"));
            presenter.setHomePhone((String) df.get("homephone"));
            presenter.setWorkPhone((String) df.get("workphone"));
            presenter.setFax((String) df.get("fax"));

            presenter.setAddressStreet((String) df.get("street"));
            presenter.setAddressCity((String) df.get("city"));
            presenter.setAddressState((String) df.get("state"));
            presenter.setAddressZipCode((String) df.get("zip"));

            us.registerUser(presenter);
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
