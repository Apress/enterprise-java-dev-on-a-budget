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
import com.ejdoab.tcms.entities.PresentationLocal;
import com.ejdoab.tcms.entities.RoomLocal;
import com.ejdoab.tcms.entities.SessionLocal;
import com.ejdoab.tcms.entities.UserLocal;
import com.ejdoab.tcms.services.dto.exceptions.*;

import java.util.Date;


/**
 * @author Brian Sam-Bodden
 */
public class SessionDTOFactory implements DTOFactory {
    private SessionDTO buildSessionDTO(SessionLocal session) {
        PresentationLocal presentation = session.getPresentation();
        ConferenceAbstractLocal cAbstract = presentation.getConferenceAbstract();
        UserLocal user = cAbstract.getPresenter().getUser();
        RoomLocal room = session.getRoom();

        int sessionId = session.getId().intValue();
        Date dtBegin = session.getDateTimeBegin();
        Date dtEnd = session.getDateTimeEnd();

        String title = cAbstract.getTitle();

        String type = StringCache.getType(cAbstract.getType());
        String topic = StringCache.getTopic(cAbstract.getTopic());
        String level = StringCache.getLevel(cAbstract.getLevel());
        String body = cAbstract.getBody();
        String where = room.getName();
        String presenter = user.getFirstName() + " " + user.getLastName();
        String presenterId = user.getEmail();

        SessionDTO dto = new SessionDTO(sessionId, title, dtBegin, dtEnd, type,
                topic, level, body, where, presenter, presenterId);

        return dto;
    }

    /* (non-Javadoc)
     * @see com.ejdoab.tcms.services.dto.DTOBuilder#getDTO(java.lang.Object)
     */
    public DTO getDTO(Object obj) throws DTOCreateException {
        if (obj instanceof SessionLocal) {
            try {
                SessionLocal session = (SessionLocal) obj;

                return buildSessionDTO(session);
            } catch (Exception e) {
                throw new DTOCreateException(e);
            }
        } else {
            throw new DTOCreateException(
                "invalid source object for DTO creation");
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
        return false;
    }

    /* (non-Javadoc)
     * @see com.ejdoab.tcms.services.dto.DTOFactory#removeDTO(com.ejdoab.tcms.services.dto.DTO)
     */
    public boolean removeDTO(DTO dto) throws DTOUpdateException {
        // TODO Auto-generated method stub
        return false;
    }
}
