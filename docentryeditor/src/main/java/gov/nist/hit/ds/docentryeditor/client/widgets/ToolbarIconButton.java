package gov.nist.hit.ds.docentryeditor.client.widgets;

import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.cell.core.client.ButtonCell;
import com.sencha.gxt.widget.core.client.button.TextButton;

/**
 * Created by onh2 on 4/7/2015.
 */
public class ToolbarIconButton extends TextButton {
    private static final int BUTTON_HEIGHT=30;
    public ToolbarIconButton(String title,ImageResource icon){
        super(title,icon);
        setHeight(BUTTON_HEIGHT);
        setIconAlign(ButtonCell.IconAlign.LEFT);
    }
    public ToolbarIconButton(String title,ImageResource icon,int width){
        this(title,icon);
        setWidth(width);
    }
}
