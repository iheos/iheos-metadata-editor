package gov.nist.hit.ds.docentryeditor.client.utils.Services;

import com.google.web.bindery.requestfactory.shared.RequestFactory;

public interface MetadataEditorRequestFactory extends RequestFactory {
	SaveFileRequestContext saveFileRequestContext();
	ExtCacheRequestContext extCacheRequestContext();
}
