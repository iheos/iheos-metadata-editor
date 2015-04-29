package gov.nist.hit.ds.docentryeditor.client.root.home;

import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.cell.core.client.ButtonCell;
import com.sencha.gxt.widget.core.client.button.ToggleButton;

/**
 * Class that create a button with icon and text for the home page.
 * Created by onh2 on 4/7/2015.
 */
public class HomeIconButton extends ToggleButton {
    private static final int BUTTON_HEIGHT=50;
    private static final int BUTTON_MIN_WIDTH=130;

    public HomeIconButton(String title, ImageResource icon){
        super(title);
        setIcon(icon);
        setIconAlign(ButtonCell.IconAlign.TOP);
        setHeight(BUTTON_HEIGHT);
        setMinWidth(BUTTON_MIN_WIDTH);
    }
}
