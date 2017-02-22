package gov.nist.hit.ds.docentryeditor.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import gov.nist.hit.ds.docentryeditor.client.eventbus.MetadataEditorEventBus;
import gov.nist.hit.ds.docentryeditor.client.root.home.WelcomePlace;
import gov.nist.hit.ds.docentryeditor.client.root.MetadataEditorAppView;
import gov.nist.hit.ds.docentryeditor.client.utils.MetadataEditorActivityMapper;
import gov.nist.hit.ds.docentryeditor.client.utils.MetadataEditorAppPlaceHistoryMapper;
import gov.nist.hit.ds.docentryeditor.client.utils.MetadataEditorGinInjector;

/**
 * This is the class to use to create the application.
 */
public class MetadataEditorApp implements IsWidget {
    private static final MetadataEditorGinInjector INJECTOR = MetadataEditorGinInjector.INSTANCE;
    private final MetadataEditorEventBus eventBus = INJECTOR.getEventBus();

    private SimplePanel activityPanel = new SimplePanel();
    private MetadataEditorAppView appView;

    public MetadataEditorApp(){
        appView = INJECTOR.getMetadataEditorAppView();

        PlaceController placeController = INJECTOR.getPlaceController();

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
//            public void onResize(ResizeEvent eventbus) {
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
        return INJECTOR;
    }

    public MetadataEditorEventBus getEventBus() {
        return eventBus;
    }
}
