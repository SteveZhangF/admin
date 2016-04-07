/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.oncore.template.exception;


import com.oncore.common.exception.BadRequestException;

/**
 * Created by steve on 2/12/16.
 */

/**
 * duplicated exception
 *
 * throws when same type and same name under same module or folder
 * */
public class FileElementDuplicatedException extends BadRequestException {
    public FileElementDuplicatedException(String developerMessage) {
        super("file or folder "+developerMessage+" already existing");
    }
}
