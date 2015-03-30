package gov.nist.hit.ds.docentryeditor.client.editor.documentEntryEditor;

import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractMVP;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry;

import javax.inject.Inject;

/**
 * Class that represents and build the Model View Presenter objects for the Document entry editor view.
 */
public class DocumentEntryEditorMVP extends AbstractMVP<XdsDocumentEntry, DocumentEntryEditorView, DocumentEntryEditorPresenter> {

    @Inject
    DocumentEntryEditorView editorView;

    @Inject
    DocumentEntryEditorPresenter editorPresenter;

    @Override
    public DocumentEntryEditorView buildView() {
        return editorView;
    }

    @Override
    public DocumentEntryEditorPresenter buildPresenter() {
        // editorPresenter.initDriver();
        return editorPresenter;
    }

}
