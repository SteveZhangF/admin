package groovy.newreport.report_newreport_1458026806421;

import com.oncore.common.groovy.GBaseDao;
import groovy.sql.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import groovy.sql.GroovyRowResult;
import java.sql.SQLException;
@Repository()
@Transactional
class newreport implements GBaseDao{

@Autowired Sql sql;
@Override
boolean insert( Map params,String userId) throws SQLException {
String insert = "insert into report_newreport_1458026806421 (eeeee_name_2c9f940b53772a7e015377304c4b0001,generated,userId,deleted) values(:eeeee_name_2c9f940b53772a7e015377304c4b0001,:generated,:userId,false)";
Map map = new HashMap();
map.put("eeeee_name_2c9f940b53772a7e015377304c4b0001",params.get("eeeee_name_2c9f940b53772a7e015377304c4b0001"));
map.put("generated",params.get("generated"));
map.put("userId",userId);
return this.sql.execute(map,insert);
}


@Override
Map get(int id,String userId)  throws SQLException {
GroovyRowResult result =  this.sql.firstRow("select report_newreport_1458026806421.userId,report_newreport_1458026806421.generated,eeeee_1457993567275.name from report_newreport_1458026806421   join eeeee_1457993567275   on  eeeee_1457993567275.id=report_newreport_1458026806421.eeeee_name_2c9f940b53772a7e015377304c4b0001 where report_newreport_1458026806421.deleted=false and report_newreport_1458026806421.id='"+id + "' and report_newreport_1458026806421.userId='"+userId+"'");
return result;
}
@Override
boolean update(int id, Map params,String userId)  throws SQLException  {
String updateSql = "update report_newreport_1458026806421 set  eeeee_name_2c9f940b53772a7e015377304c4b0001=:eeeee_name_2c9f940b53772a7e015377304c4b0001,generated=:generated  where id=:id and userId=:userId";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
map.put("eeeee_name_2c9f940b53772a7e015377304c4b0001",params.get("eeeee_name_2c9f940b53772a7e015377304c4b0001"));
map.put("generated",params.get("generated"));
return this.sql.executeUpdate(map,updateSql)
}

@Override
boolean delete(int id,String userId)  throws SQLException  {
String deleteSql = "update report_newreport_1458026806421 set deleted=true where userId=:userId and id =:id";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
return this.sql.executeUpdate(map,deleteSql);
}
@Override
List list(String userId)  throws SQLException {
String listSql = "select * from report_newreport_1458026806421 where deleted=false and  userId='" + userId + "'";
return sql.rows(listSql);
}
}
