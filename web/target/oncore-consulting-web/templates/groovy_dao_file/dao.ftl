package groovy.${name}.${tableName};

import com.oncore.common.groovy.GBaseDao;
import groovy.sql.Sql;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import groovy.sql.GroovyRowResult;
@Repository()
@Transactional
class ${name} implements GBaseDao{

@Autowired Sql sql;
@Override
boolean insert( Map params,String userId) {
String insert = "insert into ${tableName} (<#list fields as field>${field.name}<#if field?has_next>,</#if></#list><#if (fields?size>0) >,</#if>userId) values(<#list fields as field>:${field.name}<#if field?has_next>,</#if></#list><#if (fields?size>0) >,</#if>:userId)";
Map map = new HashMap();
<#list fields as field>
map.put("${field.name}",params.get("${field.name}"));
</#list>
map.put("userId",userId);
return this.sql.execute(map,insert);
}
@Override
Map get(int id,String userId){
GroovyRowResult result =  this.sql.firstRow("select * from ${tableName} where deleted=false and id='"+id + "' and userId='"+userId+"'");
return result;
}
@Override
boolean update(int id, Map params,String userId) {
String updateSql = "update ${tableName} set  (<#list fields as field>${field.name}<#if field?has_next>,</#if></#list>) values(<#list fields as field>:${field.name}<#if field?has_next>,</#if></#list>) where id=:id and userId=:userId";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
<#list fields as field>
map.put("${field.name}",params.get("${field.name}"));
</#list>
return this.sql.executeUpdate(map,updateSql)
}

@Override
boolean delete(int id,String userId) {
String deleteSql = "update ${tableName} set deleted=true where userId=:userId and id =:id";
Map map = new HashMap();
map.put("id",id);
map.put("userId",userId);
return this.sql.executeUpdate(map,updateSql);
}
@Override
List list(String userId) {
String listSql = "select * from ${tableName} where deleted=false and  userId='" + userId + "'";
return sql.rows(listSql);
}
}
