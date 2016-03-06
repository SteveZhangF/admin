package com.oncore.template.model.file;


import javax.persistence.Table;

/**
 * Created by steve on 1/14/16.
 */
@javax.persistence.Entity
@Table(name = "folder_file_element")
public class Folder extends FileElement {

    private int depth;

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
