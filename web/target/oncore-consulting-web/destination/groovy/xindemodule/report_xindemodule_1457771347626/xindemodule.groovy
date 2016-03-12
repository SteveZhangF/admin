package groovy.xindemodule.report_xindemodule_1457771347626;

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
String insert = "insert into report_xindemodule_1457771347626 (_4028818c534ecb1201534ecb2c960001,_4028819053695a6e0153697b63200004,generated,userId,deleted) values(:_4028818c534ecb1201534ecb2c960001,:_4028819053695a6e0153697b63200004,:generated,:userId,false)";
Map map = new HashMap();
map.put("_4028818c534ecb1201534ecb2c960001",params.get("_4028818c534ecb1201534ecb2c960001"));
map.put("_4028819053695a6e0153697b63200004",params.get("_4028819053695a6e0153697b63200004"));
map.put("generated",params.get("generated"));
map.put("userId",userId);
return this.sql.execute(map,insert);
}


@Override
Map get(int id,String userId)  throws SQLException {
GroovyRowResult result =  this.sql.firstRow("select report_xindemodule_1457771347626.userId,report_xindemodule_1457771347626.generated,newenddtity_1457315851342.namwqe,qwee_1457763607325.ssss from report_xindemodule_1457771347626   join newenddtity_1457315851342  join  join qwee_1457763607325   on  newenddtity_1457315851342.id=report_xindemodule_1457771347626._4028818c534ecb1201534ecb2c960001qwee_1457763607325.id=report_xindemodule_1457771347626._4028819053695a6e0153697b63200004 where report_xindemodule_1457771347626.deleted=false and report_xindemodule_1457771347626.id='"+id + "' and report_xindemodule_1457771347626.userId='"+userId+"'");
return result;
}
@Override
boolean update(int id, Map params,String userId)  throws SQLException  {
String updateSql = "update report_xindemodule_1457771347626 set  _4028818c534ecb1201534ecb2c960001=:_4028818c534ecb1201534ecb2c960001,_4028819053695a6e0153697b63200004=:_4028819053695a6e0153697b63200004,generated=:generated  where id=:id and userId=:userId";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
map.put("_4028818c534ecb1201534ecb2c960001",params.get("_4028818c534ecb1201534ecb2c960001"));
map.put("_4028819053695a6e0153697b63200004",params.get("_4028819053695a6e0153697b63200004"));
map.put("generated",params.get("generated"));
return this.sql.executeUpdate(map,updateSql)
}

@Override
boolean delete(int id,String userId)  throws SQLException  {
String deleteSql = "update report_xindemodule_1457771347626 set deleted=true where userId=:userId and id =:id";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
return this.sql.executeUpdate(map,deleteSql);
}
@Override
List list(String userId)  throws SQLException {
String listSql = "select * from report_xindemodule_1457771347626 where deleted=false and  userId='" + userId + "'";
return sql.rows(listSql);
}
}