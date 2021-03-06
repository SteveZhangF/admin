package com.oncore.template.model;


import com.oncore.common.model.TableElement;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by steve on 1/14/16.
 */



/**
 *  an entity
 *  such as Company,Employee,Entrance, Exit, Office, Door...
 *
 * */

@javax.persistence.Entity
@Table(name = "entity")
public class Entity extends Element implements TableElement {


    // if the entity is unique
    private boolean unique_;

    private int priority;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    private String tableName;

    private String hbmPath;

    @OneToMany(mappedBy = "entity")
    @Cascade(value = CascadeType.ALL)
    private List<Field> fields = new ArrayList<>();

    public String getHbmPath() {
        return hbmPath;
    }

    public void setHbmPath(String hbmPath) {
        this.hbmPath = hbmPath;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }



    public List<Field> getFields() {
        return fields;
    }

    @Override
    public boolean isReport() {
        return false;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public void addField(Field field) {
        this.fields.add(field);
        field.setEntity(this);
    }

    public void removeField(Field field) {
        for (Field field1 : fields) {
            if (field1.getId().equals(field.getId())) {
                fields.remove(field1);
            }
        }
    }

//


    public boolean isUnique_() {
        return unique_;
    }

    public void setUnique_(boolean unique_) {
        this.unique_ = unique_;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        if (!tableName.equals(entity.tableName)) return false;
        if (!hbmPath.equals(entity.hbmPath)) return false;
        return fields.equals(entity.fields);

    }

    @Override
    public int hashCode() {
        int result = tableName.hashCode();
        result = 31 * result + hbmPath.hashCode();
        result = 31 * result + fields.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "tableName='" + tableName + '\'' +
                ", hbmPath='" + hbmPath + '\'' +
                ", fields=" + fields +
                "} " + super.toString();
    }
}
