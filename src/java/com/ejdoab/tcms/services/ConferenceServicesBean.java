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

import com.ejdoab.tcms.entities.ConferenceAbstractLocal;
import com.ejdoab.tcms.entities.ConferenceAbstractLocalHome;
import com.ejdoab.tcms.entities.ConferenceAbstractUtil;
import com.ejdoab.tcms.entities.ConferenceNewsLocalHome;
import com.ejdoab.tcms.entities.ConferenceNewsUtil;
import com.ejdoab.tcms.entities.PresentationLocal;
import com.ejdoab.tcms.entities.PresenterLocal;
import com.ejdoab.tcms.entities.SessionLocalHome;
import com.ejdoab.tcms.entities.SessionUtil;
import com.ejdoab.tcms.entities.UserLocalHome;
import com.ejdoab.tcms.services.dto.ConferenceAbstractDTO;
import com.ejdoab.tcms.services.dto.ConferenceAbstractDTOFactory;
import com.ejdoab.tcms.services.dto.DTOAbstractFactory;
import com.ejdoab.tcms.services.dto.NewsItemDTO;
import com.ejdoab.tcms.services.dto.NewsItemDTOFactory;
import com.ejdoab.tcms.services.dto.SessionDTO;
import com.ejdoab.tcms.services.dto.SessionDTOFactory;
import com.ejdoab.tcms.services.dto.StringCache;
import com.ejdoab.tcms.services.dto.exceptions.DTOCreateException;
import com.ejdoab.tcms.services.dto.exceptions.DTOUpdateException;
import com.ejdoab.tcms.services.dto.page.Page;
import com.ejdoab.tcms.services.dto.page.PageCache;
import com.ejdoab.tcms.services.dto.page.PageFactory;
import com.ejdoab.tcms.services.exceptions.NoSuchUserException;
import com.ejdoab.tcms.services.locator.ServiceLocator;
import com.ejdoab.tcms.services.locator.ServiceLocatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;

import javax.naming.NamingException;


/**
 * @author <a href="mailto:bsbodden@integrallis.com">Brian Sam-Bodden</a>
 *
 * @ejb.bean
 *      name="ConferenceServices"
 *      type="Stateless"
 *      view-type="both"
 *      jndi-name="ejb.ConferenceServicesHome"
 *      local-jndi-name="ejb.ConferenceServicesLocalHome"
 * @ejb.transaction
 *      type="Required"
 * @ejb.util
 *      generate="physical"
 * @axis.service
 *      name="Conference"
 *      scope="Request"
 *      include-all="true"
 */
public abstract class ConferenceServicesBean implements SessionBean {
    private static Log log = LogFactory.getLog(ConferenceServicesBean.class);

    /**
    * @ejb.interface-method
    * @ejb.transaction
    *      type="NotSupported"
    *
     * @param start
     * @param size
     * @return
     * @throws DTOCreateException
    */
    public Page getSessions(int start, int size) throws DTOCreateException {
        SessionLocalHome slh;

        String pageName = "ALL_SESSIONS_" + start + "_" + size;
        PageCache cache = PageCache.getInstance();
        Page page = cache.getPage(pageName);

        if (page == null) {
            Collection c = null;

            try {
                slh = SessionUtil.getLocalHome();
                c = slh.findAll();
            } catch (NamingException ne) {
                throw new EJBException("Error accessing Session Information", ne);
            } catch (FinderException fe) {
                throw new EJBException("Error accessing Session Information", fe);
            }

            SessionDTOFactory dtoFactory = (SessionDTOFactory) DTOAbstractFactory.getInstance()
                                                                                 .getDTOBuilder(SessionDTO.class);

            if (c != null) {
                page = PageFactory.buildPage(c, start, size, dtoFactory);
                cache.addPage(pageName, page, 10);
            }
        }

        return page;
    }

