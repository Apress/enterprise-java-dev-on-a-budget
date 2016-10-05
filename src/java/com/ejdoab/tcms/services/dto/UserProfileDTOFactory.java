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

import com.ejdoab.tcms.entities.AddressLocal;
import com.ejdoab.tcms.entities.AddressLocalHome;
import com.ejdoab.tcms.entities.AddressUtil;
import com.ejdoab.tcms.entities.AttendeeLocal;
import com.ejdoab.tcms.entities.PresenterLocal;
import com.ejdoab.tcms.entities.UserLocal;
import com.ejdoab.tcms.entities.UserLocalHome;
import com.ejdoab.tcms.entities.UserUtil;
import com.ejdoab.tcms.services.dto.exceptions.*;
import com.ejdoab.tcms.services.exceptions.NoSuchUserException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import javax.naming.NamingException;


/**
 * @author Brian Sam-Bodden
 */
public class UserProfileDTOFactory implements DTOFactory {
    private static Log log = LogFactory.getLog(UserProfileDTOFactory.class);

    /**
     * Method updateProfile.
     * @param userProfile
     * @return boolean
     */
    private boolean updateProfile(UserLocal user, UserProfileDTO up)
        throws NoSuchUserException {
        boolean retValue = true;
        Integer id = up.getIdAsInteger();
        log.debug("[updateProfile] profile submitted with id =" + id);

        if (!id.equals(user.getId()) && (id.intValue() != -1)) {
            // this is not the right exception to throw
            throw new NoSuchUserException(
                "trying to update a user with the wrong profile");
        }

        try {
            AddressLocal address = user.getAddress();

            // if the user didn't have an address set the set it
            if (address == null) {
                AddressLocalHome alh = AddressUtil.getLocalHome();

                try {
                    address = alh.create();
                    user.setAddress(address);
                } catch (CreateException e) {
                    e.printStackTrace();
                }
            }

            if (address != null) {
                // do the address
                address.setAptNumber(up.getAddressAptNumber());
                address.setCity(up.getAddressCity());
                address.setState(up.getAddressState());
                address.setStreetAddress(up.getAddressStreet());
                address.setZipCode(up.getAddressZipCode());
            }

            // do other user fields 
            user.setEmail(up.getEmail());
            user.setFax(up.getFax());
            user.setFirstName(up.getFirstName());
            user.setHomePhone(up.getHomePhone());
            user.setLastName(up.getLastName());
            user.setPassword(up.getPassword());
            user.setWorkPhone(up.getWorkPhone());

            if (up.getUserType() == UserProfileDTO.UserType.PRESENTER) {
                PresenterLocal presenter = user.getPresenter();

                if (presenter != null) {
                    presenter.setBio(up.getBio());
                    presenter.setCompany(up.getCompany());
                }
            }
        } catch (NamingException e) {
            // can't find the home
            retValue = false;
        }

        return retValue;
    }

    /**
     * Method updateProfile.
     * @param userProfile
     * @return boolean
     */
    private boolean updateProfile(UserProfileDTO up) throws NoSuchUserException {
        boolean retValue = true;
        UserLocal user = null;
        Integer id = up.getIdAsInteger();

        if (id.intValue() == -1) {
            throw new NoSuchUserException(
                "trying to update a user with the wrong profile");
        }

        log.debug("[updateProfile] profile submitted with id =" + id);

        try {
            UserLocalHome ulh = UserUtil.getLocalHome();
            user = ulh.findByPrimaryKey(id);
        } catch (NamingException e) {
            // can't find the home
            retValue = false;
        } catch (FinderException e) {
            // can't find the user
            throw new NoSuchUserException(e,
                "there is no user with id " + up.getId() + " in the system");
        }

        if (user != null) {
            log.debug("[updateProfile] user is not null");

            return updateProfile(user, up);
        } else {
            log.debug("[updateProfile] user is null");

            return false;
        }
    }

