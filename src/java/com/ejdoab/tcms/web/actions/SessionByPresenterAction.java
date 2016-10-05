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

/*
 * Created on Dec 23, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ejdoab.tcms.web.actions;

import com.ejdoab.tcms.entities.PresenterLocal;
import com.ejdoab.tcms.services.*;
import com.ejdoab.tcms.services.dto.*;
import com.ejdoab.tcms.services.dto.page.*;

import org.apache.struts.action.*;

import java.util.*;

import javax.servlet.http.*;


/**
 * List sessions by presenter
 * @author cjudd
 */
public class SessionByPresenterAction extends Action {
    /**
     * List sessions by presenters
     *
     * @param mapping Action Mapping
     * @param form Action Form
     * @param request Http Request
     * @param response Http Response
     *
     * @return forward
     *
     * @throws Exception Any possible exception
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        ConferenceServicesLocalHome csHome = ConferenceServicesUtil.getLocalHome();
        ConferenceServicesLocal cs = csHome.create();

        UserServicesLocalHome usHome = UserServicesUtil.getLocalHome();
        UserServicesLocal us = usHome.create();

        String email = request.getParameter("presenter");

        PresenterLocal pl = us.findPresenterByEmail(email);

        UserProfileDTOFactory upf = new UserProfileDTOFactory();
        DTO presenterDTO = upf.getDTO(pl);

        request.setAttribute("presenter", presenterDTO);

        Page page = cs.getSessionsByPresenter(email, 0, 16);
        List sessions = new ArrayList();

        while (page.hasNext()) {
            sessions.add(page.next());
        }

        request.setAttribute("sessions", sessions);

        return (mapping.findForward("success"));
    }
}