    /**
     * @ejb.interface-method
     *
         * @param email
         * @param start
         * @param size
         * @return
         * @throws NoSuchUserException
         * @throws DTOCreateException
         */
    public Page getSessionsByPresenter(String email, int start, int size)
        throws NoSuchUserException, DTOCreateException {
        UserLocalHome ulh = null;
        UserServicesLocal us = null;

        String pageName = "SESSIONS_FOR_" + email + "_" + start + "_" + size;
        PageCache cache = PageCache.getInstance();
        Page page = cache.getPage(pageName);

        if (page == null) {
            ServiceLocator sl;

            try {
                sl = ServiceLocator.getInstance();

                UserServicesLocalHome usHome = (UserServicesLocalHome) sl.getLocalHome(
                        "ejb.UserServicesLocalHome");

                us = usHome.create();
            } catch (ServiceLocatorException e) {
                new EJBException("could not access user services", e);
            } catch (CreateException e) {
                new EJBException("could not access user services", e);
            }

            Collection c = new ArrayList();

            PresenterLocal presenter = us.findPresenterByEmail(email);
            log.info("[getSessionsByPresenter] presenter was" +
                ((presenter == null) ? "not" : "") + " found");

            Collection abstracts = presenter.getConferenceAbstracts();
            log.info("[getSessionsByPresenter] found " + abstracts.size() +
                " abstracts");

            Iterator i = abstracts.iterator();

            for (int index = 0, n = abstracts.size(); index < n; index++) {
                PresentationLocal presentation = ((ConferenceAbstractLocal) i.next()).getPresentation();

                if (presentation != null) {
                    Collection sessions = presentation.getSessions();
                    c.addAll(sessions);
                }
            }

            SessionDTOFactory dtoFactory = (SessionDTOFactory) DTOAbstractFactory.getInstance()
                                                                                 .getDTOBuilder(SessionDTO.class);

            if (c != null) {
                page = PageFactory.buildPage(c, start, size, dtoFactory);
                cache.addPage(pageName, page, 10);
            }
        }

        return page;
    }

    /**
     * @ejb.interface-method
     */
    public boolean submitAbstract(ConferenceAbstractDTO dto)
        throws DTOUpdateException {
        log.info("[submitAbstract] abstract " + dto.getTitle() + " submitted");

        boolean sucess = false;
        ConferenceAbstractDTOFactory builder = (ConferenceAbstractDTOFactory) DTOAbstractFactory.getInstance()
                                                                                                .getDTOBuilder(ConferenceAbstractDTO.class);

        if (builder.setDTO(dto)) {
            // invalidate the affected cache entries
            PageCache cache = PageCache.getInstance();
            cache.invalidateAllStartsWith("ALL_ABSTRACT_");
            cache.invalidateAllStartsWith("ALL_SESSIONS_");

            return true;
        } else {
            return false;
        }
    }

    /**
    * @ejb.interface-method
    * @ejb.transaction
    *      type="NotSupported"
    *
     * @param id
     * @return
     * @throws DTOCreateException
    *
    */
    public ConferenceAbstractDTO getAbstract(int id) throws DTOCreateException {
        ConferenceAbstractLocalHome calh;
        ConferenceAbstractDTO dto = null;
        ConferenceAbstractLocal cal = null;

        try {
            calh = ConferenceAbstractUtil.getLocalHome();
        } catch (NamingException ne) {
            throw new EJBException("Error accessing Conference Abstract Information",
                ne);
        }

        try {
            cal = calh.findByPrimaryKey(new Integer(id));
        } catch (FinderException fe) {
            throw new DTOCreateException("Error accessing Conference Abstract Information",
                fe);
        }

        if (cal != null) {
            ConferenceAbstractDTOFactory dtoFactory = (ConferenceAbstractDTOFactory) DTOAbstractFactory.getInstance()
                                                                                                       .getDTOBuilder(ConferenceAbstractDTO.class);
            dto = (ConferenceAbstractDTO) dtoFactory.getDTO(cal);
        }

        return dto;
    }

