package gov.nist.hit.ds.docentryeditor.client.widgets;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import gov.nist.hit.ds.docentryeditor.client.resources.AppImages;
import gov.nist.hit.ds.docentryeditor.client.resources.ToolTipResources;

import javax.inject.Inject;

/**
 * Created by onh2 on 3/3/2015.
 */
public class EditorToolbar extends HorizontalLayoutContainer {
    private static final int BUTTON_RIGHT_MARGIN=5;
    private static final int SAVE_BUTTON_WIDTH = 60;
    private static final int CANCEL_BUTTON_WIDTH = 110;
    private static final int POPULATE_BUTTON_WIDTH = 85;
    private static final int TOOBAR_HEIGHT = 35;

    private HomeButton homeButton;
    private ToolbarIconButton saveButton;
    private ToolbarIconButton cancelButton;
    private ToolbarIconButton populateButton;


    @Inject
    public EditorToolbar(HomeButton homeButton) {
        this.homeButton =homeButton;

        saveButton = new ToolbarIconButton("Save",AppImages.INSTANCE.saveBW(), SAVE_BUTTON_WIDTH);
        saveButton.setToolTip(ToolTipResources.INSTANCE.getSaveButtonToolTip());
        cancelButton = new ToolbarIconButton("Cancel changes",AppImages.INSTANCE.back(),CANCEL_BUTTON_WIDTH);
        cancelButton.setToolTip(ToolTipResources.INSTANCE.getCancelButtonToolTip());
        populateButton =new ToolbarIconButton("Populate",AppImages.INSTANCE.pen(), POPULATE_BUTTON_WIDTH);
        populateButton.setToolTip("Populate the editor form with test data.");

        this.addButton(this.homeButton);
        this.addButton(saveButton);
        this.addButton(cancelButton);
        this.addButton(populateButton);
        this.setHeight(TOOBAR_HEIGHT);
    }

    public HandlerRegistration addSaveHandler(SelectEvent.SelectHandler handler){
        return saveButton.addSelectHandler(handler);
    }

    public HandlerRegistration addCancelHandler(SelectEvent.SelectHandler handler) {
        return cancelButton.addSelectHandler(handler);
    }

    public HandlerRegistration addPopulateHandler(SelectEvent.SelectHandler handler) {
        return populateButton.addSelectHandler(handler);
    }

    public void addButton(ToolbarIconButton button){
        this.add(button,new HorizontalLayoutData(-1,-1,new Margins(0,BUTTON_RIGHT_MARGIN,0,0)));
    }

    public void removeButton(int buttonIndex){
        this.remove(buttonIndex);
    }

    public void removeButton(boolean removeHomeButton,boolean removeSaveButton,
                             boolean removeCancelButton, boolean removePopulateButton){
        removeWidget(removeHomeButton, homeButton);
        removeWidget(removeSaveButton, saveButton);
        removeWidget(removeCancelButton, cancelButton);
        removeWidget(removePopulateButton, populateButton);
    }

    private void removeWidget(boolean removeWidget, Widget widget) {
        if (removeWidget){
            this.remove(widget);
        }
    }

}
