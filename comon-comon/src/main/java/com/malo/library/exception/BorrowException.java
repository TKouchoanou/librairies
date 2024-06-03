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

package com.malo.library.exception;

import com.malo.library.exception.BorrowExceptionKey;

import java.io.Serializable;
import java.util.List;

public interface BorrowException<E extends BorrowExceptionKey> {

    E getKey();


    String getCustomMessage();

    Object getData();



}