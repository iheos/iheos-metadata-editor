package gov.nist.hit.ds.docentryeditor.client.root.submission;

import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractMVP;

import javax.inject.Inject;

/**
 * Created by onh2 on 7/11/2014.
 */
public class SubmissionPanelMVP extends AbstractMVP<SubmissionMenuData, SubmissionPanelView, SubmissionPanelPresenter> {

    @Inject
    SubmissionPanelView submissionPanelView;

    @Inject
    SubmissionPanelPresenter submissionPanelPresenter;

    @Override
    public SubmissionPanelView buildView() {
        return submissionPanelView;
    }

    @Override
    public SubmissionPanelPresenter buildPresenter() {
        return submissionPanelPresenter;
    }

}
