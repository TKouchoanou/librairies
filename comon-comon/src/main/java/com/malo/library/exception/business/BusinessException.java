/*
 * Copyright (c) 2001-2018 Group JCDecaux.
 * 17 rue Soyer, 92523 Neuilly Cedex, France.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Group JCDecaux ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you
 * entered into with Group JCDecaux.
 */

package com.malo.library.exception.business;


import com.malo.library.exception.BorrowException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("unused")
public class BusinessException extends Exception implements BorrowException<BusinessExceptionKeyEnum> {

    private final BusinessExceptionKeyEnum key;

    private final List<? extends Serializable> params;

    // Optional key representing the target of the error (example: member id)
    private final String targetKey;

    @Serial
    private static final long serialVersionUID = 1L;

    public BusinessException(BusinessExceptionKeyEnum key) {
        this(key, null, null);
    }

    public BusinessException(BusinessException source, String targetKey) {
        this(source.key, source.params, targetKey);
    }

    public BusinessException(BusinessExceptionKeyEnum key, List<? extends Serializable> params, String targetKey) {
        super(buildMessage(key, params, targetKey, false));
        this.key = key;
        this.params = Objects.requireNonNullElseGet(params, ArrayList::new);
        this.targetKey = targetKey;
    }

    @SafeVarargs
    public <T extends Serializable> BusinessException(BusinessExceptionKeyEnum key, T... params) {
        this(key, (params == null) ? null : Arrays.asList(params), null);
    }

    private static String buildMessage(BusinessExceptionKeyEnum key, List<? extends Serializable> params, String targetKey, boolean inShortMode) {
        StringBuilder message = new StringBuilder();

        if (!inShortMode) {
            message.append("Business exception with key ");
        }

        message.append(key);

        if (targetKey != null) {
            message.append(" and target key ").append(targetKey);
        }

        if (params != null && !params.isEmpty()) {
            message.append(" and params ");
            for (int i = 0; i < params.size(); i++) {
                if (i > 0) {
                    message.append(',');
                }
                message.append(params.get(i));
            }
        }

        return message.toString();
    }

    @Override
    public String getShortDescription() {
        return buildMessage(key, params, targetKey, true);
    }

    @Override
    public Throwable getException() {
        return this;
    }

}
