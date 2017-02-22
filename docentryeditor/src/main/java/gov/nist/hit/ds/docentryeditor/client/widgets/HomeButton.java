package gov.nist.hit.ds.docentryeditor.client.widgets;

import com.sencha.gxt.widget.core.client.event.SelectEvent;
import gov.nist.hit.ds.docentryeditor.client.eventbus.MetadataEditorEventBus;
import gov.nist.hit.ds.docentryeditor.client.resources.AppImages;

import javax.inject.Inject;

/**
 * Button to return to the Home page.
 */
public class HomeButton extends ToolbarIconButton {

    public static final int HOME_BUTTON_WIDTH = 130;
    @Inject
    private MetadataEditorEventBus eventBus;

    public HomeButton() {
        super("Back to home page", AppImages.INSTANCE.home());
        setToolTip("Navigate back to the home page");
        setWidth(HOME_BUTTON_WIDTH);
        bindUI();
    }

    private void bindUI() {
        addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent selectEvent) {
                eventBus.fireBackToHomePageEvent();
            }
        });
    }
}
