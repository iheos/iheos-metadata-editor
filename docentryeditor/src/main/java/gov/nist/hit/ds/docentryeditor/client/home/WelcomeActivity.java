package gov.nist.hit.ds.docentryeditor.client.home;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import gov.nist.hit.ds.docentryeditor.client.generics.ActivityDisplayer;

import javax.inject.Inject;

public class WelcomeActivity extends AbstractActivity {

    @Inject
    ActivityDisplayer displayer;
	@Inject
	WelcomePanel welcomePanel;

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		displayer.display(welcomePanel.asWidget(),panel,eventBus);
	}

	@Override
	public String mayStop() {
		return null;
	}

}
