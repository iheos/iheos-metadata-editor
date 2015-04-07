package gov.nist.hit.ds.docentryeditor.client.widgets;

import com.sencha.gxt.widget.core.client.event.SelectEvent;
import gov.nist.hit.ds.docentryeditor.client.event.MetadataEditorEventBus;
import gov.nist.hit.ds.docentryeditor.client.resources.AppImages;
import gov.nist.hit.ds.docentryeditor.client.utils.MetadataEditorGinInjector;

/**
 * Button to return to the Home page.
 */
public class HomeButton extends ToolbarIconButton {

    private MetadataEditorEventBus eventBus;

    public HomeButton() {
        super("Back to home page",AppImages.INSTANCE.home());
        setToolTip("Navigate back to the home page");
        eventBus= MetadataEditorGinInjector.INSTANCE.getEventBus();
        setWidth(130);
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
