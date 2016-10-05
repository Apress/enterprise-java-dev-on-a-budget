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

import com.ejdoab.tcms.entities.ConferenceAbstractLocal;
import com.ejdoab.tcms.entities.ConferenceAbstractLocalHome;
import com.ejdoab.tcms.entities.ConferenceAbstractUtil;
import com.ejdoab.tcms.entities.PresenterLocal;
import com.ejdoab.tcms.entities.UserLocal;
import com.ejdoab.tcms.services.UserServicesLocal;
import com.ejdoab.tcms.services.UserServicesLocalHome;
import com.ejdoab.tcms.services.dto.exceptions.*;
import com.ejdoab.tcms.services.exceptions.NoSuchUserException;
import com.ejdoab.tcms.services.locator.ServiceLocator;
import com.ejdoab.tcms.services.locator.ServiceLocatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import javax.naming.NamingException;


/**
 * @author Brian Sam-Bodden
 */
public class ConferenceAbstractDTOFactory implements DTOFactory {
    private static Log log = LogFactory.getLog(ConferenceAbstractDTOFactory.class);

    private ConferenceAbstractDTO buildConferenceAbstractDTO(
        ConferenceAbstractLocal cAbstract) {
        UserLocal user = cAbstract.getPresenter().getUser();

        String title = cAbstract.getTitle();

        int abstractId = cAbstract.getId().intValue();

        String type = StringCache.getType(cAbstract.getType());
        String topic = StringCache.getTopic(cAbstract.getTopic());
        String level = StringCache.getLevel(cAbstract.getLevel());
        String status = StringCache.getAbstractStatus(cAbstract.getStatus());
        String body = cAbstract.getBody();
        String presenter = user.getFirstName() + " " + user.getLastName();
        String presenterEmail = user.getEmail();

        boolean hasPresentations = cAbstract.getPresentation() != null;

        ConferenceAbstractDTO dto = new ConferenceAbstractDTO(title, type,
                topic, level, body, status, hasPresentations, presenter,
                presenterEmail);
        dto.setAbstractId(abstractId);

        return dto;
    }

