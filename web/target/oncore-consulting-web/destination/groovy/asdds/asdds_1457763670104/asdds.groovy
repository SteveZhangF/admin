package groovy.asdds.asdds_1457763670104;

import com.oncore.common.groovy.GBaseDao;
import groovy.sql.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import groovy.sql.GroovyRowResult;
import java.sql.SQLException;
@Repository()
@Transactional
class asdds implements GBaseDao{

@Autowired Sql sql;
@Override
boolean insert( Map params,String userId) throws SQLException {
String insert = "insert into asdds_1457763670104 (aaaa,userId,deleted) values(:aaaa,:userId,false)";
Map map = new HashMap();
map.put("aaaa",params.get("aaaa"));
map.put("userId",userId);
return this.sql.execute(map,insert);
}
@Override
Map get(int id,String userId)  throws SQLException {
GroovyRowResult result =  this.sql.firstRow("select * from asdds_1457763670104 where deleted=false and id='"+id + "' and userId='"+userId+"'");
return result;
}
@Override
boolean update(int id, Map params,String userId)  throws SQLException  {
String updateSql = "update asdds_1457763670104 set  aaaa=:aaaa  where id=:id and userId=:userId";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
map.put("aaaa",params.get("aaaa"));
return this.sql.executeUpdate(map,updateSql)
}

@Override
boolean delete(int id,String userId)  throws SQLException  {
String deleteSql = "update asdds_1457763670104 set deleted=true where userId=:userId and id =:id";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
return this.sql.executeUpdate(map,deleteSql);
}
@Override
List list(String userId)  throws SQLException {
String listSql = "select * from asdds_1457763670104 where deleted=false and  userId='" + userId + "'";
return sql.rows(listSql);
}
}
