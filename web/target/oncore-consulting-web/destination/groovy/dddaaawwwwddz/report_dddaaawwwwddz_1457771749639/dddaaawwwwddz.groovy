package groovy.dddaaawwwwddz.report_dddaaawwwwddz_1457771749639;

import com.oncore.common.groovy.GBaseDao;
import groovy.sql.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import groovy.sql.GroovyRowResult;
import java.sql.SQLException;
@Repository()
@Transactional
class dddaaawwwwddz implements GBaseDao{

@Autowired Sql sql;
@Override
boolean insert( Map params,String userId) throws SQLException {
String insert = "insert into report_dddaaawwwwddz_1457771749639 (generated,userId,deleted) values(:generated,:userId,false)";
Map map = new HashMap();
map.put("generated",params.get("generated"));
map.put("userId",userId);
return this.sql.execute(map,insert);
}


@Override
Map get(int id,String userId)  throws SQLException {
GroovyRowResult result =  this.sql.firstRow("select report_dddaaawwwwddz_1457771749639.userId,report_dddaaawwwwddz_1457771749639.generated from report_dddaaawwwwddz_1457771749639     where report_dddaaawwwwddz_1457771749639.deleted=false and report_dddaaawwwwddz_1457771749639.id='"+id + "' and report_dddaaawwwwddz_1457771749639.userId='"+userId+"'");
return result;
}
@Override
boolean update(int id, Map params,String userId)  throws SQLException  {
String updateSql = "update report_dddaaawwwwddz_1457771749639 set  generated=:generated  where id=:id and userId=:userId";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
map.put("generated",params.get("generated"));
return this.sql.executeUpdate(map,updateSql)
}

@Override
boolean delete(int id,String userId)  throws SQLException  {
String deleteSql = "update report_dddaaawwwwddz_1457771749639 set deleted=true where userId=:userId and id =:id";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
return this.sql.executeUpdate(map,deleteSql);
}
@Override
List list(String userId)  throws SQLException {
String listSql = "select * from report_dddaaawwwwddz_1457771749639 where deleted=false and  userId='" + userId + "'";
return sql.rows(listSql);
}
}
