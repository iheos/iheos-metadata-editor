package gov.nist.hit.ds.docentryeditor.client.widgets.uploader;

import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractMVP;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry;

import javax.inject.Inject;

/**
 * Created by onh2 on 4/18/17.
 */
public class ExtCacheFileLoaderMVP extends AbstractMVP<XdsDocumentEntry, ExtCacheFileLoaderView, ExtCacheFileLoaderPresenter> {
    @Inject
    private ExtCacheFileLoaderView extCacheFileLoaderView;
    @Inject
    private ExtCacheFileLoaderPresenter extCacheFileLoaderPresenter;

    @Override
    public ExtCacheFileLoaderView buildView() {
        return extCacheFileLoaderView;
    }

    @Override
    public ExtCacheFileLoaderPresenter buildPresenter() {
        return extCacheFileLoaderPresenter;
    }
}
