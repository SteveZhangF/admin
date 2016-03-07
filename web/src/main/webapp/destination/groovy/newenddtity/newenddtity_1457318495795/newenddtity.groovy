package groovy.newenddtity.newenddtity_1457318495795;

import com.oncore.common.groovy.GBaseDao;
import groovy.sql.Sql;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import groovy.sql.GroovyRowResult;
@Repository()
@Transactional
class newenddtity implements GBaseDao{

@Autowired Sql sql;
@Override
boolean insert( Map params,String userId) {
String insert = "insert into newenddtity_1457318495795 (name,userId) values(:name,:userId)";
Map map = new HashMap();
map.put("name",params.get("name"));
map.put("userId",userId);
return this.sql.execute(map,insert);
}
@Override
Map get(int id,String userId){
GroovyRowResult result =  this.sql.firstRow("select * from newenddtity_1457318495795 where deleted=false and id='"+id + "' and userId='"+userId+"'");
return result;
}
@Override
boolean update(int id, Map params,String userId) {
String updateSql = "update newenddtity_1457318495795 set  (name) values(:name) where id=:id and userId=:userId";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
map.put("name",params.get("name"));
return this.sql.executeUpdate(map,updateSql)
}

@Override
boolean delete(int id,String userId) {
String deleteSql = "update newenddtity_1457318495795 set deleted=true where userId=:userId and id =:id";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
return this.sql.executeUpdate(map,updateSql);
}
@Override
List list(String userId) {
String listSql = "select * from newenddtity_1457318495795 where deleted=false and  userId='" + userId + "'";
return sql.rows(listSql);
}
}
