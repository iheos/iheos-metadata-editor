package gov.nist.hit.ds.docentryeditor.client.editor.widgets.NameValueWidgets;

import com.google.gwt.core.client.GWT;
import gov.nist.hit.ds.docentryeditor.client.generics.GridModelFactory;
import gov.nist.hit.ds.docentryeditor.shared.model.String256;

/**
 * Factory class that creates an instance of String256 for a GridEditor
 * (pattern used to solve a problem of java reflection in GWT).
 */
public enum String256Factory implements GridModelFactory<String256> {
    instance;
    private int counter=0;

    /**
     * This methods returns a new instance of String256 in order to avoid reflection in GWT.
     * @return new instance of String256.
     */
    @Override
    public String256 newInstance() {
        counter++;
        String256 value=GWT.create(String256.class);
        value.setString("new value "+counter);
        return value;
    }
}
