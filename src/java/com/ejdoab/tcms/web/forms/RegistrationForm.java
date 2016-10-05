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

package com.ejdoab.tcms.web.forms;

import org.apache.struts.validator.*;


/**
 * Registration Form
 *
 * @author cjudd
 */
public class RegistrationForm extends ValidatorForm {
    private String city;
    private String email;
    private String fax;
    private String firstname;
    private String homephone;
    private String lastname;
    private String password;
    private String state;
    private String street;
    private String workphone;
    private String zip;

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getCity() {
        return city;
    }

    /**
     * DOCUMENT ME!
     *
     * @param city DOCUMENT ME!
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getEmail() {
        return email;
    }

    /**
     * DOCUMENT ME!
     *
     * @param email DOCUMENT ME!
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getFax() {
        return fax;
    }

    /**
     * DOCUMENT ME!
     *
     * @param fax DOCUMENT ME!
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * DOCUMENT ME!
     *
     * @param firstname DOCUMENT ME!
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getHomephone() {
        return homephone;
    }

    /**
     * DOCUMENT ME!
     *
     * @param homephone DOCUMENT ME!
     */
    public void setHomephone(String homephone) {
        this.homephone = homephone;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * DOCUMENT ME!
     *
     * @param lastname DOCUMENT ME!
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getPassword() {
        return password;
    }

    /**
     * DOCUMENT ME!
     *
     * @param password DOCUMENT ME!
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getState() {
        return state;
    }

    /**
     * DOCUMENT ME!
     *
     * @param state DOCUMENT ME!
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getStreet() {
        return street;
    }

    /**
     * DOCUMENT ME!
     *
     * @param street DOCUMENT ME!
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getWorkphone() {
        return workphone;
    }

    /**
     * DOCUMENT ME!
     *
     * @param workphone DOCUMENT ME!
     */
    public void setWorkphone(String workphone) {
        this.workphone = workphone;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getZip() {
        return zip;
    }

    /**
     * DOCUMENT ME!
     *
     * @param zip DOCUMENT ME!
     */
    public void setZip(String zip) {
        this.zip = zip;
    }
}
