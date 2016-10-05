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

import com.ejdoab.tcms.services.ConferenceServicesLocal;
import com.ejdoab.tcms.services.ConferenceServicesLocalHome;
import com.ejdoab.tcms.services.ConferenceServicesUtil;
import com.ejdoab.tcms.services.dto.page.Page;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * List conference sessions.
 * @author cjudd
 */
public class ListSessionsAction extends Action {
    /* (non-Javadoc)
     * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        ConferenceServicesLocalHome csHome = ConferenceServicesUtil.getLocalHome();
        ConferenceServicesLocal cs = csHome.create();

        //todo: Change to use variable count after page is fixed
        Page page = cs.getSessions(0, 1000);
        List sessions = new ArrayList();

        while (page.hasNext()) {
            sessions.add(page.next());
        }

        request.setAttribute("sessions", sessions);

        return (mapping.findForward("success"));
    }
}
