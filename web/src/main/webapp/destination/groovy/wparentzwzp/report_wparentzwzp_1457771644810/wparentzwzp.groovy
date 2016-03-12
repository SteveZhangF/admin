package groovy.wparentzwzp.report_wparentzwzp_1457771644810;

import com.oncore.common.groovy.GBaseDao;
import groovy.sql.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import groovy.sql.GroovyRowResult;
import java.sql.SQLException;
@Repository()
@Transactional
class wparentzwzp implements GBaseDao{

@Autowired Sql sql;
@Override
boolean insert( Map params,String userId) throws SQLException {
String insert = "insert into report_wparentzwzp_1457771644810 (_4028819053695a6e01536964c14d0002,_4028818c534ecb1201534ecb2c960001,generated,userId,deleted) values(:_4028819053695a6e01536964c14d0002,:_4028818c534ecb1201534ecb2c960001,:generated,:userId,false)";
Map map = new HashMap();
map.put("_4028819053695a6e01536964c14d0002",params.get("_4028819053695a6e01536964c14d0002"));
map.put("_4028818c534ecb1201534ecb2c960001",params.get("_4028818c534ecb1201534ecb2c960001"));
map.put("generated",params.get("generated"));
map.put("userId",userId);
return this.sql.execute(map,insert);
}


@Override
Map get(int id,String userId)  throws SQLException {
GroovyRowResult result =  this.sql.firstRow("select report_wparentzwzp_1457771644810.userId,report_wparentzwzp_1457771644810.generated,newenddtity_1457315851342.wwww,newenddtity_1457315851342.namwqe from report_wparentzwzp_1457771644810   join newenddtity_1457315851342  join  join newenddtity_1457315851342   on  newenddtity_1457315851342.id=report_wparentzwzp_1457771644810._4028819053695a6e01536964c14d0002newenddtity_1457315851342.id=report_wparentzwzp_1457771644810._4028818c534ecb1201534ecb2c960001 where report_wparentzwzp_1457771644810.deleted=false and report_wparentzwzp_1457771644810.id='"+id + "' and report_wparentzwzp_1457771644810.userId='"+userId+"'");
return result;
}
@Override
boolean update(int id, Map params,String userId)  throws SQLException  {
String updateSql = "update report_wparentzwzp_1457771644810 set  _4028819053695a6e01536964c14d0002=:_4028819053695a6e01536964c14d0002,_4028818c534ecb1201534ecb2c960001=:_4028818c534ecb1201534ecb2c960001,generated=:generated  where id=:id and userId=:userId";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
map.put("_4028819053695a6e01536964c14d0002",params.get("_4028819053695a6e01536964c14d0002"));
map.put("_4028818c534ecb1201534ecb2c960001",params.get("_4028818c534ecb1201534ecb2c960001"));
map.put("generated",params.get("generated"));
return this.sql.executeUpdate(map,updateSql)
}

@Override
boolean delete(int id,String userId)  throws SQLException  {
String deleteSql = "update report_wparentzwzp_1457771644810 set deleted=true where userId=:userId and id =:id";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
return this.sql.executeUpdate(map,deleteSql);
}
@Override
List list(String userId)  throws SQLException {
String listSql = "select * from report_wparentzwzp_1457771644810 where deleted=false and  userId='" + userId + "'";
return sql.rows(listSql);
}
}
