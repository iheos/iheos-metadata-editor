package gov.nist.hit.ds.docentryeditor.client.generics.abstracts;

import com.google.web.bindery.event.shared.EventBus;

import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * Generic class that handles the Presenter of the MVP design.
 * Used to build a Presenter for a given view.
 *
 * @param <V> Class that handles the View binded to this presenter
 */
public abstract class AbstractPresenter<V extends AbstractView<?>> {
    // Logger
    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    // EventBus to catch and fire events
    @Inject
    protected EventBus eventBus;

    // Variable that handles the instance of the view binded to this presenter
    protected V view;

    /**
     * Default "constructor"
     */
    @Inject
    public AbstractPresenter() {
    }

    /**
     * Constructor.
     * @param eventBus Application event bus.
     */
    @Inject
    public AbstractPresenter(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    /**
     * Abstract method called by mvp before view.init() does not necessarily need to do something.
     */
    public abstract void init();

    /**
     * Abstract method called by mvp just before view.getDisplay()
     */
    public void start() {
    }

    /**
     * Getter that returns the view object binded to this presenter.
     * @return View
     */
    public V getView() {
        return view;
    }

    /**
     * Setter to change the view object binded to this presenter.
     * @param view
     */
    public void setView(V view) {
        this.view = view;
    }

    /**
     * Getter that return the event bus of the presenter.
     * @return Event bus of the Presenter (same than the one of the Application)
     */
    public EventBus getEventBus() {
        return eventBus;
    }

    /**
     * Method that sets the event bus of the presenter (should be the same than the one of the Application)
     * @param eventBus
     */
    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

}
