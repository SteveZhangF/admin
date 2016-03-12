package groovy.woshireport.report_woshireport_1457582479974;

import com.oncore.common.groovy.GBaseDao;
import groovy.sql.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import groovy.sql.GroovyRowResult;
import java.sql.SQLException;
@Repository()
@Transactional
class woshireport implements GBaseDao{

@Autowired Sql sql;
@Override
boolean insert( Map params,String userId) throws SQLException {
String insert = "insert into report_woshireport_1457582479974 (name,generated,userId,deleted) values(:name,:generated,:userId,false)";
Map map = new HashMap();
map.put("name",params.get("name"));
map.put("generated",params.get("generated"));
map.put("userId",userId);
return this.sql.execute(map,insert);
}


@Override
Map get(int id,String userId)  throws SQLException {
GroovyRowResult result =  this.sql.firstRow("select report_woshireport_1457582479974.userId,report_woshireport_1457582479974.generated,newenddtity_1457291281138.name from report_woshireport_1457582479974   join newenddtity_1457291281138   on  newenddtity_1457291281138.id=report_woshireport_1457582479974.name where report_woshireport_1457582479974.deleted=false and report_woshireport_1457582479974.id='"+id + "' and report_woshireport_1457582479974.userId='"+userId+"'");
return result;
}
@Override
boolean update(int id, Map params,String userId)  throws SQLException  {
String updateSql = "update report_woshireport_1457582479974 set  name=:name,generated=:generated  where id=:id and userId=:userId";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
map.put("name",params.get("name"));
map.put("generated",params.get("generated"));
return this.sql.executeUpdate(map,updateSql)
}

@Override
boolean delete(int id,String userId)  throws SQLException  {
String deleteSql = "update report_woshireport_1457582479974 set deleted=true where userId=:userId and id =:id";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
return this.sql.executeUpdate(map,deleteSql);
}
@Override
List list(String userId)  throws SQLException {
String listSql = "select * from report_woshireport_1457582479974 where deleted=false and  userId='" + userId + "'";
return sql.rows(listSql);
}
}
