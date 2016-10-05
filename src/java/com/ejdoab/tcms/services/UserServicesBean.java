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

package com.ejdoab.tcms.services;

import com.ejdoab.tcms.entities.AttendeeLocal;
import com.ejdoab.tcms.entities.AttendeeLocalHome;
import com.ejdoab.tcms.entities.AttendeeUtil;
import com.ejdoab.tcms.entities.PresenterLocal;
import com.ejdoab.tcms.entities.PresenterLocalHome;
import com.ejdoab.tcms.entities.PresenterUtil;
import com.ejdoab.tcms.entities.UserLocal;
import com.ejdoab.tcms.entities.UserLocalHome;
import com.ejdoab.tcms.entities.UserRoleLocalHome;
import com.ejdoab.tcms.entities.UserRoleUtil;
import com.ejdoab.tcms.entities.UserUtil;
import com.ejdoab.tcms.services.dto.DTOAbstractFactory;
import com.ejdoab.tcms.services.dto.UserProfileDTO;
import com.ejdoab.tcms.services.dto.UserProfileDTOFactory;
import com.ejdoab.tcms.services.dto.exceptions.DTOCreateException;
import com.ejdoab.tcms.services.dto.exceptions.DTOUpdateException;
import com.ejdoab.tcms.services.exceptions.DuplicateEmailException;
import com.ejdoab.tcms.services.exceptions.NoSuchUserException;
import com.ejdoab.tcms.services.locator.ServiceLocator;
import com.ejdoab.tcms.services.locator.ServiceLocatorException;

import org.apache.log4j.Logger;

import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;

import javax.naming.NamingException;


/**
 * @author <a href="mailto:bsbodden@integrallis.com">Brian Sam-Bodden</a>
 *
 * @ejb.bean
 *      name="UserServices"
 *      type="Stateless"
 *      view-type="both"
 *      jndi-name="ejb.UserServicesHome"
 *      local-jndi-name="ejb.UserServicesLocalHome"
 * @ejb.transaction
 *      type="Required"
 * @ejb.util
 *      generate="physical"
 */
public abstract class UserServicesBean implements SessionBean {
    private Logger log;

    /**
     * @ejb.interface-method
     * @ejb.transaction
     *      type="NotSupported"
     *
     * @param email
     * @param password
     * @return
     * @throws NoSuchUserException
     */
    public boolean authenticate(String email, String password)
        throws NoSuchUserException {
        UserLocal user = findUserByEmail(email);

        return (password == null) ? (user.getPassword() == null)
                                  : password.equals(user.getPassword());
    }

