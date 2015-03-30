package gov.nist.hit.ds.docentryeditor.client.utils;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import gov.nist.hit.ds.docentryeditor.client.editor.documentEntryEditor.DocEntryEditorPlace;
import gov.nist.hit.ds.docentryeditor.client.editor.submissionSetEditor.SubmissionSetEditorPlace;
import gov.nist.hit.ds.docentryeditor.client.home.WelcomePlace;

import javax.inject.Inject;

/**
 * Finds the activity to run for a given Place, used to configure an ActivityManager.
 * It binds the Places with the right Activities.
 *
 * @see com.google.gwt.activity.shared.ActivityManager
 */
public class MetadataEditorActivityMapper implements ActivityMapper {

    MetadataEditorGinInjector injector = MetadataEditorGinInjector.instance;

    @Inject
    public MetadataEditorActivityMapper() {
        super();
    }

    /**
     * This method is supposed to return the right Activity for a given Place to load.
     *
     * @param place Place to load
     * @return the right Activity for a given place to load.
     */
    @Override
    public Activity getActivity(Place place) {
        AbstractActivity activity = null;

        // WELCOME
        if (place instanceof WelcomePlace) {
            activity = injector.getWelcomeActivity();
        } else if (place instanceof SubmissionSetEditorPlace) {
            activity = injector.getSubmissionSetEditorActivity();
        } else if (place instanceof DocEntryEditorPlace) {
            activity = injector.getDocEntryEditorActivity();
        }
        return activity;
    }

}
