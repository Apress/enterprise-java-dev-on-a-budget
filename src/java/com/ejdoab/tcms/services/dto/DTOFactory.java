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

import com.ejdoab.tcms.services.dto.exceptions.*;


/**
 * @author Brian Sam-Bodden
 */
public interface DTOFactory {
    DTO getDTO(Object obj) throws DTOCreateException;

    boolean setDTO(DTO dto) throws DTOUpdateException;

    boolean removeDTO(DTO dto) throws DTOUpdateException;
}
