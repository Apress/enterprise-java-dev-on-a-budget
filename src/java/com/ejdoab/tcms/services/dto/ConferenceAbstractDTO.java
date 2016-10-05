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


/**
 * @author Brian Sam-Bodden
 */
public class ConferenceAbstractDTO implements DTO {
    private int abstractId = -1;
    private String title = "";
    private String type = "";
    private String topic = "";
    private String level = "";
    private String body = "";
    private String status = "";
    private boolean hasPresentations = false;
    private String presenter = "";
    private String presenterEmail = "";

    ConferenceAbstractDTO(String title, String type, String topic,
        String level, String body, String status, boolean hasPresentations,
        String presenter, String presenterEmail) {
        //this.abstractId = abstractId;
        this.title = title;
        this.type = type;
        this.topic = topic;
        this.level = level;
        this.body = body;
        this.status = status;
        this.hasPresentations = hasPresentations;
        this.presenter = presenter;
        this.presenterEmail = presenterEmail;
    }

    /**
     * Creates a new ConferenceAbstractDTO object.
     *
     * @param title DOCUMENT ME!
     * @param type DOCUMENT ME!
     * @param topic DOCUMENT ME!
     * @param level DOCUMENT ME!
     * @param body DOCUMENT ME!
     * @param presenterEmail DOCUMENT ME!
     */
    public ConferenceAbstractDTO(String title, String type, String topic,
        String level, String body, String presenterEmail) {
        //this.abstractId = abstractId;
        this.title = title;
        this.type = type;
        this.topic = topic;
        this.level = level;
        this.body = body;
        this.presenterEmail = presenterEmail;
        hasPresentations = false;
        status = "";
    }

    /**
     * Returns the body.
     * @return java.lang.String
     */
    public String getBody() {
        return body;
    }

    /**
     * Returns the presenter.
     * @return String
     */
    public String getPresenter() {
        return presenter;
    }

    /**
     * Returns the status.
     * @return int
     */
    public String getStatus() {
        return status;
    }

    /**
     * Returns the title.
     * @return java.lang.String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the body.
     * @param body The body to set
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Sets the title.
     * @param title The title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns whether there is a presentation associated with
     * this Abstract
     * @return true if there is a presentation associated
     *         false otherwise
     */
    public boolean hasPresentations() {
        return hasPresentations;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("abstractId=").append(abstractId).append('\n').append("title=")
          .append(title).append('\n').append("type=").append(type).append('\n')
          .append("topic=").append(topic).append('\n').append("level=")
          .append(level).append('\n').append("body=").append(body).append('\n')
          .append("presenter=").append(presenter).append('\n');

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

    /**
     * Returns the abstractId.
     * @return int
     */
    public int getAbstractId() {
        return abstractId;
    }

    /**
     * Returns the level.
     * @return String
     */
    public String getLevel() {
        return level;
    }

    /**
     * Returns the topic.
     * @return String
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Returns the type.
     * @return String
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the level.
     * @param level The level to set
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * Sets the presenterEmail.
     * @param presenterEmail The presenterEmail to set
     */
    public void setPresenterEmail(String presenterEmail) {
        this.presenterEmail = presenterEmail;
    }

    /**
     * Returns the presenterEmail.
     * @return String
     */
    public String getPresenterEmail() {
        return presenterEmail;
    }

    /**
     * Idempotent and package access only
     * @param i
     */
    void setAbstractId(int id) {
        if (abstractId == -1) {
            abstractId = id;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param obj DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean equals(Object obj) {
        if (obj instanceof ConferenceAbstractDTO) {
            ConferenceAbstractDTO dto = (ConferenceAbstractDTO) obj;
            boolean equals = true;

            if (this.title == null) {
                equals = equals && (dto.title == null);
            } else {
                equals = equals && this.title.equals(dto.title);
            }

            if (this.type == null) {
                equals = equals && (dto.type == null);
            } else {
                equals = equals && this.body.equalsIgnoreCase(dto.type);
            }

            if (this.topic == null) {
                equals = equals && (dto.topic == null);
            } else {
                equals = equals && this.body.equalsIgnoreCase(dto.topic);
            }

            if (this.level == null) {
                equals = equals && (dto.level == null);
            } else {
                equals = equals && this.body.equalsIgnoreCase(dto.level);
            }

            if (this.body == null) {
                equals = equals && (dto.body == null);
            } else {
                equals = equals && this.body.equals(dto.body);
            }

            if (this.status == null) {
                equals = equals && (dto.status == null);
            } else {
                equals = equals && this.body.equalsIgnoreCase(dto.status);
            }

            if (this.presenterEmail == null) {
                equals = equals && (dto.presenterEmail == null);
            } else {
                equals = equals &&
                    this.body.equalsIgnoreCase(dto.presenterEmail);
            }

            return equals;
        } else {
            return false;
        }
    }

    /**
     * @param string
     */
    public void setStatus(String string) {
        status = string;
    }

    /**
     * @param string
     */
    public void setTopic(String string) {
        topic = string;
    }

    /**
     * @param string
     */
    public void setType(String string) {
        type = string;
    }
}
