<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE hibernate-mapping PUBLIC
        "-//Hibernate/Hibernate Mapping DTD//EN"
        "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">
<hibernate-mapping>
    <!-- 此处不需要类名，和包名 -->
    <class entity-name="${tableName}">
        <id name="id" type="java.lang.Long" column="id">
            <generator class="native"/>
        </id>
    <#list fields as field>
        <property name="${field.name}"
                  <#if field.length!=0>length="${field.length}"</#if>
                  <#if field.fieldType?? > type="${field.fieldType}"</#if>
                  column="${field.name}"/>
    </#list>
        <property name="userId"
        type="string"
                  column="userId"/>
        <property name="deleted"
                  type="boolean"
                  column="deleted"/>
    </class>
</hibernate-mapping>