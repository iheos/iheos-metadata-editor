package gov.nist.hit.ds.docentryeditor.client.editor.widgets.AuthorWidgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.client.editor.ListStoreEditor;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.validator.AbstractValidator;
import com.sencha.gxt.widget.core.client.form.validator.RegExValidator;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;
import gov.nist.hit.ds.docentryeditor.client.editor.EditionMode;
import gov.nist.hit.ds.docentryeditor.client.editor.properties.String256Properties;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.String256EditorWidget;
import gov.nist.hit.ds.docentryeditor.client.widgets.ConfirmDeleteDialog;
import gov.nist.hit.ds.docentryeditor.client.widgets.FieldEmptyValidator;
import gov.nist.hit.ds.docentryeditor.shared.model.Author;
import gov.nist.hit.ds.docentryeditor.shared.model.String256;

import java.util.List;
import java.util.logging.Logger;

/**
 * <p>
 * <b>This class represents the widget which can edit the Author model type</b>
 * It does an automatic mapping between the Author class's attributes name and
 * the widget's fields name.
 * </p>
 *
 * @see Author
 * @see AuthorEditorDriver
 * @see EditionMode
 */
public class AuthorEditorWidget extends Composite implements Editor<Author> {
    // class logger
    private static Logger logger = Logger.getLogger(AuthorEditorWidget.class.getName());
    // instance of the driver for the edition of an author (used for the edition
    // the selected author in authors list)
    private final AuthorEditorDriver authorEditorDriver = GWT.create(AuthorEditorDriver.class);
    /*
     * field to input the authorPerson attribute it is directly mapped by its
     * name on "authorPerson" in Author's class.
     */
    String256EditorWidget authorPerson = new String256EditorWidget();
    // /////////////////////////////////
    // --- List of institutions
    // /////////////////////////////////
    /*
     * List store to handle the editor on "List<String> authorInstitutions" in
	 * Author's class. It is used in a listView widget and handle its content.
	 * It inputs the author model authorInsitutions attribute, it is directly
	 * mapped by its name on "authorInstitutions" in Author's class.
	 */
    ListStoreEditor<String256> authorInstitutions;
    @Ignore
    ListView<String256, String> listViewAuthInstitutions;
    // Sub editor for an authorInstitution
    @Ignore
    String256EditorWidget authorInstitution = new String256EditorWidget();

    // /*
    // * field to input the authorTelecommunication attribute it is directly
    // mapped by its
    // * name on "Telecommunication" in Author's class.
    // */
    // String256EditorWidget authorTelecommunication = new
    // String256EditorWidget();
    @Ignore
    TextButton addInstitutionButton = new TextButton("Add");
    @Ignore
    TextButton deleteInstitutionButton = new TextButton("Delete entry");
    // /////////////////////////////////
    // --- List of Roles
    // /////////////////////////////////
    /*
     * List store to handle the editor on "List<String> authorRoles" in Author's
	 * class. It is used in a listView widget and handle its content. It inputs
	 * the author model authorRoles attribute, it is directly mapped by its name
	 * on "authorRoles" in Author's class.
	 */
    ListStoreEditor<String256> authorRoles;
    @Ignore
    ListView<String256, String> listViewAuthRoles;
    // Sub Editor for an authorRole
    @Ignore
    String256EditorWidget authorRole = new String256EditorWidget();
    @Ignore
    TextButton addRoleButton = new TextButton("Add");
    @Ignore
    TextButton deleteRoleButton = new TextButton("Delete entry");
    // /////////////////////////////////
    // --- List of Specialties
    // /////////////////////////////////
    /*
     * List store to handle the editor on "List<String> authorSpecialties" in
	 * Author's class. It is used in a listView widget and handle its content.
	 * It inputs the author mode authorSpecialties attribute, it is directly
	 * mapped by its name on "authorSpecialties" in Author's class.
	 */
    ListStoreEditor<String256> authorSpecialties;
    @Ignore
    ListView<String256, String> listViewAuthSpecialties;
    // Sub Editor for an authorSpecialty
    @Ignore
    String256EditorWidget authorSpecialty = new String256EditorWidget();
    @Ignore
    TextButton addSpecialtyButton = new TextButton("Add");
    @Ignore
    TextButton deleteSpecialtyButton = new TextButton("Delete entry");
    // /////////////////////////////////
    // --- List of telecommunications
    // /////////////////////////////////
    /*
     * List store to handle the editor on
	 * "List<String> authorTelecommunications" in Author's class. It is used in
	 * a listView widget and handle its content. It inputs the author model
	 * authorTelecommunications attribute, it is directly mapped by its name on
	 * "authorTelecommunications" in Author's class.
	 */
    ListStoreEditor<String256> authorTelecommunications;
    @Ignore
    ListView<String256, String> listViewAuthTelecommunications;
    // Sub editor for an authorInstitution
    @Ignore
    String256EditorWidget authorTelecommunication = new String256EditorWidget();
    @Ignore
    TextButton addTelecommunicationButton = new TextButton("Add");
    @Ignore
    TextButton deleteTelecommunicationButton = new TextButton("Delete entry");
    /*
     * editionMode is an instance of EditionMode. It is used to know the state
     * of the editor, to know how to display the different fields
     * (enable/disable especially)
     */
    @Ignore
    private EditionMode editionMode = EditionMode.DISPLAY;
    // the author object being edited
    @Ignore
    private Author model;

