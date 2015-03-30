package gov.nist.hit.ds.docentryeditor.client.editor;

/**
 * Enumerated class to handle the different possible status for a sub-editor.
 * Especially useful to handle editor's display.
 * It is used to know the state of the editor, to know how to display the different
 * fields (enable/disable especially)
 */
public enum EditionMode {
    NODATA, DISPLAY, NEW, EDIT;
}
