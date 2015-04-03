package gov.nist.hit.ds.docentryeditor.client.generics;

import javax.inject.Inject;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

import gov.nist.hit.ds.docentryeditor.client.root.MetadataEditorAppView;

/**
 * Interface to declare a displayer pattern for gwt for activity place design.
 */
public interface ActivityDisplayer {
	public void display(Widget w, AcceptsOneWidget p, EventBus b);

    /**
     * This is the displayer of the Metadata editor application. It enables to make the application more flexible
     * and reduce the amount of code. This way there is only one part of the application that changes through the
     * browser navigation while the rest of the application stays the same and keeps working.
     */
	public class MetadataEditorAppDisplayer implements ActivityDisplayer {
		@Inject
		MetadataEditorAppView appView;

		@Override
		public void display(Widget w, AcceptsOneWidget p, EventBus b) {
			appView.setCenterDisplay(w);
		}
	}
}