    /**
     * AuthorEditorWidget default constructor
     */
    public AuthorEditorWidget() {
        // Instance of String256 AccessProperties with value providers
        final String256Properties props = GWT.create(String256Properties.class);

        // Author INSTITUTIONS listWidget init
        listViewAuthInstitutions = new ListView<String256, String>(new ListStore<String256>(props.key()), props.string());
        listViewAuthInstitutions.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // init institutions store
        authorInstitutions = new ListStoreEditor<String256>(listViewAuthInstitutions.getStore());

        // Author ROLES listWidget init
        listViewAuthRoles = new ListView<String256, String>(new ListStore<String256>(props.key()), props.string());
        listViewAuthRoles.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // init roles store
        authorRoles = new ListStoreEditor<String256>(listViewAuthRoles.getStore());

        // Author SPECIALTIES listWidget init
        listViewAuthSpecialties = new ListView<String256, String>(new ListStore<String256>(props.key()), props.string());
        listViewAuthSpecialties.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // init specialties store
        authorSpecialties = new ListStoreEditor<String256>(listViewAuthSpecialties.getStore());

        // Author TLECOMMUNICATIONS listWidget init
        listViewAuthTelecommunications = new ListView<String256, String>(new ListStore<String256>(props.key()),
                props.string());
        listViewAuthTelecommunications.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // init telecommunications store
        authorTelecommunications = new ListStoreEditor<String256>(listViewAuthTelecommunications.getStore());

        // /////////////////////
        // --BuildUI
        // /////////////////////

        // containers declaration
        VerticalLayoutContainer vcontainer = new VerticalLayoutContainer();
        HorizontalLayoutContainer listsContainer = new HorizontalLayoutContainer();
        VerticalLayoutContainer authorInstitutionsContainer = new VerticalLayoutContainer();
        VerticalLayoutContainer authorRolesContainer = new VerticalLayoutContainer();
        VerticalLayoutContainer authorSpecialtiesContainer = new VerticalLayoutContainer();
        VerticalLayoutContainer authorTelecommunicationsContainer = new VerticalLayoutContainer();

        // Author Person field
        FieldLabel authorPersonLabel = new FieldLabel(authorPerson, "Author Person");
        authorPersonLabel.setLabelWidth(125);
        authorPerson.addValidator(new RegExValidator("^([A-Za-z]*[0-9]+\\^){0,1}[A-z]+(\\^{6}&[0-9](\\.[0-9])+&ISO$){0,1}"));
        authorPerson.addValidator(new FieldEmptyValidator("author person"));

        // ///////////////////////////////////////////////////
        // --- Author Institutions Widget field+listview
        // ///////////////////////////////////////////////////
        HorizontalLayoutContainer hcontainerInstitution = new HorizontalLayoutContainer();

        FieldLabel institutionLabel = new FieldLabel();
        institutionLabel.setText("Institution");
        authorInstitution
                .addValidator(new RegExValidator("[A-Za-z]+(\\s[A-Za-z]+)*" + "((\\^{9}[0-9]+(\\.[0-9]+)+)|"
                        + "(\\^{5}&[0-9]+(\\.[0-9]+)+&ISO\\^{4}[0-9]+)){0,1}",
                        "This value is incorrect for an author institution."));
        authorInstitution.setToolTipConfig(new ToolTipConfig("Institution format", "Examples:<br/>" + "Some Hospital<br/>"
                + "Some Hospital^^^^^^^^^1.2.3.4.5.6.7.8.9.1789.45<br/>"
                + "Some Hospital^^^^^&1.2.3.4.5.6.7.8.9.1789&ISO^^^^45"));

        hcontainerInstitution.add(authorInstitution, new HorizontalLayoutData(1, 30, new Margins(0, 10, 10, 0)));
        authorInstitution.setWidth("auto");
        addInstitutionButton.setWidth("auto");
        hcontainerInstitution.add(addInstitutionButton);
        hcontainerInstitution.setWidth("auto");

        deleteInstitutionButton.disable();

        authorInstitutionsContainer.add(institutionLabel, new VerticalLayoutData(1, -1));
        authorInstitutionsContainer.add(hcontainerInstitution, new VerticalLayoutData(1, 30));
        authorInstitutionsContainer.add(listViewAuthInstitutions, new VerticalLayoutData(1, 150));
        authorInstitutionsContainer.add(deleteInstitutionButton);

        // ///////////////////////////////////////////////////
        // --- Author Roles Widget field+listview
        // ///////////////////////////////////////////////////
        HorizontalLayoutContainer hcontainerRole = new HorizontalLayoutContainer();

        FieldLabel roleLabel = new FieldLabel();
        roleLabel.setText("Role");

        hcontainerRole.add(authorRole, new HorizontalLayoutData(1, 30, new Margins(0, 10, 10, 0)));
        authorRole.setWidth("auto");
        addRoleButton.setWidth("auto");
        hcontainerRole.add(addRoleButton);
        hcontainerRole.setWidth("auto");

        deleteRoleButton.disable();

        authorRolesContainer.add(roleLabel, new VerticalLayoutData(1, -1));
        authorRolesContainer.add(hcontainerRole, new VerticalLayoutData(1, 30));
        authorRolesContainer.add(listViewAuthRoles, new VerticalLayoutData(1, 150));
        authorRolesContainer.add(deleteRoleButton);

        // ///////////////////////////////////////////////////
        // --- Author Specialties Widget field+listview
        // ///////////////////////////////////////////////////
        HorizontalLayoutContainer hcontainerSpecialty = new HorizontalLayoutContainer();

        FieldLabel specialtyLabel = new FieldLabel();
        specialtyLabel.setText("Specialty");

        hcontainerSpecialty.add(authorSpecialty, new HorizontalLayoutData(1, 30, new Margins(0, 10, 10, 0)));
        authorSpecialty.setWidth("auto");
        addSpecialtyButton.setWidth("auto");
        hcontainerSpecialty.add(addSpecialtyButton);
        hcontainerSpecialty.setWidth("auto");

        deleteSpecialtyButton.disable();

        authorSpecialtiesContainer.add(specialtyLabel, new VerticalLayoutData(1, -1));
        authorSpecialtiesContainer.add(hcontainerSpecialty, new VerticalLayoutData(1, 30));
        authorSpecialtiesContainer.add(listViewAuthSpecialties, new VerticalLayoutData(1, 150));
        authorSpecialtiesContainer.add(deleteSpecialtyButton);

        // ///////////////////////////////////////////////////
        // --- Author Telecommunications Widget field+listview
        // ///////////////////////////////////////////////////
        HorizontalLayoutContainer hcontainerTelecommunication = new HorizontalLayoutContainer();

        FieldLabel telecommunicationLabel = new FieldLabel();
        telecommunicationLabel.setText("Telecommunication");

        hcontainerTelecommunication.add(authorTelecommunication, new HorizontalLayoutData(1, 30, new Margins(0, 10, 10, 0)));
        authorTelecommunication.setWidth("auto");
        authorTelecommunication.setToolTipConfig(new ToolTipConfig("Telecommunication represents the telecommunications address (e.g., email) of the document or SubmissionSet author."));
        addTelecommunicationButton.setWidth("auto");
        authorTelecommunication.addValidator(new AbstractValidator<String>() {
            @Override
            public List<EditorError> validate(Editor<String> editor, String value) {
                List<EditorError> errors = null;
                if (value == null)
                    value = authorTelecommunication.getField().getText();
                if (/* value == null || */!value.matches(".*@.*\\.[a-z]*$")) {
                    errors = createError(editor, "Value is not a valid telecommunication email.", value);
                }
                return errors;
            }
        });
        hcontainerTelecommunication.add(addTelecommunicationButton);
        hcontainerTelecommunication.setWidth("auto");

        deleteTelecommunicationButton.disable();

        authorTelecommunicationsContainer.add(telecommunicationLabel, new VerticalLayoutData(1, -1));
        authorTelecommunicationsContainer.add(hcontainerTelecommunication, new VerticalLayoutData(1, 30));
        authorTelecommunicationsContainer.add(listViewAuthTelecommunications, new VerticalLayoutData(1, 150));
        authorTelecommunicationsContainer.add(deleteTelecommunicationButton);

        // Add each widget to the main container
        vcontainer.add(authorPersonLabel, new VerticalLayoutData(1, -1));
        listsContainer.add(authorInstitutionsContainer, new HorizontalLayoutData(0.25, -1, new Margins(0, 10, 0, 0)));
        listsContainer.add(authorRolesContainer, new HorizontalLayoutData(0.25, -1, new Margins(0, 10, 0, 0)));
        listsContainer.add(authorSpecialtiesContainer, new HorizontalLayoutData(0.25, -1, new Margins(0, 10, 0, 0)));
        listsContainer.add(authorTelecommunicationsContainer, new HorizontalLayoutData(0.25, -1, new Margins()));
        vcontainer.add(listsContainer, new VerticalLayoutData(1, 230));

        // init namevaluestring256 with its widgets container
        initWidget(vcontainer);

        bindUI();
    }

