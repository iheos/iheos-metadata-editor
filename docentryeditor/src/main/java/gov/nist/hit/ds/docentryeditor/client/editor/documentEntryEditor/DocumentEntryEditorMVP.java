package gov.nist.hit.ds.docentryeditor.client.editor.documentEntryEditor;

import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractMVP;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry;

/**
 * Class that represents and build the Model View Presenter objects for the Document entry editor view.
 */
public class DocumentEntryEditorMVP extends AbstractMVP<XdsDocumentEntry, DocumentEntryEditorView, DocumentEntryEditorPresenter> {

    @Override
    public DocumentEntryEditorView buildView() {
        return getView();
    }

    @Override
    public DocumentEntryEditorPresenter buildPresenter() {
        // editorPresenter.initDriver();
        return getPresenter();
    }

}
