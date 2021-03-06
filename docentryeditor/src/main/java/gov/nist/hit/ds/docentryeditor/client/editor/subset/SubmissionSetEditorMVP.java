package gov.nist.hit.ds.docentryeditor.client.editor.subset;

import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractMVP;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsSubmissionSet;

/**
 * Class that represents and build the Model View Presenter objects for the Submission set editor view.
 * Created by onh2 on 3/2/2015.
 */
public class SubmissionSetEditorMVP extends AbstractMVP<XdsSubmissionSet,SubmissionSetEditorView,SubmissionSetEditorPresenter> {

    @Override
    public SubmissionSetEditorView buildView() {
        return getView();
    }

    @Override
    public SubmissionSetEditorPresenter buildPresenter() {
        return getPresenter();
    }
}
