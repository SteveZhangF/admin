package groovy.Employee.entity__E_m_p_l_o_y_e_e__1458190174656;

import com.oncore.common.groovy.GBaseDao;
import groovy.sql.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import groovy.sql.GroovyRowResult;
import java.sql.SQLException;
@Repository()
@Transactional
class Employee implements GBaseDao{

@Autowired Sql sql;
@Override
boolean insert( Map params,String userId) throws SQLException {
String insert = "insert into entity__E_m_p_l_o_y_e_e__1458190174656 (First_Name,Last_Name,SSNumber,userId,deleted) values(:First_Name,:Last_Name,:SSNumber,:userId,false)";
Map map = new HashMap();
map.put("First_Name",params.get("First_Name"));
map.put("Last_Name",params.get("Last_Name"));
map.put("SSNumber",params.get("SSNumber"));
map.put("userId",userId);
return this.sql.execute(map,insert);
}
@Override
Map get(int id,String userId)  throws SQLException {
GroovyRowResult result =  this.sql.firstRow("select * from entity__E_m_p_l_o_y_e_e__1458190174656 where deleted=false and id='"+id + "' and userId='"+userId+"'");
return result;
}
@Override
boolean update(int id, Map params,String userId)  throws SQLException  {
String updateSql = "update entity__E_m_p_l_o_y_e_e__1458190174656 set  First_Name=:First_Name,Last_Name=:Last_Name,SSNumber=:SSNumber  where id=:id and userId=:userId";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
map.put("First_Name",params.get("First_Name"));
map.put("Last_Name",params.get("Last_Name"));
map.put("SSNumber",params.get("SSNumber"));
return this.sql.executeUpdate(map,updateSql)
}

@Override
boolean delete(int id,String userId)  throws SQLException  {
String deleteSql = "update entity__E_m_p_l_o_y_e_e__1458190174656 set deleted=true where userId=:userId and id =:id";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
return this.sql.executeUpdate(map,deleteSql);
}
@Override
List list(String userId)  throws SQLException {
String listSql = "select * from entity__E_m_p_l_o_y_e_e__1458190174656 where deleted=false and  userId='" + userId + "'";
return sql.rows(listSql);
}
}
