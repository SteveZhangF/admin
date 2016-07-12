package com.oncore.middleware.fileCreator.table;

/**
 * Created by steve on 4/9/16.
 */
public class ColumnObject {
    private String name;
    private String type;
    private int length;
    private int digits;
    private int nullable;

    public ColumnObject(String name, String type, int length, int digits, int nullable) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.digits = digits;
        this.nullable = nullable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getDigits() {
        return digits;
    }

    public void setDigits(int digits) {
        this.digits = digits;
    }

    public int getNullable() {
        return nullable;
    }

    public void setNullable(int nullable) {
        this.nullable = nullable;
    }

    @Override
    public String toString() {
        return "ColumnObject{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", length=" + length +
                ", digits=" + digits +
                ", nullable=" + nullable +
                '}';
    }
}
