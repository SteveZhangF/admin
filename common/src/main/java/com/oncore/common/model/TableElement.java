package com.oncore.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by steve on 1/15/16.
 */

/**
 * an interface for table elements (which could representated to a table)
 * */
public interface TableElement extends Serializable {
    // return the table name
    String getTableName();

    // return the hibernate mapping file path
    String getHbmPath();
    // return the name of element
    String getName();

    //return the fields element in the table element
    List getFields();

    //if the table element is a report
    boolean isReport();
}
