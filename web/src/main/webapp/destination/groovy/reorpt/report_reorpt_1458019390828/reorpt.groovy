package groovy.reorpt.report_reorpt_1458019390828;

import com.oncore.common.groovy.GBaseDao;
import groovy.sql.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import groovy.sql.GroovyRowResult;
import java.sql.SQLException;
@Repository()
@Transactional
class reorpt implements GBaseDao{

@Autowired Sql sql;
@Override
boolean insert( Map params,String userId) throws SQLException {
String insert = "insert into report_reorpt_1458019390828 (eeeee_name_2c9f940b53772a7e015377304c4b0001,updatedmodule_name_2c9f940b537747460153774de41e0001,generated,userId,deleted) values(:eeeee_name_2c9f940b53772a7e015377304c4b0001,:updatedmodule_name_2c9f940b537747460153774de41e0001,:generated,:userId,false)";
Map map = new HashMap();
map.put("eeeee_name_2c9f940b53772a7e015377304c4b0001",params.get("eeeee_name_2c9f940b53772a7e015377304c4b0001"));
map.put("updatedmodule_name_2c9f940b537747460153774de41e0001",params.get("updatedmodule_name_2c9f940b537747460153774de41e0001"));
map.put("generated",params.get("generated"));
map.put("userId",userId);
return this.sql.execute(map,insert);
}


@Override
Map get(int id,String userId)  throws SQLException {
GroovyRowResult result =  this.sql.firstRow("select report_reorpt_1458019390828.userId,report_reorpt_1458019390828.generated,eeeee_1457993567275.name,updatedmodule_1457995506699.name from report_reorpt_1458019390828   join eeeee_1457993567275  join  join updatedmodule_1457995506699   on  eeeee_1457993567275.id=report_reorpt_1458019390828.eeeee_name_2c9f940b53772a7e015377304c4b0001updatedmodule_1457995506699.id=report_reorpt_1458019390828.updatedmodule_name_2c9f940b537747460153774de41e0001 where report_reorpt_1458019390828.deleted=false and report_reorpt_1458019390828.id='"+id + "' and report_reorpt_1458019390828.userId='"+userId+"'");
return result;
}
@Override
boolean update(int id, Map params,String userId)  throws SQLException  {
String updateSql = "update report_reorpt_1458019390828 set  eeeee_name_2c9f940b53772a7e015377304c4b0001=:eeeee_name_2c9f940b53772a7e015377304c4b0001,updatedmodule_name_2c9f940b537747460153774de41e0001=:updatedmodule_name_2c9f940b537747460153774de41e0001,generated=:generated  where id=:id and userId=:userId";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
map.put("eeeee_name_2c9f940b53772a7e015377304c4b0001",params.get("eeeee_name_2c9f940b53772a7e015377304c4b0001"));
map.put("updatedmodule_name_2c9f940b537747460153774de41e0001",params.get("updatedmodule_name_2c9f940b537747460153774de41e0001"));
map.put("generated",params.get("generated"));
return this.sql.executeUpdate(map,updateSql)
}

@Override
boolean delete(int id,String userId)  throws SQLException  {
String deleteSql = "update report_reorpt_1458019390828 set deleted=true where userId=:userId and id =:id";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
return this.sql.executeUpdate(map,deleteSql);
}
@Override
List list(String userId)  throws SQLException {
String listSql = "select * from report_reorpt_1458019390828 where deleted=false and  userId='" + userId + "'";
return sql.rows(listSql);
}
}
