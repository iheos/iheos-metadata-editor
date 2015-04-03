package gov.nist.hit.ds.docentryeditor.client.home;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import gov.nist.hit.ds.docentryeditor.client.root.MetadataEditorAppView;

import javax.inject.Inject;

public class WelcomeActivity extends AbstractActivity {

    @Inject
	MetadataEditorAppView appView;
	@Inject
	WelcomePanel welcomePanel;

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		appView.setCenterDisplay(welcomePanel.asWidget());
	}

	@Override
	public String mayStop() {
		return null;
	}

}
