package gov.nist.hit.ds.docentryeditor.client.editor.widgets;

import com.google.gwt.event.shared.HandlerRegistration;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.cell.core.client.ButtonCell;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import gov.nist.hit.ds.docentryeditor.client.resources.AppImages;
import gov.nist.hit.ds.docentryeditor.client.widgets.HomeButton;

/**
 * Created by onh2 on 3/3/2015.
 */
public class EditorToolbar extends HorizontalLayoutContainer {
    private HomeButton homeBtn;
    private TextButton saveButton;
    private TextButton cancelButton;

    public EditorToolbar() {
        homeBtn = new HomeButton();
        homeBtn.setHeight(30);
        homeBtn.setWidth(130);
        saveButton = new TextButton("Save");
        saveButton.setHeight(30);
        saveButton.setWidth(60);
        saveButton.setIcon(AppImages.INSTANCE.saveBW());
        saveButton.setIconAlign(ButtonCell.IconAlign.LEFT);
        cancelButton = new TextButton("Cancel changes");
        cancelButton.setHeight(30);
        cancelButton.setWidth(110);
        cancelButton.setIcon(AppImages.INSTANCE.back());
        cancelButton.setIconAlign(ButtonCell.IconAlign.LEFT);

        this.add(homeBtn, new HorizontalLayoutData(-1, -1, new Margins(0, 5, 0, 0)));
        this.add(saveButton, new HorizontalLayoutData(-1, -1, new Margins(0, 5, 0, 0)));
        this.add(cancelButton, new HorizontalLayoutData(-1, -1, new Margins(0, 5, 0, 0)));
    }

    public HandlerRegistration addSaveHandler(SelectEvent.SelectHandler handler){
        return saveButton.addSelectHandler(handler);
    }

    public HandlerRegistration addCancelHandler(SelectEvent.SelectHandler handler) {
        return cancelButton.addSelectHandler(handler);
    }

    public HomeButton getHomeBtn() {
        return homeBtn;
    }

    public TextButton getSaveButton() {
        return saveButton;
    }

    public TextButton getCancelButton() {
        return cancelButton;
    }
}