    /**
    * @ejb.interface-method
     * @ejb.transaction
     *      type="NotSupported"
     *
     * @return int
     */
    public int getAbstractsCount() {
        int result = -1;

        ConferenceAbstractLocalHome calh;

        try {
            calh = ConferenceAbstractUtil.getLocalHome();

            Collection c = calh.findAll();

            // TODO replace with an ejbSelect method if
            // jboss 4.x supports the COUNT function in EJB-QL
            // as this is grossly inneficient
            result = c.size();
        } catch (NamingException ne) {
            throw new EJBException("Error accessing Conference Abstract Information",
                ne);
        } catch (FinderException fe) {
            throw new EJBException("Error accessing Conference Abstract Information",
                fe);
        }

        return result;
    }

    /**
    * @ejb.interface-method
    * @ejb.transaction
    *      type="NotSupported"
    *
     * @param start
     * @param size
     * @return
     * @throws DTOCreateException
    */
    public Page getAbstracts(int start, int size) throws DTOCreateException {
        ConferenceAbstractLocalHome calh;
        String pageName = "ALL_ABSTRACT_" + start + "_" + size;
        PageCache cache = PageCache.getInstance();
        Page page = cache.getPage(pageName);

        if (page == null) {
            Collection c = null;

            try {
                calh = ConferenceAbstractUtil.getLocalHome();
                c = calh.findAll();
            } catch (NamingException ne) {
                throw new EJBException("Error accessing Conference Abstract Information",
                    ne);
            } catch (FinderException fe) {
                throw new EJBException("Error accessing Conference Abstract Information",
                    fe);
            }

            ConferenceAbstractDTOFactory dtoFactory = (ConferenceAbstractDTOFactory) DTOAbstractFactory.getInstance()
                                                                                                       .getDTOBuilder(ConferenceAbstractDTO.class);

            if (c != null) {
                page = PageFactory.buildPage(c, start, size, dtoFactory);
                cache.addPage(pageName, page, 10);
            }
        }

        return page;
    }

    /**
     * @ejb.interface-method
     *
         * @param email
         * @param start
         * @param size
         * @return
         * @throws NoSuchUserException
         * @throws DTOCreateException
         */
    public Page getAbstractsByPresenter(String email, int start, int size)
        throws NoSuchUserException, DTOCreateException {
        log.debug("[getAbstractsByPresenter] start");

        UserLocalHome ulh = null;
        UserServicesLocal us = null;

        String pageName = "ABSTRACTS_FOR_" + email + "_" + start + "_" + size;
        PageCache cache = PageCache.getInstance();
        Page page = cache.getPage(pageName);

        if (page == null) {
            ServiceLocator sl;

            try {
                sl = ServiceLocator.getInstance();

                UserServicesLocalHome usHome = (UserServicesLocalHome) sl.getLocalHome(
                        "ejb.UserServicesLocalHome");

                us = usHome.create();
            } catch (ServiceLocatorException e) {
                new EJBException("could not access user services", e);
            } catch (CreateException e) {
                new EJBException("could not access user services", e);
            }

            PresenterLocal presenter = us.findPresenterByEmail(email);
            log.info("[getAbstractsByPresenter] presenter was" +
                ((presenter == null) ? "not" : "") + " found");

            Collection c = presenter.getConferenceAbstracts();

            ConferenceAbstractDTOFactory dtoFactory = (ConferenceAbstractDTOFactory) DTOAbstractFactory.getInstance()
                                                                                                       .getDTOBuilder(ConferenceAbstractDTO.class);

            if (c != null) {
                page = PageFactory.buildPage(c, start, size, dtoFactory);
                cache.addPage(pageName, page, 10);
            }
        }

        return page;
    }

    /**
     * @ejb.interface-method
     *
         * @return
         */
    public String[] getValidTypes() {
        return StringCache.getTypes();
    }

    /**
     * @ejb.interface-method
     *
         * @return
         */
    public String[] getValidTopics() {
        return StringCache.getTopics();
    }

