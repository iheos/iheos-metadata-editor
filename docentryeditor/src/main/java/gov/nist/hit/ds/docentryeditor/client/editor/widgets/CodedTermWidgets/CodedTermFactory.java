package gov.nist.hit.ds.docentryeditor.client.editor.widgets.CodedTermWidgets;

import com.google.gwt.core.client.GWT;
import gov.nist.hit.ds.docentryeditor.client.generics.GridModelFactory;
import gov.nist.hit.ds.docentryeditor.shared.model.CodedTerm;
import gov.nist.hit.ds.docentryeditor.shared.model.CodingScheme;
import gov.nist.hit.ds.docentryeditor.shared.model.String256;

/**
 * Factory class that creates an instance of CodedTerm for a GridEditor
 * (pattern used to solve a problem of java reflection in GWT).
 */
public enum CodedTermFactory implements GridModelFactory<CodedTerm> {
    instance;
    private int counter=0;

    /**
     * This methods returns a new instance of CodedTerm in order to avoid reflection in GWT.
     * @return new instance of CodedTerm.
     */
    @Override
    public CodedTerm newInstance() {
        counter++;
        CodedTerm e = GWT.create(CodedTerm.class);
        e.setDisplayName(new String256("New Coded Term name"));
        e.setCodingScheme(new CodingScheme(new String256("New Coded Term coding scheme")));
        e.setCode(new String256("New Coded term code "+counter));
        return e;
    }
}
