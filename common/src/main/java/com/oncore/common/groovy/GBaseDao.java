package com.oncore.common.groovy;

import java.util.List;
import java.util.Map;

/**
 * Created by steve on 1/16/16.
 * base dao for groovy script
 *
 */
public interface GBaseDao {
    boolean insert(Map params, String userId);
    Map get(int id, String userId);
    boolean update(int id, Map params, String userId);
    boolean delete(int id, String userId);
    List list(String userId);
}
