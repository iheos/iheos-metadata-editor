package gov.nist.hit.ds.docentryeditor.client.widgets;

import com.google.gwt.user.client.ui.Image;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import gov.nist.hit.ds.docentryeditor.client.resources.AppImages;

/**
 * Widget to display a warning message in a red bordered panel at the bottom of the page
 */
public class PageWarningPanel extends VerticalLayoutContainer{

    private static final int LEFT_MARGIN = 5;
    private static final int HEIGHT = 100;

    /**
     * Default constructor
     */
    public PageWarningPanel(String message){
        // triangle warning icon
        Image i = new Image(AppImages.INSTANCE.warning());

        // warning message
        HtmlLayoutContainer l = new HtmlLayoutContainer(message);
        l.setStyleName("warning-message");

        // render the icon warning message
        HorizontalLayoutContainer hlc=new HorizontalLayoutContainer();
        hlc.add(i, new HorizontalLayoutContainer.HorizontalLayoutData(-1, -1, new Margins(0, 0, 0, LEFT_MARGIN)));
        hlc.add(l, new HorizontalLayoutContainer.HorizontalLayoutData(1, -1, new Margins(0, 0, 0, LEFT_MARGIN)));

        // set the red bordered warning panel
        this.add(hlc, new VerticalLayoutData(1, HEIGHT));
        this.setStyleName("warning-panel");
    }
}
