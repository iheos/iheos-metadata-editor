package gov.nist.hit.ds.docentryeditor.client.widgets;

import com.google.gwt.user.client.ui.Image;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import gov.nist.hit.ds.docentryeditor.client.resources.AppImages;

/**
 * Widget to display a warning message in a red bordered panel at the bottom of the page
 */
public class PageWarningPanel extends VerticalLayoutContainer{
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
        hlc.add(i, new HorizontalLayoutContainer.HorizontalLayoutData(-1, -1, new Margins(0, 0, 0, 5)));
        hlc.add(l, new HorizontalLayoutContainer.HorizontalLayoutData(1, -1, new Margins(0, 0, 0, 5)));

        // set the red bordered warning panel
        this.add(hlc, new VerticalLayoutData(1,100));
        // this.setHeaderVisible(false);
        // this.setBodyBorder(false);
        // this.setHeight(100);
        this.setStyleName("warning-panel");
    }
}
