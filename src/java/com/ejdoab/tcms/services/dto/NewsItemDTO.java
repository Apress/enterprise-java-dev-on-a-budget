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
 * Created on Aug 20, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ejdoab.tcms.services.dto;

import java.util.Date;


/**
 * @author Brian Sam-Bodden
 */
public class NewsItemDTO implements DTO {
    private int newsItemId = -1;
    private Date date;
    private Date removedate;
    private Date creationdate;
    private boolean published;
    private String body;
    private String title;

    /**
     * Creates a new NewsItemDTO object.
     *
     * @param newsItemId DOCUMENT ME!
     * @param date DOCUMENT ME!
     * @param removedate DOCUMENT ME!
     * @param creationdate DOCUMENT ME!
     * @param published DOCUMENT ME!
     * @param body DOCUMENT ME!
     */
    public NewsItemDTO(int newsItemId, Date date, Date removedate,
        Date creationdate, boolean published, String title, String body) {
        this.newsItemId = newsItemId;
        this.date = date;
        this.removedate = removedate;
        this.creationdate = creationdate;
        this.published = published;
        this.title = title;
        this.body = body;
    }

    /* (non-Javadoc)
     * @see com.ejdoab.tcms.services.dto.DTO#validate()
     */
    public DTOValidationResults validate() {
        return new DTOValidationResults();
    }

    /**
     * @return
     */
    public String getBody() {
        return body;
    }

    /**
     * @return
     */
    public Date getCreationdate() {
        return creationdate;
    }

    /**
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     * @return
     */
    public int getNewsItemId() {
        return newsItemId;
    }

    /**
     * @return
     */
    public boolean isPublished() {
        return published;
    }

    /**
     * @return
     */
    public Date getRemovedate() {
        return removedate;
    }

    /**
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param string
     */
    public void setBody(String string) {
        body = string;
    }

    /**
     * @param date
     */
    public void setCreationDate(Date date) {
        creationdate = date;
    }

    /**
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @param b
     */
    public void setPublished(boolean b) {
        published = b;
    }

    /**
     * @param date
     */
    public void setRemoveDate(Date date) {
        removedate = date;
    }

    /**
     * @param string
     */
    public void setTitle(String string) {
        title = string;
    }
}