    /**
     * Method getUserProfile.
     * @param attendee
     * @return UserProfile
     */
    private UserProfileDTO getUserProfile(AttendeeLocal attendee)
        throws DTOCreateException {
        int id = -1;
        String firstName = "";
        String lastName = "";
        String email = "";
        String password = "";
        String fax = "";
        String homePhone = "";
        String workPhone = "";
        String addressAptNumber = "";
        String addressCity = "";
        String addressState = "";
        String addressStreet = "";
        String addressZipCode = "";

        UserLocal user = attendee.getUser();

        if (user != null) {
            id = user.getId().intValue();
            firstName = user.getFirstName();
            lastName = user.getLastName();
            email = user.getEmail();
            password = user.getPassword();
            fax = user.getFax();
            homePhone = user.getHomePhone();
            workPhone = user.getWorkPhone();
        } else {
            // should never happen
            throw new DTOCreateException(
                "Can not find a user associated with attendee [" +
                attendee.getId() + "]");
        }

        AddressLocal address = user.getAddress();

        // do the address
        if (address != null) {
            addressAptNumber = address.getAptNumber();
            addressCity = address.getCity();
            addressState = address.getState();
            addressStreet = address.getStreetAddress();
            addressZipCode = address.getZipCode();
        }

        UserProfileDTO dto = new UserProfileDTO();
        dto.setAttendeeData(id, firstName, lastName, email, password, fax,
            homePhone, workPhone, addressAptNumber, addressCity, addressState,
            addressStreet, addressZipCode);

        return dto;
    }

    /**
     * Method getUserProfile.
     * @param presenter
     * @return UserProfile
     */
    private UserProfileDTO getUserProfile(PresenterLocal presenter)
        throws DTOCreateException {
        int id = -1;
        String firstName = "";
        String lastName = "";
        String email = "";
        String password = "";
        String fax = "";
        String homePhone = "";
        String workPhone = "";
        String addressAptNumber = "";
        String addressCity = "";
        String addressState = "";
        String addressStreet = "";
        String addressZipCode = "";
        String bio = "";
        String company = "";

        bio = presenter.getBio();
        company = presenter.getCompany();

        UserLocal user = presenter.getUser();

        if (user != null) {
            id = user.getId().intValue();
            firstName = user.getFirstName();
            lastName = user.getLastName();
            email = user.getEmail();
            password = user.getPassword();
            fax = user.getFax();
            homePhone = user.getHomePhone();
            workPhone = user.getWorkPhone();
        } else {
            // should never happen
            throw new DTOCreateException(
                "Can not find a user associated with attendee [" +
                presenter.getId() + "]");
        }

        AddressLocal address = user.getAddress();

        // do the address
        if (address != null) {
            addressAptNumber = address.getAptNumber();
            addressCity = address.getCity();
            addressState = address.getState();
            addressStreet = address.getStreetAddress();
            addressZipCode = address.getZipCode();
        }

        UserProfileDTO dto = new UserProfileDTO();
        dto.setPresenterData(id, firstName, lastName, email, password, fax,
            homePhone, workPhone, addressAptNumber, addressCity, addressState,
            addressStreet, addressZipCode, bio, company);

        return dto;
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
        if (dto instanceof UserProfileDTO) {
            UserProfileDTO userProfile = (UserProfileDTO) dto;

            try {
                return updateProfile(userProfile);
            } catch (NoSuchUserException nsue) {
                throw new DTOUpdateException("", nsue);
            }
        } else {
            throw new DTOUpdateException(
                "Attempting to update with an invalid DTO class");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param obj DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DTOCreateException DOCUMENT ME!
     */
    public DTO getDTO(Object obj) throws DTOCreateException {
        DTO dto = null;

        if (obj instanceof AttendeeLocal) {
            log.debug("[getDTO] Parameter is an Attendee");

            AttendeeLocal attendee = (AttendeeLocal) obj;
            dto = getUserProfile(attendee);
        } else if (obj instanceof PresenterLocal) {
            log.debug("[getDTO] Parameter is a Presenter");

            PresenterLocal presenter = (PresenterLocal) obj;
            dto = getUserProfile(presenter);
        } else {
            throw new DTOCreateException(
                "Attempting to create a DTO with an invalid source object");
        }

        return dto;
    }

    /* (non-Javadoc)
     * @see com.ejdoab.tcms.services.dto.DTOFactory#removeDTO(com.ejdoab.tcms.services.dto.DTO)
     */
    public boolean removeDTO(DTO dto) throws DTOUpdateException {
        // TODO Auto-generated method stub
        return false;
    }
}
