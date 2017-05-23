package gov.nist.hit.ds.docentryeditor.shared.util;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by onh2 on 5/10/17.
 */
public class ExtCacheFile implements Serializable, IsSerializable{
    private String name;
    private boolean directory=false;
    private ExtCacheFile parent;
    private List<ExtCacheFile> children=new ArrayList<ExtCacheFile>();

    public ExtCacheFile() {
    }

    public ExtCacheFile(String name){
        this.name=name;
        parent=null;
    }

    public ExtCacheFile(String name, ExtCacheFile parent){
        this.name=name;
        this.parent=parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExtCacheFile getParent() {
        return parent;
    }

    public void setParent(ExtCacheFile parent) {
        this.parent = parent;
    }

    public List<ExtCacheFile> getChildren() {
        return children;
    }

    public void setChildren(List<ExtCacheFile> children) {
        this.children = children;
    }

    public void addChild(ExtCacheFile child){
        children.add(child);
    }

    public boolean isDirectory() {
        return directory;
    }
}
