package gov.nist.hit.ds.docentryeditor.client.editor.association;

import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractMVP;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsAssociation;

/**
 * Class that represents and build the Model View Presenter objects for the Association editor view.
 */
public class AssociationEditorMVP extends AbstractMVP<XdsAssociation,AssociationEditorView,AssociationEditorPresenter> {

    @Override
    public AssociationEditorView buildView() {
        return getView();
    }

    @Override
    public AssociationEditorPresenter buildPresenter() {
        return getPresenter();
    }
}
