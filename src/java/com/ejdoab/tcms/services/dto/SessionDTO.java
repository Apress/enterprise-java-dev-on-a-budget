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

import java.util.Date;


/**
 * @author Brian Sam-Bodden
 */
public class SessionDTO implements DTO {
    private int sessionId;
    private Date dtBegin;
    private Date dtEnd;
    private String title;
    private String type;
    private String topic;
    private String level;
    private String body;
    private String where;
    private String presenter;
    private String presenterId;

    /**
     * Creates a new SessionDTO object.
     *
     * @param sessionId DOCUMENT ME!
     * @param title DOCUMENT ME!
     * @param dtBegin DOCUMENT ME!
     * @param dtEnd DOCUMENT ME!
     * @param type DOCUMENT ME!
     * @param topic DOCUMENT ME!
     * @param level DOCUMENT ME!
     * @param body DOCUMENT ME!
     * @param where DOCUMENT ME!
     * @param presenter DOCUMENT ME!
     */
    public SessionDTO(int sessionId, String title, Date dtBegin, Date dtEnd,
        String type, String topic, String level, String body, String where,
        String presenter, String presenterId) {
        this.sessionId = sessionId;
        this.dtBegin = dtBegin;
        this.dtEnd = dtEnd;
        this.title = title;
        this.type = type;
        this.topic = topic;
        this.level = level;
        this.body = body;
        this.where = where;
        this.presenter = presenter;
        this.presenterId = presenterId;
    }

    /**
     * @return
     */
    public String getLevel() {
        return level;
    }

    /**
     * @return
     */
    public int getSessionId() {
        return sessionId;
    }

    /**
     * @return
     */
    public java.lang.String getTitle() {
        return title;
    }

    /**
     * @return
     */
    public String getTopic() {
        return topic;
    }

    /**
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * @return
     */
    public java.lang.String getBody() {
        return body;
    }

    /**
     * @return
     */
    public java.util.Date getDateTimeBegin() {
        return dtBegin;
    }

    /**
     * @return
     */
    public java.util.Date getDateTimeEnd() {
        return dtEnd;
    }

    /**
     * @return
     */
    public Date getDtBegin() {
        return dtBegin;
    }

    /**
     * @return
     */
    public Date getDtEnd() {
        return dtEnd;
    }

    /**
     * @return
     */
    public String getPresenter() {
        return presenter;
    }

    /**
     * @return
     */
    public String getPresenterId() {
        return presenterId;
    }

    /**
     * @return
     */
    public String getWhere() {
        return where;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getDurationInMinutes() {
        return (int) (dtEnd.getTime() - dtBegin.getTime()) / 60000;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("sessionId=").append(sessionId).append('\n').append("dtBegin=")
          .append(dtBegin).append('\n').append("dtEnd=").append(dtEnd)
          .append('\n').append("title=").append(title).append('\n')
          .append("type=").append(type).append('\n').append("topic=")
          .append(topic).append('\n').append("level=").append(level).append('\n')
          .append("body=").append(body).append('\n').append("where=")
          .append(where).append('\n').append("presenter=").append(presenter)
          .append('\n').append("presenter id=").append(presenterId).append('\n');

        return sb.toString();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public DTOValidationResults validate() {
        return new DTOValidationResults();
    }
}
