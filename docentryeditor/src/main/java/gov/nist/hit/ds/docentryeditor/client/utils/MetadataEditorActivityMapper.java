package gov.nist.hit.ds.docentryeditor.client.utils;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import gov.nist.hit.ds.docentryeditor.client.editor.association.AssociationEditorActivity;
import gov.nist.hit.ds.docentryeditor.client.editor.association.AssociationEditorPlace;
import gov.nist.hit.ds.docentryeditor.client.editor.docentry.DocEntryEditorPlace;
import gov.nist.hit.ds.docentryeditor.client.editor.subset.SubmissionSetEditorPlace;
import gov.nist.hit.ds.docentryeditor.client.root.home.WelcomePlace;

import javax.inject.Inject;

/**
 * Finds the activity to run for a given Place, used to configure an ActivityManager.
 * It binds the Places with the right Activities.
 *
 * @see com.google.gwt.activity.shared.ActivityManager
 */
public class MetadataEditorActivityMapper implements ActivityMapper {

    private static final MetadataEditorGinInjector INJECTOR = MetadataEditorGinInjector.INSTANCE;

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
            activity = INJECTOR.getWelcomeActivity();
        } else if (place instanceof SubmissionSetEditorPlace) {
            activity = INJECTOR.getSubmissionSetEditorActivity();
        } else if (place instanceof DocEntryEditorPlace) {
            activity = INJECTOR.getDocEntryEditorActivity();
        } else if(place instanceof AssociationEditorPlace){
            activity = INJECTOR.getAssociationEditorActivity();
        }
        return activity;
    }

}
