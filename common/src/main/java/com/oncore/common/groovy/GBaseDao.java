package com.oncore.common.groovy;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by steve on 1/16/16.
 * base dao for groovy script
 *
 */
public interface GBaseDao {
    boolean insert(Map params, String userId)  throws SQLException;
    Map get(int id, String userId)  throws SQLException ;
    boolean update(int id, Map params, String userId)  throws SQLException ;
    boolean delete(int id, String userId)  throws SQLException ;
    List list(String userId)  throws SQLException ;
}
