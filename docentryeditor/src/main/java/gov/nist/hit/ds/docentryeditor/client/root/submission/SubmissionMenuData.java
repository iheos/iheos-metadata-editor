package gov.nist.hit.ds.docentryeditor.client.root.submission;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import gov.nist.hit.ds.docentryeditor.shared.model.ModelElement;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsModelElement;

/**
 * This is the model class handled by the UI tree to manage the different element of the
 * XDS Submission structure.
 * Created by onh2 on 7/11/2014.
 */
public class SubmissionMenuData<M extends XdsModelElement> {
    public static final SubmissionMenuData.SubmissionMenuDataProperties props = GWT.create(SubmissionMenuData.SubmissionMenuDataProperties.class);
    private String key;
    private String value;
    private M model;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubmissionMenuData)) return false;

        SubmissionMenuData that = (SubmissionMenuData) o;

        if (!key.equals(that.key)) return false;
        if (!model.equals(that.model)) return false;
        if (!value.equals(that.value)) return false;

        return true;
    }

    public interface SubmissionMenuDataProperties extends PropertyAccess<SubmissionMenuData> {

        ModelKeyProvider<SubmissionMenuData> key();

        @Editor.Path("value")
        LabelProvider<SubmissionMenuData> valueLabel();

        ValueProvider<SubmissionMenuData, String> value();


    }
}
