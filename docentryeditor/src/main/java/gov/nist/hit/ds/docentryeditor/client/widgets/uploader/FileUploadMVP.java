package gov.nist.hit.ds.docentryeditor.client.widgets.uploader;

import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractMVP;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry;

import javax.inject.Inject;

public class FileUploadMVP extends AbstractMVP<XdsDocumentEntry, FileUploadView, FileUploadPresenter> {
    @Inject
    FileUploadView fileUploadView;
    @Inject
    FileUploadPresenter fileUploadPresenter;

    public FileUploadMVP() {

    }

    @Override
    public FileUploadView buildView() {
        return fileUploadView;
    }

    @Override
    public FileUploadPresenter buildPresenter() {
        return fileUploadPresenter;
    }

}