    /**
     * @ejb.interface-method
     * @ejb.transaction
     *      type="NotSupported"
     *
     * @param email
     * @throws NoSuchUserException
     */
    public void requestPassword(String email) throws NoSuchUserException {
        UserLocal user = findUserByEmail(email);

        ServiceLocator sl;

        try {
            sl = ServiceLocator.getInstance();

            EmailServicesLocalHome esHome = (EmailServicesLocalHome) sl.getLocalHome(
                    "ejb.EmailServicesLocalHome");

            EmailServicesLocal es = esHome.create();
            es.sendEmail(email, "Your password",
                "you password is " + user.getPassword());
        } catch (ServiceLocatorException e) {
            new EJBException("could not access user services", e);
        } catch (CreateException e) {
            new EJBException("could not access user services", e);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.transaction
     *      type="NotSupported"
     *
     * @param up
     * @return
     * @throws DuplicateEmailException
     * @throws CreateException
     * @throws NamingException
     */
    public boolean registerUser(UserProfileDTO up)
        throws DuplicateEmailException, CreateException, NamingException {
        boolean success = true;

        // see if this email is already in use
        String email = up.getEmail();
        log.info("[register] email parameter is =" + email);

        if (email != null) {
            UserLocal user = createNewUser(email);
            up.setId(user.getId());
            log.info("[register] after createNewUser");

            // new user is an attendee
            log.info("[register] user type is " + up.getUserType());

            if (up.getUserType().equals(UserProfileDTO.UserType.ATTENDEE)) {
                AttendeeLocalHome home = AttendeeUtil.getLocalHome();
                AttendeeLocal attendee = home.create(user);
                log.info("[register] after creating new attendee");

                if (attendee != null) {
                    log.info("[register] attendee is not null");

                    UserRoleLocalHome urh = UserRoleUtil.getLocalHome();
                    urh.create(0, user);
                    log.info("[register] user role created");
                } else {
                    success = false;
                }

                // new user is a presenter
            } else if (up.getUserType().equals(UserProfileDTO.UserType.PRESENTER)) {
                PresenterLocalHome home = PresenterUtil.getLocalHome();
                PresenterLocal presenter = home.create();
                presenter.setUser(user);

                if (presenter != null) {
                    UserRoleLocalHome urh = UserRoleUtil.getLocalHome();
                    urh.create(1, user);
                } else {
                    success = false;
                }
            }

            if (success) {
                try {
                    UserProfileDTOFactory builder = (UserProfileDTOFactory) DTOAbstractFactory.getInstance()
                                                                                              .getDTOBuilder(UserProfileDTO.class);
                    builder.setDTO(up);
                    log.info("[register] after update profile");
                } catch (DTOUpdateException due) {
                    throw new EJBException("this should never happen", due);
                }
            }
        } else {
            throw new EJBException("this should never happen");
        }

        return success;
    }

    /**
     * @ejb.interface-method
     * @ejb.transaction
     *      type="NotSupported"
     *
     * @param email
     * @return
     * @throws NoSuchUserException
     */
    public UserProfileDTO getUserProfile(String email)
        throws NoSuchUserException {
        UserProfileDTO up = null;

        try {
            UserLocalHome home = UserUtil.getLocalHome();
            log.info("[getUserProfile] looking for a user with email =" +
                email);

            UserLocal user = findUserByEmail(email);

            // find which role this user plays in the system
            // there are two ways to accomplish this, one is to
            // get all the user roles all loop through them, since
            // for this implementation we are making the assumption that
            // a user plays one role only we can use the getPresenter 
            // and the getAttendee and go with whichever is not null
            if (user != null) {
                log.info("[getUserProfile] found the user!");

                Object source = user.getAttendee();

                if (source == null) {
                    source = user.getPresenter();
                }

                if (source != null) {
                    UserProfileDTOFactory builder = (UserProfileDTOFactory) DTOAbstractFactory.getInstance()
                                                                                              .getDTOBuilder(UserProfileDTO.class);

                    try {
                        up = (UserProfileDTO) builder.getDTO(source);
                    } catch (DTOCreateException dce) {
                        dce.printStackTrace();
                    }
                } else {
                    new EJBException("no role found");
                }
            } else {
                throw new NoSuchUserException(
                    "[getUserProfile] No user with email= " + email);
            }
        } catch (NamingException ne) {
            new EJBException("Error accessing user information", ne);
        }

        return up;
    }

    /**
     * @ejb.interface-method
     * @ejb.transaction
     *      type="NotSupported"
     *
     * @param userProfile
     * @return
     * @throws NoSuchUserException
     */
    public boolean setUserProfile(UserProfileDTO userProfile)
        throws NoSuchUserException {
        boolean result;
        UserProfileDTOFactory builder = (UserProfileDTOFactory) DTOAbstractFactory.getInstance()
                                                                                  .getDTOBuilder(UserProfileDTO.class);

        try {
            result = builder.setDTO(userProfile);
        } catch (DTOUpdateException due) {
            // get the root exception and throw it
            throw new NoSuchUserException("");
        }

        return result;
    }

    /**
     * @ejb.interface-method
     *      view-type="local"
     * @ejb.transaction
     *      type="NotSupported"
    *
     * @param email
     * @return
     * @throws NoSuchUserException
     */
    public UserLocal findUserByEmail(String email) throws NoSuchUserException {
        try {
            UserLocalHome home = UserUtil.getLocalHome();
            Collection c = home.findByEmail(email);

            if (!c.isEmpty()) {
                UserLocal user = (UserLocal) c.iterator().next();

                return user;
            } else {
                throw new NoSuchUserException(
                    "[findUserByEmail] No user with email= " + email);
            }
        } catch (NamingException ne) {
            throw new EJBException("[findUserByEmail] Error retrieving user information",
                ne);
        } catch (FinderException fe) {
            throw new NoSuchUserException(
                "[findUserByEmail] No user with email= " + email);
        }
    }

    /**
     * @ejb.interface-method
     *      view-type="local"
     * @ejb.transaction
     *      type="NotSupported"
    *
     * @param email
     * @return
     * @throws NoSuchUserException
     */
    public PresenterLocal findPresenterByEmail(String email)
        throws NoSuchUserException {
        PresenterLocal presenter = null;
        UserLocal user = findUserByEmail(email);

        if (user != null) {
            presenter = user.getPresenter();
        }

        if (presenter != null) {
            return presenter;
        } else {
            throw new NoSuchUserException(
                "[findPresenterByEmail] No presenter with email = " + email);
        }
    }

    /**
     * @ejb.interface-method
     *      view-type="local"
     * @ejb.transaction
     *      type="NotSupported"
     *
     * @param email
     * @return
     * @throws NoSuchUserException
     */
    public AttendeeLocal findAttendeeByEmail(String email)
        throws NoSuchUserException {
        AttendeeLocal attendee = null;
        UserLocal user = findUserByEmail(email);

        if (user != null) {
            attendee = user.getAttendee();
        }

        if (attendee != null) {
            return attendee;
        } else {
            throw new NoSuchUserException(
                "[findAttendeeByEmail] No attendee with email = " + email);
        }
    }

    // -- private methods --
    private UserLocal createNewUser(String email)
        throws DuplicateEmailException, CreateException, NamingException {
        UserLocal user = null;

        try {
            user = findUserByEmail(email);
        } catch (NoSuchUserException e) {
            // do nothing
        }

        if (user == null) {
            UserLocalHome home = UserUtil.getLocalHome();
            user = home.create();
            user.setEmail(email);
        } else {
            throw new DuplicateEmailException("User with email =" + email +
                " already exists");
        }

        return user;
    }

    //==========================================
    //  EJB callbacks
    //==========================================

    /**
     * @ejb.create-method
     */
    public void ejbCreate() throws CreateException {
        log = Logger.getLogger(UserServicesBean.class);
    }

    /**
     * DOCUMENT ME!
     *
     * @throws CreateException DOCUMENT ME!
     */
    public void ejbPostCreate() throws CreateException {
    }
}
