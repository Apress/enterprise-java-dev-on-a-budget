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

import java.io.Serializable;

import java.util.HashSet;
import java.util.Set;


/**
 * @author Brian Sam-Bodden
 */
public class UserProfileDTO implements DTO {
    private int id = -1;
    private UserType userType = null;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String fax;
    private String homePhone;
    private String workPhone;
    private String addressAptNumber;
    private String addressCity;
    private String addressState;
    private String addressStreet;
    private String addressZipCode;

    // presenter only fields
    private String bio;
    private String company;

    /**
     * DOCUMENT ME!
     *
     * @param id DOCUMENT ME!
     * @param firstName DOCUMENT ME!
     * @param lastName DOCUMENT ME!
     * @param email DOCUMENT ME!
     * @param password DOCUMENT ME!
     * @param fax DOCUMENT ME!
     * @param homePhone DOCUMENT ME!
     * @param workPhone DOCUMENT ME!
     * @param addressAptNumber DOCUMENT ME!
     * @param addressCity DOCUMENT ME!
     * @param addressState DOCUMENT ME!
     * @param addressStreet DOCUMENT ME!
     * @param addressZipCode DOCUMENT ME!
     * @param bio DOCUMENT ME!
     * @param company DOCUMENT ME!
     */
    public void setPresenterData(int id, String firstName, String lastName,
        String email, String password, String fax, String homePhone,
        String workPhone, String addressAptNumber, String addressCity,
        String addressState, String addressStreet, String addressZipCode,
        String bio, String company) {
        setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.fax = fax;
        this.homePhone = homePhone;
        this.workPhone = workPhone;
        this.addressAptNumber = addressAptNumber;
        this.addressCity = addressCity;
        this.addressState = addressState;
        this.addressStreet = addressStreet;
        this.addressZipCode = addressZipCode;
        this.bio = bio;
        this.company = company;

        try {
            setUserType(UserType.PRESENTER);
        } catch (NoSuchUserTypeException e) {
            e.printStackTrace();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param id DOCUMENT ME!
     * @param firstName DOCUMENT ME!
     * @param lastName DOCUMENT ME!
     * @param email DOCUMENT ME!
     * @param password DOCUMENT ME!
     * @param fax DOCUMENT ME!
     * @param homePhone DOCUMENT ME!
     * @param workPhone DOCUMENT ME!
     * @param addressAptNumber DOCUMENT ME!
     * @param addressCity DOCUMENT ME!
     * @param addressState DOCUMENT ME!
     * @param addressStreet DOCUMENT ME!
     * @param addressZipCode DOCUMENT ME!
     */
    public void setAttendeeData(int id, String firstName, String lastName,
        String email, String password, String fax, String homePhone,
        String workPhone, String addressAptNumber, String addressCity,
        String addressState, String addressStreet, String addressZipCode) {
        setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.fax = fax;
        this.homePhone = homePhone;
        this.workPhone = workPhone;
        this.addressAptNumber = addressAptNumber;
        this.addressCity = addressCity;
        this.addressState = addressState;
        this.addressStreet = addressStreet;
        this.addressZipCode = addressZipCode;

        try {
            setUserType(UserType.ATTENDEE);
        } catch (NoSuchUserTypeException e) {
            e.printStackTrace();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getId() {
        return this.id;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Integer getIdAsInteger() {
        return new Integer(id);
    }

    /**
     * DOCUMENT ME!
     *
     * @param id DOCUMENT ME!
     */
    public void setId(Integer id) {
        setId(id.intValue());
    }

    // Idempotent Method
    public void setId(int id) {
        if (this.id == -1) {
            this.id = id;
        }
    }

    // idempotent method 
    public void setUserType(UserType userType) throws NoSuchUserTypeException {
        if (!UserType.types.contains(userType)) {
            throw new NoSuchUserTypeException(userType.desc);
        }

        if (this.userType == null) {
            this.userType = userType;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String toString() {
        return new StringBuffer().append("userType = ").append(userType)
                                 .append('\n').append("firstName = ")
                                 .append(firstName).append('\n')
                                 .append("lastName = ").append(lastName)
                                 .append('\n').append("email = ").append(email)
                                 .append('\n').append("password = ")
                                 .append(password).append('\n').append("fax = ")
                                 .append(fax).append('\n').append("homePhone = ")
                                 .append(homePhone).append('\n')
                                 .append("workPhone = ").append(workPhone)
                                 .append('\n').append("addressAptNumber = ")
                                 .append(addressAptNumber).append('\n')
                                 .append("addressCity = ").append(addressCity)
                                 .append('\n').append("addressState = ")
                                 .append(addressState).append('\n')
                                 .append("addressStreet = ")
                                 .append(addressStreet).append('\n')
                                 .append("addressZipCode = ")
                                 .append(addressZipCode).append('\n').toString();
    }

    /**
     * Returns the addressAptNumber.
     * @return String
     */
    public String getAddressAptNumber() {
        return addressAptNumber;
    }

    /**
     * Returns the addressCity.
     * @return String
     */
    public String getAddressCity() {
        return addressCity;
    }

    /**
     * Returns the addressState.
     * @return String
     */
    public String getAddressState() {
        return addressState;
    }

    /**
     * Returns the addressStreet.
     * @return String
     */
    public String getAddressStreet() {
        return addressStreet;
    }

    /**
     * Returns the addressZipCode.
     * @return String
     */
    public String getAddressZipCode() {
        return addressZipCode;
    }

    /**
     * Returns the bio.
     * @return String
     */
    public String getBio() {
        return bio;
    }

    /**
     * Returns the email.
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the fax.
     * @return String
     */
    public String getFax() {
        return fax;
    }

    /**
     * Returns the firstName.
     * @return String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the homePhone.
     * @return String
     */
    public String getHomePhone() {
        return homePhone;
    }

    /**
     * Returns the lastName.
     * @return String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the password.
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the addressAptNumber.
     * @param addressAptNumber The addressAptNumber to set
     */
    public void setAddressAptNumber(String addressAptNumber) {
        this.addressAptNumber = addressAptNumber;
    }

    /**
     * Sets the addressCity.
     * @param addressCity The addressCity to set
     */
    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    /**
     * Sets the addressState.
     * @param addressState The addressState to set
     */
    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    /**
     * Sets the addressStreet.
     * @param addressStreet The addressStreet to set
     */
    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    /**
     * Sets the addressZipCode.
     * @param addressZipCode The addressZipCode to set
     */
    public void setAddressZipCode(String addressZipCode) {
        this.addressZipCode = addressZipCode;
    }

    /**
     * Sets the bio.
     * @param bio The bio to set
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Sets the email.
     * @param email The email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the fax.
     * @param fax The fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * Sets the firstName.
     * @param firstName The firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the homePhone.
     * @param homePhone The homePhone to set
     */
    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    /**
     * Sets the lastName.
     * @param lastName The lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the password.
     * @param password The password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public DTOValidationResults validate() {
        DTOValidationResults results = new DTOValidationResults();

        return results;
    }

    /**
     * Returns the company.
     * @return String
     */
    public String getCompany() {
        return company;
    }

    /**
     * Returns the workPhone.
     * @return String
     */
    public String getWorkPhone() {
        return workPhone;
    }

    /**
     * Sets the company.
     * @param company The company to set
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * Sets the workPhone.
     * @param workPhone The workPhone to set
     */
    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    /**
     * DOCUMENT ME!
     *
     * @author $author$
     * @version $Revision$
     */
    public static class UserType implements Serializable {
        /** DOCUMENT ME! */
        public static Set types = new HashSet();

        /** DOCUMENT ME! */
        public static UserType ATTENDEE = new UserType("Attendee");

        /** DOCUMENT ME! */
        public static UserType PRESENTER = new UserType("Presenter");

        static {
            types.add(ATTENDEE);
            types.add(PRESENTER);
        }

        private String desc;

        /**
         * Constructor UserType.
         * @param string
         */
        private UserType(String desc) {
            this.desc = desc;
        }

        /**
         * DOCUMENT ME!
         *
         * @return DOCUMENT ME!
         */
        public String toString() {
            return desc;
        }

        /**
         * DOCUMENT ME!
         *
         * @param o DOCUMENT ME!
         *
         * @return DOCUMENT ME!
         */
        public boolean equals(Object o) {
            if (o instanceof UserType) {
                return ((UserType) o).desc.equals(desc);
            } else {
                return false;
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @author $author$
     * @version $Revision$
     */
    public static class NoSuchUserTypeException extends Exception {
        /**
         * Constructor NoSuchUserTypeException.
         * @param string
         */
        public NoSuchUserTypeException(String message) {
            super(message);
        }
    }
}