    private void addAuthorInstitutionToListStore() {
        String s = authorInstitution.getField().getText();

        if (s != null && !s.isEmpty()) {
            logger.info("adding new value (" + s + ") to list store");
            String256 v = new String256().setString(s);
            if (!contains(authorInstitutions.getStore(), v)) {
                authorInstitutions.getStore().add(new String256().setString(s));
                authorInstitution.getField().clear();
                authorInstitution.getField().redraw();
            } else {
                Info.display("Impossible to add value", "It is impossible to add this value. It already is in the list.");
            }
        }
        authorInstitution.getField().focus();
    }

    private void addAuthorRoleToListStore() {
        String s = authorRole.getField().getText();
        if (s != null && !s.isEmpty()) {
            logger.info("adding new value (" + s + ") to list store");
            String256 v = new String256().setString(s);
            if (!contains(authorRoles.getStore(), v)) {
                authorRoles.getStore().add(new String256().setString(s));
                authorRole.getField().clear();
                authorRole.getField().redraw();
            } else {
                Info.display("Impossible to add value", "It is impossible to add this value. It already is in the list.");
            }
        }
        authorRole.getField().focus();
    }

    private void addAuthorSpecialtyToListStore() {
        String s = authorSpecialty.getField().getText();
        if (s != null && !s.isEmpty()) {
            logger.info("adding new value (" + s + ") to list store");
            String256 v = new String256().setString(s);
            if (!contains(authorSpecialties.getStore(), v)) {
                authorSpecialties.getStore().add(new String256().setString(s));
                authorSpecialty.getField().clear();
            } else {
                Info.display("Impossible to add value", "It is impossible to add this value. It already is in the list.");
            }
        }
        authorSpecialty.getField().focus();
    }

