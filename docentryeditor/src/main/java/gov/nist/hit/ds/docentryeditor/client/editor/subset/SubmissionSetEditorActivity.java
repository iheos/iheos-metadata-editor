package gov.nist.hit.ds.docentryeditor.client.editor.subset;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.widget.core.client.*;
import com.sencha.gxt.widget.core.client.container.*;
import gov.nist.hit.ds.docentryeditor.client.event.MetadataEditorEventBus;
import gov.nist.hit.ds.docentryeditor.client.generics.*;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsSubmissionSet;

import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * Place for the Submission Set editor.
 * This enables the navigation within the application thanks to
 * the Activity-place design.
 */
public class SubmissionSetEditorActivity extends AbstractActivity{
    private static final int VALIDATION_PANEL_HEIGHT = 250;
    @Inject
    private ActivityDisplayer displayer;
    @Inject
    private MetadataEditorEventBus metadataEditorEventBus;

    private GenericMVP<XdsSubmissionSet,SubmissionSetEditorView,SubmissionSetEditorPresenter> editorMVP;
    @Inject
    private SubmissionSetEditorView editorView;
    @Inject
    private SubmissionSetEditorPresenter editorPresenter;



    // method automatically called in the GWT Activity-Place design.
    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        editorMVP=buildEditorMVP();
        editorMVP.init();
        displayer.display(getContainer(), panel, eventBus);
        // timer to solve a gwt issue (ugly)
        Timer t = new Timer() {
            @Override
            public void run() {
                Logger.getLogger(this.getClass().getName()).info("Fire Editor UI loaded event...");
                // signal that the editor view has loaded.
                metadataEditorEventBus.fireXdsEditorLoadedEvent();
            }
        };

        // Schedule the timer to run once in 1 milliseconds.
        t.schedule(1);
    }

    /**
     * Build the editor layout with borderlayoutcontainer for the Submission Set.
     * Made of a main panel for the editor itself and a collapsible and splittable validation panel underneath.
     *
     * @return widget editor container
     */
    private Widget getContainer() {
        SimpleContainer sc = new SimpleContainer();
        BorderLayoutContainer blc = new BorderLayoutContainer();

        ContentPanel center = new ContentPanel();
        center.setHeaderVisible(false);
        center.add(editorMVP.getDisplay());
        blc.setCenterWidget(center);

        ContentPanel validationView = new ContentPanel();
        validationView.setHeadingText("Validation");

        BorderLayoutContainer.BorderLayoutData southData = new BorderLayoutContainer.BorderLayoutData(VALIDATION_PANEL_HEIGHT);
        southData.setCollapsible(true);
        southData.setSplit(true);
        southData.setCollapsed(true);
        blc.setSouthWidget(validationView, southData);

        blc.collapse(Style.LayoutRegion.SOUTH);

        sc.add(blc);

        return sc.asWidget();
    }

    /**
     * Method that builds the Submission Set Editor (view and presenter for a specific model => Submission Set).
     * @return Submission Set Editor MVP.
     */
    private GenericMVP<XdsSubmissionSet, SubmissionSetEditorView, SubmissionSetEditorPresenter> buildEditorMVP() {
        return new GenericMVP<XdsSubmissionSet,SubmissionSetEditorView,SubmissionSetEditorPresenter>(editorView,editorPresenter);
    }
}
