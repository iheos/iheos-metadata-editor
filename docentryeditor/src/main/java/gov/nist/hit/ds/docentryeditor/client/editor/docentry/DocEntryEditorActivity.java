package gov.nist.hit.ds.docentryeditor.client.editor.docentry;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style.LayoutRegion;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import gov.nist.hit.ds.docentryeditor.client.editor.validation.ValidationPresenter;
import gov.nist.hit.ds.docentryeditor.client.editor.validation.ValidationView;
import gov.nist.hit.ds.docentryeditor.client.eventbus.MetadataEditorEventBus;
import gov.nist.hit.ds.docentryeditor.client.generics.ActivityDisplayer;
import gov.nist.hit.ds.docentryeditor.client.generics.GenericMVP;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry;

import javax.inject.Inject;
import java.util.logging.Logger;

public class DocEntryEditorActivity extends AbstractActivity {

    public static final int VALIDATION_PANEL_HEIGHT = 250;
    @Inject
    private ActivityDisplayer displayer;

    private GenericMVP<XdsDocumentEntry, DocumentEntryEditorView, DocumentEntryEditorPresenter> editorMVP;
    private GenericMVP<XdsDocumentEntry, ValidationView, ValidationPresenter> validationMVP;

    @Inject
    private DocumentEntryEditorView editorView;
    @Inject
    private DocumentEntryEditorPresenter editorPresenter;

    @Inject
    private ValidationView validationView;
    @Inject
    private ValidationPresenter validationPresenter;

    @Inject
    private MetadataEditorEventBus metadataEditorEventBus;

    private SimpleContainer sc;
    private BorderLayoutContainer blc;

    // this method is automatically called in the GWT Activity - Places process.
    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        editorMVP = buildEditorMVP();
        editorMVP.init();
        validationMVP = buildValidationMVP();
        validationMVP.init();
        displayer.display(getContainer(), panel, eventBus);
        // timer to solve a gwt issue (ugly)
        Timer t = new Timer() {
            @Override
            public void run() {
                Logger.getLogger(this.getClass().getName()).info("Fire Doc. Entry Editor UI loaded eventbus...");
                // signal that the document entry editor view has loaded.
                metadataEditorEventBus.fireXdsEditorLoadedEvent();
            }
        };

        // Schedule the timer to run once in 1 milliseconds.
        t.schedule(1);
    }

    /**
     * Build editor layout with borderlayoutcontainer for the Document Entry.
     * Made of a main panel for the editor itself and a collapsible and splittable validation panel underneath.
     *
     * @return widget editor container
     */
    private Widget getContainer() {
        sc = new SimpleContainer();
        blc = new BorderLayoutContainer();

        ContentPanel validationViewCP = new ContentPanel();
        validationViewCP.setHeadingText("Validation");

        ContentPanel center = new ContentPanel();
        center.setHeaderVisible(false);
        center.add(editorMVP.getDisplay());

        blc.setCenterWidget(center);

        BorderLayoutData southData = new BorderLayoutData(VALIDATION_PANEL_HEIGHT);
        southData.setCollapsible(true);
        southData.setSplit(true);
        southData.setCollapsed(true);

        blc.setSouthWidget(validationViewCP, southData);

        blc.collapse(LayoutRegion.SOUTH);

        sc.add(blc);

        return sc.asWidget();
    }

    /**
     * Method that builds the Document Entry Editor (MVP).
     * @return Document Entry Editor MVP.
     */
    public GenericMVP<XdsDocumentEntry, DocumentEntryEditorView, DocumentEntryEditorPresenter> buildEditorMVP() {
        return new GenericMVP<XdsDocumentEntry, DocumentEntryEditorView, DocumentEntryEditorPresenter>(editorView, editorPresenter);
    }

    /**
     * Method that builds the Validation view (MVP).
     * @return Validation view MVP.
     */
    public GenericMVP<XdsDocumentEntry, ValidationView, ValidationPresenter> buildValidationMVP() {
        return new GenericMVP<XdsDocumentEntry, ValidationView, ValidationPresenter>(validationView, validationPresenter);
    }
}
