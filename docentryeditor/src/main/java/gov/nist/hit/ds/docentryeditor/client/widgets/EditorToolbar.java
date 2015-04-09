package gov.nist.hit.ds.docentryeditor.client.widgets;

import com.google.gwt.event.shared.HandlerRegistration;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import gov.nist.hit.ds.docentryeditor.client.resources.AppImages;
import gov.nist.hit.ds.docentryeditor.client.resources.ToolTipResources;

import javax.inject.Inject;

/**
 * Created by onh2 on 3/3/2015.
 */
public class EditorToolbar extends HorizontalLayoutContainer {
    private HomeButton homeBtn;
    private ToolbarIconButton saveButton;
    private ToolbarIconButton cancelButton;

    private final static int BUTTON_RIGHT_MARGIN=5;

    @Inject
    public EditorToolbar(HomeButton hb) {
        homeBtn=hb;
        saveButton = new ToolbarIconButton("Save",AppImages.INSTANCE.saveBW(),60);
        saveButton.setToolTip(ToolTipResources.INSTANCE.getSaveButtonToolTip());
        cancelButton = new ToolbarIconButton("Cancel changes",AppImages.INSTANCE.back(),110);
        cancelButton.setToolTip(ToolTipResources.INSTANCE.getCancelButtonToolTip());

        this.add(homeBtn, new HorizontalLayoutData(-1, -1, new Margins(0, BUTTON_RIGHT_MARGIN, 0, 0)));
        this.add(saveButton, new HorizontalLayoutData(-1, -1, new Margins(0, BUTTON_RIGHT_MARGIN, 0, 0)));
        this.add(cancelButton, new HorizontalLayoutData(-1, -1, new Margins(0, BUTTON_RIGHT_MARGIN, 0, 0)));
        bindUI();
    }

    private void bindUI() {
//        homeBtn.addSelectHandler(new SelectEvent.SelectHandler() {
//            @Override
//            public void onSelect(SelectEvent selectEvent) {
//                eventBus.fireBackToHomePageEvent();
//            }
//        });
    }

    public HandlerRegistration addSaveHandler(SelectEvent.SelectHandler handler){
        return saveButton.addSelectHandler(handler);
    }

    public HandlerRegistration addCancelHandler(SelectEvent.SelectHandler handler) {
        return cancelButton.addSelectHandler(handler);
    }

    public void addButton(ToolbarIconButton button){
        this.add(button,new HorizontalLayoutData(-1,-1,new Margins(0,BUTTON_RIGHT_MARGIN,0,0)));
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
