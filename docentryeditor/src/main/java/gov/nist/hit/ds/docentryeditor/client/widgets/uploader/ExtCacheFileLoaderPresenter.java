package gov.nist.hit.ds.docentryeditor.client.widgets.uploader;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.requestfactory.shared.Receiver;
import gov.nist.hit.ds.docentryeditor.client.eventbus.MetadataEditorEventBus;
import gov.nist.hit.ds.docentryeditor.client.eventbus.events.NewFileLoadedEvent;
import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractPresenter;
import gov.nist.hit.ds.docentryeditor.client.parser.XdsParserServices;
import gov.nist.hit.ds.docentryeditor.client.parser.XdsParserServicesAsync;
import gov.nist.hit.ds.docentryeditor.client.utils.Services.MetadataEditorRequestFactory;
import gov.nist.hit.ds.docentryeditor.client.widgets.environment.EnvironmentState;
import gov.nist.hit.ds.docentryeditor.client.widgets.session.SessionState;
import gov.nist.hit.ds.docentryeditor.shared.EnvSessionRequestContext;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsMetadata;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by onh2 on 4/18/17.
 */
public class ExtCacheFileLoaderPresenter extends AbstractPresenter<ExtCacheFileLoaderView>{
    // RPC services declaration
    private final XdsParserServicesAsync xdsParserServices = GWT
            .create(XdsParserServices.class);

    @Inject
    private MetadataEditorRequestFactory requestFactory;
    @Inject
    private EnvironmentState environmentState;
    @Inject
    private SessionState sessionState;

    private String selectedTransactionType;
    private String selectedTestdataInstance;
    private String selectedSection;

    @Override
    public void init() {

    }

    /**
     * Request values for the transaction type section.
     */
    public void retrieveTransactionTypes() {
        view.clearTransactionTypeRadioGroup();
        requestFactory.extCacheRequestContext().getTransactionTypes().fire(new Receiver<List<String>>() {
            @Override
            public void onSuccess(List<String> response) {
                for (String s:response) {
                    view.addTransacationRadio(s);
                }
            }
        });
    }

    public void retrieveTestdataInstanceNames(String selectedTransactionType) {
        this.selectedTransactionType=selectedTransactionType;
        this.selectedTestdataInstance=null;
        this.selectedSection=null;
        requestFactory.extCacheRequestContext()
                .getTestNames(environmentState.getSelectedEnvironment(),sessionState.getSelectedSession(),selectedTransactionType)
                .fire(new Receiver<List<String>>() {
                    @Override
                    public void onSuccess(List<String> response) {
                        view.populateTestdataCombo(response);
                    }
                });
    }

    public void retrieveSections(String selectedTestdataInstance) {
        this.selectedTestdataInstance=selectedTestdataInstance;
        logger.info("Retrieve sections");
        requestFactory.extCacheRequestContext()
                .getSectionNames(environmentState.getSelectedEnvironment(),sessionState.getSelectedSession(),selectedTransactionType,selectedTestdataInstance)
                .fire(new Receiver<List<String>>() {
                    @Override
                    public void onSuccess(List<String> response) {
                        view.showSections(response);
                    }
                });
    }

    public void loadMetadataFile() {
        xdsParserServices.retrieveFromExtCache(new EnvSessionRequestContext(environmentState.getSelectedEnvironment(), sessionState.getSelectedSession()),
                selectedTransactionType, selectedTestdataInstance, selectedSection, new AsyncCallback<XdsMetadata>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Failure!");
                    }

                    @Override
                    public void onSuccess(XdsMetadata xdsMetadata) {
                        logger.info("... file parsed.");
                        getEventBus().fireEvent(new NewFileLoadedEvent(xdsMetadata));
                    }
                });
    }

    public void setSelectedSession(String selectedSession) {
        this.selectedSection = selectedSession;
    }
}
