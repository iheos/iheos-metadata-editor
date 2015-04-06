package gov.nist.hit.ds.docentryeditor.shared.model;

/**
 * Created by onh2 on 3/9/2015.
 */
public interface XdsModelElement {
    public String toString();
    public boolean verify();
    public XdsModelElement clone();
}