    /**
     * @see com.ejdoab.tcms.services.dto.DTOFactory#getDTO(Object)
     */
    public DTO getDTO(Object obj) throws DTOCreateException {
        if (obj instanceof ConferenceAbstractLocal) {
            try {
                ConferenceAbstractLocal cAbstract = (ConferenceAbstractLocal) obj;

                return buildConferenceAbstractDTO(cAbstract);
            } catch (Exception e) {
                throw new DTOCreateException(e);
            }
        } else {
            throw new DTOCreateException(
                "invalid source object for DTO construction");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param dto DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DTOUpdateException DOCUMENT ME!
     */
    public boolean setDTO(DTO dto) throws DTOUpdateException {
        if (dto instanceof ConferenceAbstractDTO) {
            log.trace("setDTO");

            ConferenceAbstractDTO cAbstract = (ConferenceAbstractDTO) dto;

            return updateAbstract(cAbstract);
        } else {
            throw new DTOUpdateException(
                "Attempting to update with an invalid DTO class");
        }
    }

    /**
     * Method updateAbstract.
     * @param cAbstract
     * @return boolean
     */
    private boolean updateAbstract(ConferenceAbstractDTO dto)
        throws DTOUpdateException {
        log.trace("updateAbstract");

        boolean retValue = false;
        boolean isNewSubmission = false;
        Integer id = new Integer(dto.getAbstractId());
        PresenterLocal presenter = null;

        log.debug("[updateAbstract] Abstract id is" + id);

        ConferenceAbstractLocalHome calh = null;

        try {
            calh = ConferenceAbstractUtil.getLocalHome();
        } catch (NamingException ne) {
            throw new DTOUpdateException("Exception getting ConferenceAbstractLocalHome",
                ne);
        }

        // get the service locator and find the UserServices
        UserServicesLocal us = null;
        ServiceLocator sl;

        try {
            sl = ServiceLocator.getInstance();

            UserServicesLocalHome usHome = (UserServicesLocalHome) sl.getLocalHome(
                    "ejb.UserServicesLocalHome");

            us = usHome.create();
        } catch (ServiceLocatorException sle) {
            throw new DTOUpdateException("ServiceLocator problem", sle);
        } catch (CreateException ce) {
            throw new DTOUpdateException("Create problem with UserServices", ce);
        }

        if (calh != null) {
            ConferenceAbstractLocal cal = null;

            if (id.intValue() == -1) {
                try {
                    // possible new abstract submission					
                    try {
                        presenter = us.findPresenterByEmail(dto.getPresenterEmail());
                        log.info(
                            "[updateAbstract] Presenter found for the abstract");

                        Collection c = presenter.getConferenceAbstracts();
                        Iterator i = c.iterator();
                        ConferenceAbstractLocal match = null;

                        for (int index = 0, n = c.size(); index < n; index++) {
                            ConferenceAbstractLocal anAbstract = (ConferenceAbstractLocal) i.next();

                            if (match(dto, anAbstract)) {
                                match = anAbstract;

                                break;
                            }
                        }

                        if (match != null) {
                            throw new DTOUpdateException(
                                "An abstract matching the submitted abstract already exists");
                        }
                    } catch (NoSuchUserException nsue) {
                        throw new DTOUpdateException(
                            "There is no Presenter with email =" +
                            dto.getPresenterEmail(), nsue);
                    }

                    log.info("[updateAbstract] Submission is a new abstract");
                    cal = calh.create();
                    isNewSubmission = true;
                } catch (CreateException ce) {
                    throw new DTOUpdateException("could not create a new abstract",
                        ce);
                }
            } else {
                try {
                    // update of an existing abstract
                    log.info(
                        "[updateAbstract] Submission is an existing abstract");
                    cal = calh.findByPrimaryKey(id);
                } catch (FinderException fe) {
                    throw new DTOUpdateException(
                        "could not find an abstract with id =" + id, fe);
                }
            }

            if (cal != null) {
                log.info("[updateAbstract] Abstract succesfully created!");

                cal.setBody(dto.getBody());
                cal.setTitle(dto.getTitle());
                cal.setLevel(StringCache.getLevelIndex(dto.getLevel()));
                log.info("level = " + dto.getLevel());
                cal.setTopic(StringCache.getTopicIndex(dto.getTopic()));
                log.info("topic = " + dto.getTopic());
                cal.setType(StringCache.getTypeIndex(dto.getType()));
                log.info("type = " + dto.getType());
                cal.setStatus(StringCache.getAbstractStatusIndex(
                        dto.getStatus()));
                log.info("status = " + dto.getStatus());

                if (isNewSubmission) {
                    if (presenter != null) {
                        cal.setPresenter(presenter);
                        retValue = true;
                        log.info(
                            "[updateAbstract] Presenter set for the abstract");
                    }
                } else {
                    retValue = true;
                }
            }
        }

        return retValue;
    }

    private boolean match(ConferenceAbstractDTO dto, ConferenceAbstractLocal cal) {
        boolean match = true;

        String type = StringCache.getType(cal.getType());
        String topic = StringCache.getTopic(cal.getTopic());
        String level = StringCache.getLevel(cal.getLevel());
        String body = cal.getBody();
        String title = cal.getTitle();

        // no duplicate titles accepted unless they are in different levels
        if (dto.getTitle() == null) {
            match = match && (title == null);
        } else {
            match = match && dto.getTitle().equalsIgnoreCase(title);

            if (dto.getLevel() == null) {
                match = match && (level == null);
            } else {
                match = match && dto.getLevel().equalsIgnoreCase(level);
            }
        }

        if (!match) {
            // no duplicate body text accepted
            if (dto.getBody() == null) {
                match = match && (body == null);
            } else {
                match = match && dto.getBody().equalsIgnoreCase(body);
            }
        }

        return match;
    }

    /* (non-Javadoc)
     * @see com.ejdoab.tcms.services.dto.DTOFactory#removeDTO(com.ejdoab.tcms.services.dto.DTO)
     */
    public boolean removeDTO(DTO dto) throws DTOUpdateException {
        // TODO Auto-generated method stub
        return false;
    }
}
