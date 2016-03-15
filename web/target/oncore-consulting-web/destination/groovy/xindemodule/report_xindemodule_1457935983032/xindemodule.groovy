package groovy.xindemodule.report_xindemodule_1457935983032;

import com.oncore.common.groovy.GBaseDao;
import groovy.sql.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import groovy.sql.GroovyRowResult;
import java.sql.SQLException;
@Repository()
@Transactional
class xindemodule implements GBaseDao{

@Autowired Sql sql;
@Override
boolean insert( Map params,String userId) throws SQLException {
String insert = "insert into report_xindemodule_1457935983032 (Employee_name_2c9f940b5371936a015371975aad0002,generated,userId,deleted) values(:Employee_name_2c9f940b5371936a015371975aad0002,:generated,:userId,false)";
Map map = new HashMap();
map.put("Employee_name_2c9f940b5371936a015371975aad0002",params.get("Employee_name_2c9f940b5371936a015371975aad0002"));
map.put("generated",params.get("generated"));
map.put("userId",userId);
return this.sql.execute(map,insert);
}


@Override
Map get(int id,String userId)  throws SQLException {
GroovyRowResult result =  this.sql.firstRow("select report_xindemodule_1457935983032.userId,report_xindemodule_1457935983032.generated,Employee_1457899657886.name from report_xindemodule_1457935983032   join Employee_1457899657886   on  Employee_1457899657886.id=report_xindemodule_1457935983032.Employee_name_2c9f940b5371936a015371975aad0002 where report_xindemodule_1457935983032.deleted=false and report_xindemodule_1457935983032.id='"+id + "' and report_xindemodule_1457935983032.userId='"+userId+"'");
return result;
}
@Override
boolean update(int id, Map params,String userId)  throws SQLException  {
String updateSql = "update report_xindemodule_1457935983032 set  Employee_name_2c9f940b5371936a015371975aad0002=:Employee_name_2c9f940b5371936a015371975aad0002,generated=:generated  where id=:id and userId=:userId";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
map.put("Employee_name_2c9f940b5371936a015371975aad0002",params.get("Employee_name_2c9f940b5371936a015371975aad0002"));
map.put("generated",params.get("generated"));
return this.sql.executeUpdate(map,updateSql)
}

@Override
boolean delete(int id,String userId)  throws SQLException  {
String deleteSql = "update report_xindemodule_1457935983032 set deleted=true where userId=:userId and id =:id";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
return this.sql.executeUpdate(map,deleteSql);
}
@Override
List list(String userId)  throws SQLException {
String listSql = "select * from report_xindemodule_1457935983032 where deleted=false and  userId='" + userId + "'";
return sql.rows(listSql);
}
}
