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

package com.ejdoab.tcms.services.dto.page;

import com.ejdoab.tcms.util.Dates;

import org.apache.commons.logging.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Singleton collection of cached Pages for improving performance and response
 * time.
 * @author Brian Sam-Bodden
 */
public class PageCache {
    private static long CLEANING_INTERVAL = 5 * Dates.MILLIS_PER_MINUTE;
    private static PageCache instance = new PageCache();
    private static Log log = LogFactory.getLog(PageCache.class);
    private Map cache = new HashMap();
    private Map ttls = new HashMap();
    private long lastCleaned = System.currentTimeMillis();

    // private to support singlton interface.
    private PageCache() {
    }

    /**
     * Returns singleton instance.
     */
    public static PageCache getInstance() {
        return instance;
    }

    /**
     * Adds page to cache.
     * @param name Name used to retrieve page from cache.
     * @param page Page to store
     * @param ttlInMinutes Time to Live in minutes
     */
    public void addPage(String name, Page page, int ttlInMinutes) {
        cleanIfNeeded();

        // check if the Collection is in the cache		
        long now = System.currentTimeMillis();
        long ttlInMillis = now + (ttlInMinutes * Dates.MILLIS_PER_MINUTE);
        Date ttl = new Date(ttlInMillis);
        ttls.put(name, ttl);
        cache.put(name, page);
        log.debug("Collection " + name + " added on " + new Date(now) +
            " with ttl set to " + ttl);
    }

    /**
     * Returns cached page by name.
     */
    public Page getPage(String name) {
        cleanIfNeeded();

        Page page = null;
        Object o = cache.get(name);

        if (o != null) {
            // check the ttl
            Date ttl = (Date) ttls.get(name);
            Date now = new Date();

            // is it expired?
            log.debug("now = " + now + " ttl = " + ttl);

            if (ttl.after(now)) {
                log.debug("Collection " + name + " found");
                page = (Page) o;
            } else {
                // remove it if expired
                log.debug("Collection " + name + " found but it has expired");
                ttls.remove(name);
                cache.remove(name);
            }
        } else {
            log.warn("Collection " + name + " not found");
        }

        return page;
    }

    /**
         * @param name
         */
    public void invalidatePage(String name) {
        cache.remove(name);
    }

    /**
     * DOCUMENT ME!
     *
     * @param prefix DOCUMENT ME!
     */
    public void invalidateAllStartsWith(String prefix) {
        boolean done = false;
        Set keys = cache.keySet();
        Object[] keysAsArray = keys.toArray();

        for (int i = 0; i < keysAsArray.length; i++) {
            String key = (String) keysAsArray[i];

            if (key.startsWith(prefix)) {
                invalidatePage(key);
            }
        }
    }

    private void cleanIfNeeded() {
        long now = System.currentTimeMillis();
        log.debug("Cache was last cleaned on " + new Date(lastCleaned) +
            " now is " + new Date(now) + "\nlastCleaned =" + lastCleaned +
            " now =" + now);

        if (now > (lastCleaned + CLEANING_INTERVAL)) {
            log.trace("Need to clean the cache!");
            cleanCache();
        } else {
            log.trace("No need to clean cache!");
        }
    }

    /**
     * Cleans cache if necessary.
     */
    public synchronized void cleanCache() {
        log.trace("cleaning Page Cache");

        lastCleaned = System.currentTimeMillis();

        Set keySet = ttls.keySet();
        String[] keys = (String[]) keySet.toArray(new String[keySet.size()]);
        Date now = new Date();

        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            Date ttl = (Date) ttls.get(key);

            if (ttl != null) {
                if (ttl.before(now)) {
                    log.debug("Removing " + key + " from cache on " + now +
                        " ttl was " + ttl);
                    ttls.remove(key);
                    cache.remove(key);
                }
            }
        }
    }
}
