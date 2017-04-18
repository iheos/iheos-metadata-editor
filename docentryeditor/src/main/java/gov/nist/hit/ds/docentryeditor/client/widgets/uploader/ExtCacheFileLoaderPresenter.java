package gov.nist.hit.ds.docentryeditor.client.widgets.uploader;

import com.google.web.bindery.requestfactory.shared.Receiver;
import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractPresenter;
import gov.nist.hit.ds.docentryeditor.client.utils.Services.MetadataEditorRequestFactory;
import gov.nist.hit.ds.docentryeditor.client.widgets.environment.EnvironmentState;
import gov.nist.hit.ds.docentryeditor.client.widgets.session.SessionState;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by onh2 on 4/18/17.
 */
public class ExtCacheFileLoaderPresenter extends AbstractPresenter<ExtCacheFileLoaderView>{
    @Inject
    private MetadataEditorRequestFactory requestFactory;
    @Inject
    private EnvironmentState environmentState;
    @Inject
    private SessionState sessionState;

    private String selectedTransactionType;
    private String selectedTestdataInstance;

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
}
