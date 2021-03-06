package groovy.wordformattest.report_wordformattest_1458061370336;

import com.oncore.common.groovy.GBaseDao;
import groovy.sql.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import groovy.sql.GroovyRowResult;
import java.sql.SQLException;
@Repository()
@Transactional
class wordformattest implements GBaseDao{

@Autowired Sql sql;
@Override
boolean insert( Map params,String userId) throws SQLException {
String insert = "insert into report_wordformattest_1458061370336 (generated,userId,deleted) values(:generated,:userId,false)";
Map map = new HashMap();
map.put("generated",params.get("generated"));
map.put("userId",userId);
return this.sql.execute(map,insert);
}


@Override
Map get(int id,String userId)  throws SQLException {
GroovyRowResult result =  this.sql.firstRow("select report_wordformattest_1458061370336.userId,report_wordformattest_1458061370336.generated from report_wordformattest_1458061370336     where report_wordformattest_1458061370336.deleted=false and report_wordformattest_1458061370336.id='"+id + "' and report_wordformattest_1458061370336.userId='"+userId+"'");
return result;
}
@Override
boolean update(int id, Map params,String userId)  throws SQLException  {
String updateSql = "update report_wordformattest_1458061370336 set  generated=:generated  where id=:id and userId=:userId";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
map.put("generated",params.get("generated"));
return this.sql.executeUpdate(map,updateSql)
}

@Override
boolean delete(int id,String userId)  throws SQLException  {
String deleteSql = "update report_wordformattest_1458061370336 set deleted=true where userId=:userId and id =:id";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
return this.sql.executeUpdate(map,deleteSql);
}
@Override
List list(String userId)  throws SQLException {
String listSql = "select * from report_wordformattest_1458061370336 where deleted=false and  userId='" + userId + "'";
return sql.rows(listSql);
}
}
