package groovy.${name}.${tableName};

import com.oncore.common.groovy.GBaseDao;
import groovy.sql.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import groovy.sql.GroovyRowResult;
import java.sql.SQLException;
@Repository()
@Transactional
class ${name} implements GBaseDao{

@Autowired Sql sql;
@Override
boolean insert( Map params,String userId) throws SQLException {
String insert = "insert into ${tableName} (<#list fields as field>${field.name}<#if field?has_next>,</#if></#list><#if (fields?size>0) >,</#if>userId,deleted) values(<#list fields as field>:${field.name}<#if field?has_next>,</#if></#list><#if (fields?size>0) >,</#if>:userId,false)";
Map map = new HashMap();
<#list fields as field>
map.put("${field.name}",params.get("${field.name}"));
</#list>
map.put("userId",userId);
return this.sql.execute(map,insert);
}
@Override
Map get(int id,String userId)  throws SQLException {
GroovyRowResult result =  this.sql.firstRow("select * from ${tableName} where deleted=false and id='"+id + "' and userId='"+userId+"'");
return result;
}
@Override
boolean update(int id, Map params,String userId)  throws SQLException  {
String updateSql = "update ${tableName} set  <#list fields as field>${field.name}=:${field.name}<#if field?has_next>,</#if></#list>  where id=:id and userId=:userId";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
<#list fields as field>
map.put("${field.name}",params.get("${field.name}"));
</#list>
return this.sql.executeUpdate(map,updateSql)
}

@Override
boolean delete(int id,String userId)  throws SQLException  {
String deleteSql = "update ${tableName} set deleted=true where userId=:userId and id =:id";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
return this.sql.executeUpdate(map,deleteSql);
}
@Override
List list(String userId)  throws SQLException {
String listSql = "select * from ${tableName} where deleted=false and  userId='" + userId + "'";
return sql.rows(listSql);
}
}
