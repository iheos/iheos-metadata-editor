package gov.nist.hit.ds.docentryeditor.client.editor.widgets.NameValueWidgets;

import gov.nist.hit.ds.docentryeditor.client.generics.GridModelFactory;

/**
 * Factory class that creates an instance of Integer for a GridEditor
 * (pattern used to solve a problem of java reflection in GWT).
 */
public enum IntegerFactory implements GridModelFactory<Integer> {
    instance;

    /**
     * This methods returns a new instance of Integer in order to avoid reflection in GWT.
     * @return new instance of Integer.
     */
    @Override
    public Integer newInstance() {
        return new Integer(0);
    }
}
