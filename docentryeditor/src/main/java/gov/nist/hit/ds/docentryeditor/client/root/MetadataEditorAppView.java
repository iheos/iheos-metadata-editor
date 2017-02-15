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

import javax.inject.Inject;

/**
 * This class contains layout objects to handle the global interface layout,
 * which is divided into 3 parts.
 *
 * @author OLIVIER
 */
public class MetadataEditorAppView extends Viewport {
    private static final int WEST_PANEL_WIDTH = 200;
    private static final int PANELS_MARGINS = 5;
    // main panel
    private CenterPanel center;
    private NorthPanel north;
    private GenericMVP<SubmissionMenuData, SubmissionPanelView, SubmissionPanelPresenter> submissionMVP;

    private SimpleContainer simpleContainer;
    private SubmissionPanelView submissionPanelView;
    private SubmissionPanelPresenter submissionPanelPresenter;

    @Inject
    public MetadataEditorAppView(SubmissionPanelView submissionPanelView,SubmissionPanelPresenter submissionPanelPresenter,NorthPanel north) {
        super();

        this.submissionPanelPresenter = submissionPanelPresenter;
        this.submissionPanelView = submissionPanelView;

        BorderLayoutContainer con = new BorderLayoutContainer();
        con.setBorders(true);

        BorderLayoutContainer c = new BorderLayoutContainer();

        // NORTH
        this.north = north;
        BorderLayoutData northData = new BorderLayoutData(50);
        northData.setMargins(new Margins(PANELS_MARGINS));
        northData.setCollapsible(false);
        northData.setSplit(false);

        c.setNorthWidget(north, northData);

        // CENTER
        center = new CenterPanel();
        MarginData centerData = new MarginData(PANELS_MARGINS);
        c.setCenterWidget(center, centerData);

        con.setCenterWidget(c);

        // WEST
        submissionMVP = buildSubmissionMVP();
        submissionMVP.init();
        BorderLayoutData westData = new BorderLayoutData(WEST_PANEL_WIDTH);
        westData.setMargins(new Margins(PANELS_MARGINS));
        westData.setCollapsible(true);
        westData.setSplit(false);

        con.setWestWidget(submissionMVP.getDisplay(), westData);


        simpleContainer = new SimpleContainer();
        simpleContainer.add(con, new MarginData(0));
        simpleContainer.setWidget(con);
        add(simpleContainer);

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
        simpleContainer.getWidget().setLayoutData(new MarginData(0,0,marginTop,0));
    }
}
