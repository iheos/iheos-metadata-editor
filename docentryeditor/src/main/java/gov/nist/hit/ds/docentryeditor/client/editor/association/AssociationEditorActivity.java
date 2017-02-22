package gov.nist.hit.ds.docentryeditor.client.editor.association;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import gov.nist.hit.ds.docentryeditor.client.eventbus.MetadataEditorEventBus;
import gov.nist.hit.ds.docentryeditor.client.generics.ActivityDisplayer;
import gov.nist.hit.ds.docentryeditor.client.generics.GenericMVP;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsAssociation;

import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * Created by onh2 on 5/7/2015.
 */
public class AssociationEditorActivity extends AbstractActivity{
    @Inject
    private ActivityDisplayer displayer;

    private GenericMVP<XdsAssociation,AssociationEditorView,AssociationEditorPresenter> editorMVP;

    @Inject
    private AssociationEditorView editorView;
    @Inject
    private AssociationEditorPresenter editorPresenter;

    @Inject
    private MetadataEditorEventBus metadataEditorEventBus;

    private SimpleContainer sc;

    // this method is automatically called in the GWT Activity - Places process.
    @Override
    public void start(AcceptsOneWidget acceptsOneWidget, EventBus eventBus) {
        editorMVP=buildEditorMVP();
        editorMVP.init();
        displayer.display(getContainer(),acceptsOneWidget,eventBus);
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
     * Build editor layout for the Association editor.
     *
     * @return widget editor container
     */
    private Widget getContainer() {
        sc=new SimpleContainer();

        ContentPanel mainPanel=new ContentPanel();
        mainPanel.setHeaderVisible(false);
        mainPanel.add(editorMVP.getDisplay());

        sc.add(mainPanel);
        return sc.asWidget();
    }

    private GenericMVP<XdsAssociation, AssociationEditorView, AssociationEditorPresenter> buildEditorMVP() {
        return new GenericMVP<XdsAssociation, AssociationEditorView, AssociationEditorPresenter>(editorView, editorPresenter);
    }
}
