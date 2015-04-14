package gov.nist.hit.ds.docentryeditor.client.utils;

import com.google.web.bindery.requestfactory.shared.RequestFactory;

public interface MetadataEditorRequestFactory extends RequestFactory {
	SaveFileRequestContext saveFileRequestContext();
}