    private void addAuthorTelecommunicationToListStore() {
        String s = authorTelecommunication.getField().getText();

        if (authorTelecommunication.getField().validate())
            if (s != null && !s.isEmpty()) {
                logger.info("adding new value (" + s + ") to list store");
                String256 v = new String256().setString(s);
                if (!contains(authorTelecommunications.getStore(), v)) {
                    authorTelecommunications.getStore().add(new String256().setString(s));
                    authorTelecommunication.getField().clear();
                    authorTelecommunication.getField().clearInvalid();
                    authorTelecommunication.getField().redraw();
                } else {
                    Info.display("Impossible to add value", "It is impossible to add this value. It already is in the list.");
                }
            }
        authorTelecommunication.getField().focus();
    }

    /**
     * Method that binds the UI widget's actions.
     */
    private void bindUI() {
        // /////////////////////////////
        // --- List selection binding
        // /////////////////////////////
        // institution selection handler
        listViewAuthInstitutions.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<String256>() {
            @Override
            public void onSelectionChanged(SelectionChangedEvent<String256> event) {
                if (listViewAuthInstitutions.isEnabled()) {
                    if (listViewAuthInstitutions.getSelectionModel().getSelectedItem() != null) {
                        deleteInstitutionButton.enable();
                    } else {
                        deleteInstitutionButton.disable();
                    }
                }
            }
        });
        // role selection handler
        listViewAuthRoles.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<String256>() {
            @Override
            public void onSelectionChanged(SelectionChangedEvent<String256> event) {
                if (listViewAuthRoles.isEnabled()) {
                    if (listViewAuthRoles.getSelectionModel().getSelectedItem() != null) {
                        deleteRoleButton.enable();
                    } else {
                        deleteRoleButton.disable();
                    }
                }
            }
        });
        // specialty selection handler
        listViewAuthSpecialties.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<String256>() {
            @Override
            public void onSelectionChanged(SelectionChangedEvent<String256> event) {
                if (listViewAuthSpecialties.isEnabled()) {
                    if (listViewAuthSpecialties.getSelectionModel().getSelectedItem() != null) {
                        deleteSpecialtyButton.enable();
                    } else {
                        deleteSpecialtyButton.disable();
                    }
                }
            }
        });
        // telecommunication selection handler
        listViewAuthTelecommunications.getSelectionModel().addSelectionChangedHandler(
                new SelectionChangedHandler<String256>() {
                    @Override
                    public void onSelectionChanged(SelectionChangedEvent<String256> event) {
                        if (listViewAuthTelecommunications.isEnabled()) {
                            if (listViewAuthTelecommunications.getSelectionModel().getSelectedItem() != null) {
                                deleteTelecommunicationButton.enable();
                            } else {
                                deleteTelecommunicationButton.disable();
                            }
                        }
                    }
                });

        // ////////////////////////////////////////////////////
        // --- Add and delete value from lists handlers
        // ////////////////////////////////////////////////////

        // Add Institution value handlers
        addInstitutionButton.addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                addAuthorInstitutionToListStore();
            }
        });
        authorInstitution.getField().addKeyDownHandler(new KeyDownHandler() {

            @Override
            public void onKeyDown(KeyDownEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    logger.info("ENTER KEY PRESSED on Institution Field");
                    addAuthorInstitutionToListStore();
                }
            }
        });
        // Delete Institution Value handler
        deleteInstitutionButton.addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                final ConfirmDeleteDialog cdd = new ConfirmDeleteDialog(listViewAuthInstitutions.getSelectionModel()
                        .getSelectedItem().getString());
                cdd.show();
                cdd.addDialogHideHandler(new DialogHideEvent.DialogHideHandler() {

                    @Override
                    public void onDialogHide(DialogHideEvent event) {
                        if (event.getHideButton() == PredefinedButton.YES) {
                            // perform YES action
                            authorInstitutions.getStore().remove(
                                    listViewAuthInstitutions.getSelectionModel().getSelectedItem());
                        }
                    }
                });
            }

        });

        // Add Role value handlers
        addRoleButton.addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                addAuthorRoleToListStore();
            }
        });
        authorRole.getField().addKeyDownHandler(new KeyDownHandler() {

            @Override
            public void onKeyDown(KeyDownEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    logger.info("ENTER KEY PRESSED on Role Field");
                    addAuthorRoleToListStore();
                }
            }
        });
        // Delete Role Value handler
        deleteRoleButton.addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                final ConfirmDeleteDialog cdd = new ConfirmDeleteDialog(listViewAuthRoles.getSelectionModel()
                        .getSelectedItem().getString());
                cdd.show();
                cdd.addDialogHideHandler(new DialogHideEvent.DialogHideHandler() {

                    @Override
                    public void onDialogHide(DialogHideEvent event) {
                        if (event.getHideButton() == PredefinedButton.YES) {
                            // perform YES action
                            authorRoles.getStore().remove(listViewAuthRoles.getSelectionModel().getSelectedItem());
                        }
                    }
                });
            }

        });

        // Add Specialty value handlers
        addSpecialtyButton.addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                addAuthorSpecialtyToListStore();
            }
        });
        authorSpecialty.getField().addKeyDownHandler(new KeyDownHandler() {

            @Override
            public void onKeyDown(KeyDownEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    logger.info("ENTER KEY PRESSED on Specialty field");
                    addAuthorSpecialtyToListStore();
                }
            }
        });
        // Delete Institution Value handler
        deleteSpecialtyButton.addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                final ConfirmDeleteDialog cdd = new ConfirmDeleteDialog(listViewAuthSpecialties.getSelectionModel()
                        .getSelectedItem().getString());
                cdd.show();
                cdd.addDialogHideHandler(new DialogHideEvent.DialogHideHandler() {

                    @Override
                    public void onDialogHide(DialogHideEvent event) {
                        if (event.getHideButton() == PredefinedButton.YES) {
                            // perform YES action
                            authorSpecialties.getStore().remove(
                                    listViewAuthSpecialties.getSelectionModel().getSelectedItem());
                        }
                    }
                });
            }

        });
        // Add Telecommunication value handlers
        addTelecommunicationButton.addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                addAuthorTelecommunicationToListStore();
            }
        });
        authorTelecommunication.getField().addKeyDownHandler(new KeyDownHandler() {

            @Override
            public void onKeyDown(KeyDownEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    logger.info("ENTER KEY PRESSED on Telecommunication field");
                    addAuthorTelecommunicationToListStore();
                }
            }
        });
        // Delete Telecommunication Value handler
        deleteTelecommunicationButton.addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                final ConfirmDeleteDialog cdd = new ConfirmDeleteDialog(listViewAuthTelecommunications.getSelectionModel()
                        .getSelectedItem().getString());
                cdd.show();
                cdd.addDialogHideHandler(new DialogHideEvent.DialogHideHandler() {

                    @Override
                    public void onDialogHide(DialogHideEvent event) {
                        if (event.getHideButton() == PredefinedButton.YES) {
                            // perform YES action
                            authorTelecommunications.getStore().remove(
                                    listViewAuthTelecommunications.getSelectionModel().getSelectedItem());
                        }
                    }
                });
            }

        });
    }

    /**
     * Method to check if a list of String256 contains a specific value.
     *
     * @param list list of String256 values
     * @param str  value to look for in above list
     * @return
     */
    private boolean contains(ListStore<String256> list, String256 str) {
        for (String256 s : list.getAll()) {
            if (s.getString().equals(str.getString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method handle the edition of a given author. It fill the editor with
     * the author's details and put it in edition.
     *
     * @param author The Author which will be edited.
     */
    public void edit(Author author) {
        // authorEditorDriver.getErrors().clear();
        resetWidgets();
        setModel(author);
        authorEditorDriver.edit(author);
    }

    /**
     * Method to start the edition of a new Author.
     */
    public void editNew() {
        // authorEditorDriver.getErrors().clear();
        model = new Author();
        edit(model);
        resetWidgets();
    }

    /**
     * Method to check if errors remain in the author editor.
     *
     * @return hasErrors boolean
     */
    public boolean hasErrors() {
        return authorEditorDriver.hasErrors();
    }

    /**
     * This method init the author editor driver. It must be called when the
     * view is created.
     */
    public void initEditorDriver() {
        authorEditorDriver.initialize(this);
    }

    /**
     * This method clears the fields to add values in lists roles, institutions
     * and specialties.
     */
    private void resetWidgets() {
        authorTelecommunication.getField().clear();
        authorPerson.getField().clear();
        authorInstitution.getField().clear();
        authorRole.getField().clear();
        authorSpecialty.getField().clear();
    }

    /**
     * Method that saves the changes in the Editor GUI in editor's model object
     */
    public void save() {
        model = authorEditorDriver.flush();
        if (authorEditorDriver.hasErrors()) {
            Dialog d = new Dialog();
            d.setHeadingText("Errors dialog");
            d.setPredefinedButtons(PredefinedButton.YES, PredefinedButton.NO);
            d.setBodyStyleName("pad-text");
            d.add(new Label(authorEditorDriver.getErrors().toString()));
            d.getBody().addClassName("pad-text");
            d.setHideOnButtonClick(true);
            d.setWidth(300);
            d.show();
        }
    }

    /**
     * Convenience function for setting disabled/enabled by boolean.
     *
     * @param enabled the enabled state
     */
    private void setWidgetEnable(boolean enabled) {
        authorPerson.getField().setEnabled(enabled);
        authorInstitution.getField().setEnabled(enabled);
        listViewAuthInstitutions.setEnabled(enabled);
        authorRole.getField().setEnabled(enabled);
        listViewAuthRoles.setEnabled(enabled);
        authorSpecialty.getField().setEnabled(enabled);
        listViewAuthSpecialties.setEnabled(enabled);
        authorTelecommunication.getField().setEnabled(enabled);
        listViewAuthTelecommunications.setEnabled(enabled);
        addInstitutionButton.setEnabled(enabled);
        addRoleButton.setEnabled(enabled);
        addSpecialtyButton.setEnabled(enabled);
        addTelecommunicationButton.setEnabled(enabled);
        if (listViewAuthSpecialties.getSelectionModel().getSelectedItem() != null
                || listViewAuthSpecialties.getSelectionModel().getSelectedItems().size() > 0) {
            deleteSpecialtyButton.setEnabled(enabled);
        }
        if (listViewAuthRoles.getSelectionModel().getSelectedItem() != null
                || listViewAuthRoles.getSelectionModel().getSelectedItems().size() > 0) {
            deleteRoleButton.setEnabled(enabled);
        }
        if (listViewAuthInstitutions.getSelectionModel().getSelectedItem() != null
                || listViewAuthInstitutions.getSelectionModel().getSelectedItems().size() > 0) {
            deleteInstitutionButton.setEnabled(enabled);
        }
        if (listViewAuthTelecommunications.getSelectionModel().getSelectedItem() != null
                || listViewAuthTelecommunications.getSelectionModel().getSelectedItems().size() > 0) {
            deleteTelecommunicationButton.setEnabled(enabled);
        }
    }

    /**
     * @return Current editionMode (state of the editor)
     * @see EditionMode
     */
    public EditionMode getEditionMode() {
        return editionMode;
    }

    /**
     * Method to change the editor layout regarding an edition Mode. It actually
     * enable/disable the various widgets of the editor regarding the state of
     * edition.
     *
     * @param editionMode EditionMode (NODATA,DISPLAY,NEW,EDIT)
     * @see EditionMode
     */
    public void setEditionMode(EditionMode editionMode) {
        this.editionMode = editionMode;
        if (editionMode.equals(EditionMode.DISPLAY) || editionMode.equals(EditionMode.NODATA)) {
            setWidgetEnable(false);
        } else if (editionMode.equals(EditionMode.NEW) || editionMode.equals(EditionMode.EDIT)) {
            setWidgetEnable(true);
        }
    }

    /**
     * Method to get the edited author. Should be called after save().
     *
     * @return edited Author
     */
    public Author getModel() {
        return model;
    }

    /**
     * . Simple setter method for AuthorEditorWidget's model>
     *
     * @param model author object to be edited.
     */
    private void setModel(Author model) {
        this.model = model;
    }

    /**
     * The AuthorEditorDriver is the editr driver interface which handle the
     * edition of an Author through AuthorEditorWidget.
     *
     * @see Author
     * @see AuthorEditorWidget
     */
    interface AuthorEditorDriver extends SimpleBeanEditorDriver<Author, AuthorEditorWidget> {
    }

}
