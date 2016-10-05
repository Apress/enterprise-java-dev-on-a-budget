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

import com.ejdoab.tcms.entities.ConferenceAbstractStatusLocal;
import com.ejdoab.tcms.entities.ConferenceAbstractStatusUtil;
import com.ejdoab.tcms.entities.PresentationLevelLocal;
import com.ejdoab.tcms.entities.PresentationLevelUtil;
import com.ejdoab.tcms.entities.PresentationTopicLocal;
import com.ejdoab.tcms.entities.PresentationTopicUtil;
import com.ejdoab.tcms.entities.PresentationTypeLocal;
import com.ejdoab.tcms.entities.PresentationTypeUtil;
import com.ejdoab.tcms.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.FinderException;

import javax.naming.NamingException;


/**
 * @author Brian Sam-Bodden
 */
public class StringCache {
    private static Log log = LogFactory.getLog(StringCache.class);
    private static String[] types;
    private static String[] topics;
    private static String[] levels;
    private static String[] abstractStatus;

    static {
        initialize();
    }

    /**
     * DOCUMENT ME!
     */
    public static void initialize() {
        try {
            Collection allTopics = PresentationTopicUtil.getLocalHome().findAll();
            Collection allTypes = PresentationTypeUtil.getLocalHome().findAll();
            Collection allLevels = PresentationLevelUtil.getLocalHome().findAll();
            Collection allStatus = ConferenceAbstractStatusUtil.getLocalHome()
                                                               .findAll();

            int topicCount = allTopics.size();
            topics = new String[topicCount];

            Iterator topicsIterator = allTopics.iterator();

            for (int index = 0; index < topicCount; index++) {
                PresentationTopicLocal topic = (PresentationTopicLocal) topicsIterator.next();

                if (log.isDebugEnabled()) {
                    log.debug("[initialize] Adding topic " + topic.getId() +
                        "=" + topic.getName());
                }

                topics[topic.getId().intValue()] = topic.getName();
            }

            int typesCount = allTypes.size();
            types = new String[typesCount];

            Iterator typesIterator = allTypes.iterator();

            for (int index = 0; index < typesCount; index++) {
                PresentationTypeLocal type = (PresentationTypeLocal) typesIterator.next();

                if (log.isDebugEnabled()) {
                    log.debug("[initialized] Adding type " + type.getId() +
                        "=" + type.getName());
                }

                types[type.getId().intValue()] = type.getName();
            }

            int levelsCount = allLevels.size();
            levels = new String[levelsCount];

            Iterator levelsIterator = allLevels.iterator();

            for (int index = 0; index < levelsCount; index++) {
                PresentationLevelLocal level = (PresentationLevelLocal) levelsIterator.next();

                if (log.isDebugEnabled()) {
                    log.debug("[initialized] Adding level " + level.getId() +
                        "=" + level.getName());
                }

                levels[level.getId().intValue()] = level.getName();
            }

            int aStatusCount = allStatus.size();
            abstractStatus = new String[aStatusCount];

            Iterator abstractsStatusIterator = allStatus.iterator();

            for (int index = 0; index < aStatusCount; index++) {
                ConferenceAbstractStatusLocal status = (ConferenceAbstractStatusLocal) abstractsStatusIterator.next();

                if (log.isDebugEnabled()) {
                    log.debug("[initialized] Adding abstract status " +
                        status.getId() + "=" + status.getName());
                }

                abstractStatus[status.getId().intValue()] = status.getName();
            }
        } catch (NamingException ne) {
            ne.printStackTrace();
        } catch (FinderException fe) {
            fe.printStackTrace();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param index DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static String getType(int index) {
        if ((index >= 0) && (index < types.length)) {
            return types[index];
        } else {
            return "n/a";
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param type DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static int getTypeIndex(String type) {
        return Arrays.getElementIndexIgnoreCase(types, type);
    }

    /**
     * DOCUMENT ME!
     *
     * @param index DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static String getLevel(int index) {
        if ((index >= 0) && (index < levels.length)) {
            return levels[index];
        } else {
            return "n/a";
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param level DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static int getLevelIndex(String level) {
        return Arrays.getElementIndexIgnoreCase(levels, level);
    }

    /**
     * DOCUMENT ME!
     *
     * @param index DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static String getTopic(int index) {
        if ((index >= 0) && (index < topics.length)) {
            return topics[index];
        } else {
            return "n/a";
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param topic DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static int getTopicIndex(String topic) {
        return Arrays.getElementIndexIgnoreCase(topics, topic);
    }

    /**
     * DOCUMENT ME!
     *
     * @param index DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static String getAbstractStatus(int index) {
        if ((index >= 0) && (index < abstractStatus.length)) {
            return abstractStatus[index];
        } else {
            return "n/a";
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param status DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static int getAbstractStatusIndex(String status) {
        return Arrays.getElementIndexIgnoreCase(abstractStatus, status);
    }

    /**
     * @return
     */
    public static String[] getAbstractStatus() {
        return abstractStatus;
    }

    /**
     * @return
     */
    public static String[] getLevels() {
        return levels;
    }

    /**
     * @return
     */
    public static String[] getTopics() {
        return topics;
    }

    /**
     * @return
     */
    public static String[] getTypes() {
        return types;
    }
}
