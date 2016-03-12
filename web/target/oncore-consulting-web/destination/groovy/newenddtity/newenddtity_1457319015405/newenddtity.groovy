package groovy.newenddtity.newenddtity_1457319015405;

import com.oncore.common.groovy.GBaseDao;
import groovy.sql.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import groovy.sql.GroovyRowResult;
import java.sql.SQLException;
@Repository()
@Transactional
class newenddtity implements GBaseDao{

@Autowired Sql sql;
@Override
boolean insert( Map params,String userId) throws SQLException {
String insert = "insert into newenddtity_1457319015405 (name,userId,deleted) values(:name,:userId,false)";
Map map = new HashMap();
map.put("name",params.get("name"));
map.put("userId",userId);
return this.sql.execute(map,insert);
}
@Override
Map get(int id,String userId)  throws SQLException {
GroovyRowResult result =  this.sql.firstRow("select * from newenddtity_1457319015405 where deleted=false and id='"+id + "' and userId='"+userId+"'");
return result;
}
@Override
boolean update(int id, Map params,String userId)  throws SQLException  {
String updateSql = "update newenddtity_1457319015405 set  name=:name  where id=:id and userId=:userId";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
map.put("name",params.get("name"));
return this.sql.executeUpdate(map,updateSql)
}

@Override
boolean delete(int id,String userId)  throws SQLException  {
String deleteSql = "update newenddtity_1457319015405 set deleted=true where userId=:userId and id =:id";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
return this.sql.executeUpdate(map,deleteSql);
}
@Override
List list(String userId)  throws SQLException {
String listSql = "select * from newenddtity_1457319015405 where deleted=false and  userId='" + userId + "'";
return sql.rows(listSql);
}
}
