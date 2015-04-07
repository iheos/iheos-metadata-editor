package gov.nist.hit.ds.docentryeditor.client.widgets.uploader;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import gov.nist.hit.ds.docentryeditor.client.event.NewFileLoadedEvent;
import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractPresenter;
import gov.nist.hit.ds.docentryeditor.client.parser.XdsParserServices;
import gov.nist.hit.ds.docentryeditor.client.parser.XdsParserServicesAsync;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsMetadata;

public class FileUploadPresenter extends AbstractPresenter<FileUploadView> {

    // RPC services declaration
    private final static XdsParserServicesAsync xdsParserServices = GWT
            .create(XdsParserServices.class);

    public void fileUploaded(String results) {
        // remove xml file first line (xml doctype => <?xml...>)
        logger.info("... file loaded, parsing metadata...");
        String s=results.replace("&lt;","<");
        s=s.replace("&gt;",">");
        s=s.replace("&amp;","&");
        xdsParserServices.parseXdsMetadata(s, new AsyncCallback<XdsMetadata>() {
            @Override
            public void onFailure(Throwable throwable) {
                logger.warning(throwable.getMessage());
            }

            @Override
            public void onSuccess(XdsMetadata xdsMetadata) {
                XdsDocumentEntry model = xdsMetadata.getDocumentEntries().get(0);

                logger.info("... file parsed.");
                // logger.info("Metadata file: " + model.toXML());

                getEventBus().fireEvent(new NewFileLoadedEvent(xdsMetadata));
            }
        });
    }

    @Override
    public void init() {
        // don't need to do anything in that particular case
    }
}
