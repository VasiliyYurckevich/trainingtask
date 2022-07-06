/*
 * Copyright 2007 Qulix Systems, Inc. All rights reserved.
 * QULIX SYSTEMS PROPRIETARY/CONFIDENTIAL. Use is subject to license
 * terms.
 * Copyright (c) 2003-2007 Qulix Systems, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Qulix Systems. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Sun.
 *
 * QULIX MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
package com.qulix.yurkevichvv.trainingtask.servlets.exceptions;

/**
 * Описывает исключение, возникающее при ошибке выполнения запроса к базе данных.
 *
 * @author Q-YVV
 */
public class DaoException extends RuntimeException {

    /**
     * Конструктор класса с сообщением.
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * Конструктор класса с сообщением и исключением.
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Конструктор класса с исключением.
     */
    public DaoException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return this.getMessage();
    }
}


