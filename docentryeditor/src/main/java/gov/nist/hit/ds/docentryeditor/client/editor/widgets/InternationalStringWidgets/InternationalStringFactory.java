package gov.nist.hit.ds.docentryeditor.client.editor.widgets.InternationalStringWidgets;

import com.google.gwt.core.client.GWT;
import gov.nist.hit.ds.docentryeditor.client.generics.GridModelFactory;
import gov.nist.hit.ds.docentryeditor.shared.model.InternationalString;
import gov.nist.hit.ds.docentryeditor.shared.model.LanguageCode;
import gov.nist.hit.ds.docentryeditor.shared.model.String256;

/**
 * Factory class that creates an instance of InternationalString for a GridEditor
 * (pattern used to solve a problem of java reflection in GWT).
 */
public enum InternationalStringFactory implements GridModelFactory<InternationalString> {
    instance;
    private int counter = 1;

    /**
     * This methods returns a new instance of InternationalString in order to avoid reflection in GWT.
     * @return new instance of InternationalString.
     */
    @Override
    public InternationalString newInstance() {
        InternationalString is = GWT.create(InternationalString.class);
        is.setLangCode(LanguageCode.ENGLISH_UNITED_STATES);
        is.setValue(new String256("New translation "+counter));
        counter++;
        return is;
    }
}
