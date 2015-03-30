package gov.nist.hit.ds.docentryeditor.client.editor.widgets.NameValueWidgets;

import com.google.gwt.core.client.GWT;
import gov.nist.hit.ds.docentryeditor.client.generics.GridModelFactory;
import gov.nist.hit.ds.docentryeditor.shared.model.DTM;

/**
 * Factory class that creates an instance of DTM for a GridEditor
 * (pattern used to solve a problem of java reflection in GWT).
 */
public enum DTMFactory implements GridModelFactory<DTM> {
    instance;

    /**
     * This methods returns a new instance of DTM in order to avoid reflection in GWT.
     * @return new instance of DTM.
     */
    @Override
    public DTM newInstance() {
        return GWT.create(DTM.class);
    }
}
