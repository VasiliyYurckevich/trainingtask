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
package com.qulix.yurkevichvv.trainingtask.main.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/**
 * Фильтр для кодирования данных.
 *
 * @author Q-YVV
 */
public class EncodingFilter implements Filter {

    /**
     * Название выброного типа кодирования.
     */
    private String encoding = "utf-8";

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
        throws IOException, ServletException {
        req.setCharacterEncoding(encoding);
        filterChain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        String encodingParam = filterConfig.getInitParameter("encoding");
        if (encodingParam != null) {
            encoding = encodingParam;
        }
    }
}
