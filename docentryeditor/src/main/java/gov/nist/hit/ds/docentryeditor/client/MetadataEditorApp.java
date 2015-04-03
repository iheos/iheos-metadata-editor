package gov.nist.hit.ds.docentryeditor.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import gov.nist.hit.ds.docentryeditor.client.event.MetadataEditorEventBus;
import gov.nist.hit.ds.docentryeditor.client.home.WelcomePlace;
import gov.nist.hit.ds.docentryeditor.client.root.MetadataEditorAppView;
import gov.nist.hit.ds.docentryeditor.client.utils.MetadataEditorActivityMapper;
import gov.nist.hit.ds.docentryeditor.client.utils.MetadataEditorAppPlaceHistoryMapper;
import gov.nist.hit.ds.docentryeditor.client.utils.MetadataEditorGinInjector;

import java.util.logging.Logger;

/**
 * This is the class to use to create the application.
 */
public class MetadataEditorApp implements IsWidget {
    private final static MetadataEditorGinInjector injector = MetadataEditorGinInjector.instance;
    protected static Logger logger = Logger.getLogger(MetadataEditorApp.class.getName());
    private final MetadataEditorEventBus eventBus = injector.getEventBus();
    private final SimplePanel activityPanel = new SimplePanel();
    private MetadataEditorAppView appView;

    public MetadataEditorApp(){
        appView = injector.getMetadataEditorAppView();

        PlaceController placeController = injector.getPlaceController();

        MetadataEditorActivityMapper activityMapper = new MetadataEditorActivityMapper();
        ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
        activityManager.setDisplay(activityPanel);

        MetadataEditorAppPlaceHistoryMapper historyMapper = GWT.create(MetadataEditorAppPlaceHistoryMapper.class);
        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);

        historyHandler.register(placeController, eventBus, new WelcomePlace("Welcome"));

        activityPanel.add(appView.asWidget());

        historyHandler.handleCurrentHistory();
//        appView.addResizeHandler(new ResizeHandler() {
//            @Override
//            public void onResize(ResizeEvent event) {
//                appView.forceLayout();
//                appView.setResize(true);
//            }
//        });
    }

    @Override
    public Widget asWidget() {
        return activityPanel;
    }

    public MetadataEditorAppView getAppView(){
        return appView;
    }

    public static MetadataEditorGinInjector getInjector() {
        return injector;
    }

    public MetadataEditorEventBus getEventBus() {
        return eventBus;
    }
}
