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
package com.qulix.yurkevichvv.trainingtask.servlets.entity;

/**
 * Описывает сущность "Статус".
 *
 * @author Q-YVV
 */
public enum Status {
    /**
     * Статус "Не начата".
     */
    N0BEGIN(1, "Не начата"),

    /**
     * Статус "В процессе".
     */
    INTERPROCESS(2, "В процессе"),

    /**
     * Статус "Завершена".
     */
    COMPLETED(3, "Завершена"),

    /**
     * Статус "Отложена".
     */
    POSTPONED(4, "Отложена");

    Status(int id, String status) {
        this.id = id;
        this.statusTitle = status;
    }

    /**
     * Идентификатор статуса.
     */
    private final Integer id;

    /**
     * Название статуса.
     */
    private final String statusTitle;

    public Integer getId() {
        return id;
    }

    /**
     * Возвращает статус по идентификатору.
     */
    public static Status getStatusById(Integer id) {
        for (Status e : values()) {
            if (e.id.equals(id)) {
                return e;
            }
        }
        return N0BEGIN;
    }

    public String getStatusTitle() {
        return statusTitle;
    }
}
