package com.oncore.common.model;

import java.util.List;

/**
 * Created by steve on 1/15/16.
 */
public interface TableElement {
    String getTableName();
    String getHbmPath();
    String getName();
    List getFields();
}
