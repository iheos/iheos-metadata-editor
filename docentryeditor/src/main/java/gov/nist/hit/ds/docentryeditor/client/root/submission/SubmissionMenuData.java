package gov.nist.hit.ds.docentryeditor.client.root.submission;

import com.google.gwt.core.client.GWT;
import gov.nist.hit.ds.docentryeditor.shared.model.ModelElement;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsModelElement;

/**
 * This is the model class handled by the UI tree to manage the different element of the
 * XDS Submission structure.
 * Created by onh2 on 7/11/2014.
 */
public class SubmissionMenuData<M extends XdsModelElement> {
    public static final SubmissionMenuDataProperties PROPS = GWT.create(SubmissionMenuDataProperties.class);
    private String key;
    private String value;
    private M model;
    private boolean isActiveLink=false;

    public SubmissionMenuData(String key, String value, M model) {
        this.key = key;
        this.value = value;
        this.model = model;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public M getModel() {
        return model;
    }

    public void setModel(M model) {
        this.model = model;
    }

    public boolean isActiveLink() {
        return isActiveLink;
    }

    public void setIsActiveLink(boolean isActiveLink) {
        this.isActiveLink = isActiveLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof SubmissionMenuData)){
            return false;
        }
        SubmissionMenuData that = (SubmissionMenuData) o;
        if (!(key.equals(that.key) && model.equals(that.model) && value.equals(that.value))){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = ModelElement.HASHING_KEY * result + (value != null ? value.hashCode() : 0);
        result = ModelElement.HASHING_KEY * result + (model != null ? model.hashCode() : 0);
        return result;
    }

}