    /**
     * @ejb.interface-method
     *
         * @return
         */
    public String[] getValidLevels() {
        return StringCache.getLevels();
    }

    /**
     * @ejb.interface-method
     *
     * @return
     */
    public String[] getValidAbstractStatus() {
        return StringCache.getAbstractStatus();
    }

    /**
    * @ejb.interface-method
    * @ejb.transaction
    *      type="NotSupported"
    *
     * @param start
     * @param size
     * @return
     * @throws DTOCreateException
    */
    public Page getNews(int start, int size) throws DTOCreateException {
        ConferenceNewsLocalHome cnlh;

        String pageName = "ALL_NEWS_" + start + "_" + size;
        PageCache cache = PageCache.getInstance();
        Page page = cache.getPage(pageName);

        if (page == null) {
            Collection c = null;

            try {
                cnlh = ConferenceNewsUtil.getLocalHome();
                c = cnlh.findAll();
            } catch (NamingException ne) {
                throw new EJBException("Error accessing Session Information", ne);
            } catch (FinderException fe) {
                throw new EJBException("Error accessing Session Information", fe);
            }

            NewsItemDTOFactory dtoFactory = (NewsItemDTOFactory) DTOAbstractFactory.getInstance()
                                                                                   .getDTOBuilder(NewsItemDTO.class);

            if (c != null) {
                page = PageFactory.buildPage(c, start, size, dtoFactory);
                cache.addPage(pageName, page, 10);
            }
        }

        return page;
    }

    /**
    * @ejb.interface-method
     * @ejb.transaction
     *      type="NotSupported"
     *
     * @return int
     */
    public int getNewsCount() {
        int result = -1;

        ConferenceNewsLocalHome cnlh;

        try {
            cnlh = ConferenceNewsUtil.getLocalHome();

            Collection c = cnlh.findAll();

            // TODO replace with an ejbSelect method if
            // jboss 4.x supports the COUNT function in EJB-QL
            // as this is grossly inneficient
            result = c.size();
        } catch (NamingException ne) {
            throw new EJBException("Error accessing News", ne);
        } catch (FinderException fe) {
            throw new EJBException("Error accessing News", fe);
        }

        return result;
    }

    /**
     * @ejb.interface-method
     */
    public boolean submitNewsItem(NewsItemDTO dto) throws DTOUpdateException {
        log.info("[submitNewsItem] news item " + dto.getTitle() + " submitted");

        boolean sucess = false;

        NewsItemDTOFactory dtoFactory = (NewsItemDTOFactory) DTOAbstractFactory.getInstance()
                                                                               .getDTOBuilder(NewsItemDTO.class);

        if (dtoFactory.setDTO(dto)) {
            // invalidate the affected cache entries
            PageCache cache = PageCache.getInstance();
            cache.invalidateAllStartsWith("ALL_NEWS_");

            return true;
        } else {
            return false;
        }
    }

    /**
     * @ejb.interface-method
     */
    public boolean removeNewsItem(NewsItemDTO dto) throws DTOUpdateException {
        log.info("[removeNewsItem] news item " + dto.getTitle() +
            " submitted for removal");

        boolean sucess = false;

        NewsItemDTOFactory dtoFactory = (NewsItemDTOFactory) DTOAbstractFactory.getInstance()
                                                                               .getDTOBuilder(NewsItemDTO.class);

        if (dtoFactory.removeDTO(dto)) {
            // invalidate the affected cache entries
            PageCache cache = PageCache.getInstance();
            cache.invalidateAllStartsWith("ALL_NEWS_");

            return true;
        } else {
            return false;
        }
    }

    //==========================================
    //  EJB callbacks
    //==========================================

    /**
     * @ejb.create-method
     */
    public void ejbCreate() throws CreateException {
    }

    /**
     * @throws CreateException DOCUMENT ME!
     */
    public void ejbPostCreate() throws CreateException {
    }
}
