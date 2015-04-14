package gov.nist.hit.ds.docentryeditor.client.utils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import gov.nist.hit.ds.docentryeditor.client.event.MetadataEditorEventBus;
import gov.nist.hit.ds.docentryeditor.client.generics.ActivityDisplayer;
import gov.nist.hit.ds.docentryeditor.client.generics.ActivityDisplayer.MetadataEditorAppDisplayer;
import gov.nist.hit.ds.docentryeditor.client.root.MetadataEditorAppView;

import javax.inject.Inject;

/**
 * Class that binds the various classes and providers.
 *
 * Note that if GIN can't find a binding for a class,
 * it falls back to calling GWT.create() on that class.
 * What this means that image bundles and translated
 * messages will just magically work.
 */
public class MetadataEditorGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(com.google.web.bindery.event.shared.EventBus.class).to(MetadataEditorEventBus.class);
        // bind(com.google.gwt.event.shared.EventBus.class).to(MetadataEditorEventBus.class);
        bind(MetadataEditorEventBus.class).in(Singleton.class);

        bind(com.google.web.bindery.requestfactory.shared.RequestFactory.class).to(MetadataEditorRequestFactory.class);

        bind(com.google.gwt.place.shared.PlaceController.class).toProvider(PlaceControllerProvider.class).in(Singleton.class);

        bind(ActivityDisplayer.class).to(MetadataEditorAppDisplayer.class).in(Singleton.class);
        bind(MetadataEditorAppView.class).in(Singleton.class);
    }

    /**
     * This method instantiate and return for MetadataEditorRequestFactory
     *
     * @return Provider for MetadataEditorRequestFactory
     */
    @Provides
    MetadataEditorRequestFactory provideMetadataEditorRequestFactory() {
        MetadataEditorRequestFactory requestFactory = GWT.create(MetadataEditorRequestFactory.class);
        requestFactory.initialize(MetadataEditorGinInjector.INSTANCE.getEventBus());
        return requestFactory;
    }

    /** Provider for PlaceController */
    public static class PlaceControllerProvider implements Provider<PlaceController> {
        @Inject
        MetadataEditorEventBus eventBus;
        private PlaceController controller;

        @SuppressWarnings("deprecation")
        @Override
        public PlaceController get() {
            if (controller == null) {
                controller = new PlaceController(eventBus);
            }
            return controller;
        }
    }
}
