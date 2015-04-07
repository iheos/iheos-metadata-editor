package gov.nist.hit.ds.docentryeditor.client.root;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;
import gov.nist.hit.ds.docentryeditor.client.generics.GenericMVP;
import gov.nist.hit.ds.docentryeditor.client.root.submission.SubmissionMenuData;
import gov.nist.hit.ds.docentryeditor.client.root.submission.SubmissionPanelPresenter;
import gov.nist.hit.ds.docentryeditor.client.root.submission.SubmissionPanelView;
import gov.nist.hit.ds.docentryeditor.client.utils.MetadataEditorGinInjector;

/**
 * This class contains layout objects to handle the global interface layout,
 * which is divided into 3 parts.
 *
 * @author OLIVIER
 */
public class MetadataEditorAppView extends Viewport {
    private final static MetadataEditorGinInjector INJECTOR = MetadataEditorGinInjector.INSTANCE;
    private static final int WEST_PANEL_WIDTH = 200;

    // NorthPanel north; // interface for file loading and saving
    private CenterPanel center; // main edtior fields
    private GenericMVP<SubmissionMenuData, SubmissionPanelView, SubmissionPanelPresenter> submissionMVP;

    private SimpleContainer simpleContainer;
    private SubmissionPanelView submissionPanelView;
    private SubmissionPanelPresenter submissionPanelPresenter;

    public MetadataEditorAppView() {
        super();

        submissionPanelPresenter = INJECTOR.getSubmissionPanelPresenter();
        submissionPanelView = INJECTOR.getSubmissionPanelView();

        BorderLayoutContainer con = new BorderLayoutContainer();
        con.setBorders(true);

        // NORTH
        // north = INJECTOR.getNorthPanel();
        // BorderLayoutData northData = new BorderLayoutData(35);
        // northData.setMargins(new Margins(5, 5, 5, 5));
        // con.setNorthWidget(north, northData);

        // CENTER
        center = new CenterPanel();
        MarginData centerData = new MarginData(0, 5, 5, 5);
        BorderLayoutContainer c = new BorderLayoutContainer();
        c.setCenterWidget(center, centerData);

        con.setCenterWidget(c);

        // WEST
        submissionMVP = buildSubmissionMVP();
        submissionMVP.init();
        BorderLayoutData westData = new BorderLayoutData(WEST_PANEL_WIDTH);
        westData.setMargins(new Margins(0, 5, 5, 5));
        westData.setCollapsible(true);
        westData.setSplit(false);

        con.setWestWidget(submissionMVP.getDisplay(), westData);


        simpleContainer = new SimpleContainer();
        simpleContainer.add(con, new MarginData(0, 0, /*8*/0, 0));
        simpleContainer.setWidget(con);
        add(simpleContainer);

        //  north.start();
        submissionMVP.start();
    }

    public void setCenterDisplay(Widget display) {
        center.clear();
        center.add(display);
        center.forceLayout();
    }

    public GenericMVP<SubmissionMenuData, SubmissionPanelView, SubmissionPanelPresenter> buildSubmissionMVP() {
        return new GenericMVP<SubmissionMenuData, SubmissionPanelView, SubmissionPanelPresenter>(submissionPanelView, submissionPanelPresenter);
    }

    public void setMarginTop(int marginTop) {
        simpleContainer.getWidget().setLayoutData(new MarginData(0,0,80,0));
    }
}
