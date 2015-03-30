package gov.nist.hit.ds.docentryeditor.client.editor.widgets.AuthorWidgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.client.editor.ListStoreEditor;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldSet;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import gov.nist.hit.ds.docentryeditor.client.editor.EditionMode;
import gov.nist.hit.ds.docentryeditor.client.editor.properties.AuthorProperties;
import gov.nist.hit.ds.docentryeditor.client.widgets.ConfirmDeleteDialog;
import gov.nist.hit.ds.docentryeditor.shared.model.Author;

/**
 * <p>
 * <b>This class represents the widget which can edit a list of Authors</b>
 * It uses the AuthorEditorWidget to edit an entry of the list.
 * </p>
 *
 * @see gov.nist.hit.ds.docentryeditor.shared.model.Author
 * @see gov.nist.hit.ds.docentryeditor.client.editor.EditionMode
 * @see gov.nist.hit.ds.docentryeditor.client.editor.widgets.AuthorWidgets.AuthorEditorWidget
 */
public class AuthorsListEditorWidget extends ListStoreEditor<Author> implements IsWidget {
    // instance of the property access for the Author entity attributes (for the GXT Store).
    @Ignore
    private static final AuthorProperties authorprops = GWT.create(AuthorProperties.class);
    // Widget's container.
    @Ignore
    private SimpleContainer authorGridEditorWidget;

    // Widget that handles the list of Authors.
    @Ignore
    ListView<Author, String> listViewAuthors;

    // ///////////////////////////////////////////////////////////////////////
    // ---- AUTHOR WIDGETS
    // ///////////////////////////////////////////////////////////////////////
    // Widget to edit a selected entry of the list of Authors.
    @Ignore
    AuthorEditorWidget author;
    // Buttons to handle actions on the list of authors.
    @Ignore
    TextButton newAuthorWidget = new TextButton("New entry");
    @Ignore
    TextButton editAuthorWidget = new TextButton("Edit entry");
    @Ignore
    TextButton saveAuthorWidget = new TextButton("Save entry");
    @Ignore
    TextButton cancelAuthorWidget = new TextButton("Cancel");
    @Ignore
    TextButton deleteAuthorWidget = new TextButton("Delete entry");

    /**
     * Widget's default constuctor.
     */
    public AuthorsListEditorWidget() {
        super(new ListStore<Author>(authorprops.key()));
        buildUI();
        bindUI();
    }

    /**
     * Method to build an authors editor widget with author sub-editor
     *
     * @return authors editor widget w/ sub-editor
     */
    private void buildUI() {
        listViewAuthors = new ListView<Author, String>(this.getStore(), authorprops.authorPerson());
        // set the list selection mode to one single entry selected at a time.
        listViewAuthors.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);

        FieldSet authorFS = new FieldSet();
        authorFS.setHeadingText("Author Entry");
        author=new AuthorEditorWidget();
        authorFS.add(author);
        author.setEditionMode(EditionMode.NODATA);

        ContentPanel authorCP = new ContentPanel();
        authorCP.setHeaderVisible(false);
        authorCP.setBorders(false);
        authorCP.setBodyBorder(false);
        enableAuthorButtonWidgets(EditionMode.NODATA);
        authorCP.addButton(newAuthorWidget);
        authorCP.addButton(editAuthorWidget);
        authorCP.addButton(saveAuthorWidget);
        authorCP.addButton(cancelAuthorWidget);
        authorCP.addButton(deleteAuthorWidget);
        authorCP.setButtonAlign(BoxLayoutContainer.BoxLayoutPack.END);

        VerticalLayoutContainer vcon = new VerticalLayoutContainer();
        vcon.add(listViewAuthors, new VerticalLayoutContainer.VerticalLayoutData(1, 150));
        vcon.add(authorFS, new VerticalLayoutContainer.VerticalLayoutData(0.999, -1, new Margins(10, 0, 0, 0)));
        vcon.add(authorCP, new VerticalLayoutContainer.VerticalLayoutData(-1, 30));
        authorGridEditorWidget = new SimpleContainer();
        authorGridEditorWidget.setHeight(485);
        authorGridEditorWidget.add(vcon);
    }

    /**
     * Method that binds the widgets composing this AuthorsListEditorWidget
     * to their action handlers.
     */
    private void bindUI() {
        // Author entry select handler for the list of authors.
        listViewAuthors.getSelectionModel().addSelectionChangedHandler(new SelectionChangedEvent.SelectionChangedHandler<Author>() {

            @Override
            public void onSelectionChanged(SelectionChangedEvent<Author> event) {
                if (listViewAuthors.getSelectionModel().getSelectedItem() != null) {
                    // set the widget into display mode (editable fields disabled).
                    author.setEditionMode(EditionMode.DISPLAY);
                    // set the object (selected entry in the list) that must be handled.
                    // by the AuthorEditorWidget
                    author.edit(listViewAuthors.getSelectionModel().getSelectedItem());
                    // set the widget into edition mode (editable fields enabled).
                    author.setEditionMode(EditionMode.EDIT);
                    // set the list action buttons in edition mode (edit button disabled).
                    enableAuthorButtonWidgets(EditionMode.EDIT);
                } else {
                    // set the widget into NODATA selected mode (editable fields disabled).
                    author.setEditionMode(EditionMode.NODATA);
                    // set the list action buttons in NODATA selected mode (Only the new button is enabled).
                    enableAuthorButtonWidgets(EditionMode.NODATA);
                }
            }
        });
        // action handler for click on new entry button.
        newAuthorWidget.addSelectHandler(new SelectEvent.SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                // start the edition of a new Author instance.
                editNewAuthor();
            }
        });
        // action handler for the click on the edit button.
        editAuthorWidget.addSelectHandler(new SelectEvent.SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                // set the widget into edition mode (editable fields enabled).
                author.setEditionMode(EditionMode.EDIT);
                // set the list action buttons in edition mode (edit button disabled).
                enableAuthorButtonWidgets(EditionMode.EDIT);
            }
        });
        // action handler for the click on the cancel button during edition.
        cancelAuthorWidget.addSelectHandler(new SelectEvent.SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                // set the list action buttons in NODATA selected mode (Only the new button is enabled).
                author.setEditionMode(EditionMode.NODATA);
                // set the list action buttons in NODATA selected mode (Only the new button is enabled).
                enableAuthorButtonWidgets(EditionMode.NODATA);
                // deselect the entity in edition (deselect all)
                listViewAuthors.getSelectionModel().deselectAll();
                // set a new instance of author to the widget to empty the fields.
                author.editNew();
            }
        });
        // action handler for the click on the delete button during edition. (delete select author entry)
        deleteAuthorWidget.addSelectHandler(new SelectEvent.SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                // Show a confirmation dialog to confirm the object deletion.
                final ConfirmDeleteDialog cdd = new ConfirmDeleteDialog(listViewAuthors.getSelectionModel().getSelectedItem().getAuthorPerson()
                        .getString());
                cdd.show();
                cdd.addDialogHideHandler(new DialogHideEvent.DialogHideHandler() {
                    @Override
                    public void onDialogHide(DialogHideEvent event) {
                        // If YES button is click then deletion is performed.
                        if (event.getHideButton() == Dialog.PredefinedButton.YES) {
                            getStore().remove(listViewAuthors.getSelectionModel().getSelectedItem());
                            author.editNew();
                        }
                    }
                });
            }
        });
        // action handler for the click on the save button.
        saveAuthorWidget.addSelectHandler(new SelectEvent.SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                // save the author entry in edition.
                author.save();
                // If the author entry edition widget is in NEW edition mode,
                if (author.getEditionMode().equals(EditionMode.NEW)) {
                    // we check if the author created already exists in the list.
                    boolean isAlreadyThere = false;
                    for (Author a : getStore().getAll()) {
                        if (a.getAuthorPerson().getString().equals(author.getModel().getAuthorPerson().getString())) {
                            isAlreadyThere = true;
                            break;
                        }
                    }
                    // if it does exists
                    if (isAlreadyThere) {
                        Info.display("Impossible to add this value",
                                "This author person already exists for this document. You can not add him again.");
                    // otherwise
                    } else if (author.hasErrors()) {
                        Info.display("Impossible to add this value", "There still are errors in the editor.");
                    } else {
                        // add the new entity to the store
                        getStore().add(author.getModel());
                        // set a new instance of Author to the author entity edition widget to clear all fields.
                        author.editNew();
                        // deselect all authors
                        listViewAuthors.getSelectionModel().deselectAll();
                        // set the list action buttons in NODATA selected mode (Only the new button is enabled).
                        author.setEditionMode(EditionMode.NODATA);
                        // set the list action buttons in NODATA selected mode (Only the new button is enabled).
                        enableAuthorButtonWidgets(EditionMode.NODATA);
                    }
                // if the edition mode is in EDITion
                } else if (author.getEditionMode().equals(EditionMode.EDIT)) {
                    // update the selected entity in the store
                    getStore().update(author.getModel());
                    // set the list action buttons in NODATA selected mode (Only the new button is enabled).
                    author.setEditionMode(EditionMode.NODATA);
                    // set the list action buttons in NODATA selected mode (Only the new button is enabled).
                    enableAuthorButtonWidgets(EditionMode.NODATA);
                }
            }
        });
    }

    /**
     * Method that enables/disables the author list action buttons regarding a EditionMode
     *
     * @see gov.nist.hit.ds.docentryeditor.client.editor.EditionMode
     * @param editionMode
     */
    private void enableAuthorButtonWidgets(EditionMode editionMode) {
        if (editionMode.equals(EditionMode.NODATA) || editionMode.equals(EditionMode.DISPLAY)) {
            newAuthorWidget.setEnabled(true);
            saveAuthorWidget.setEnabled(false);
            cancelAuthorWidget.setEnabled(false);
        }
        if (editionMode.equals(EditionMode.NODATA)) {
            editAuthorWidget.setEnabled(false);
            deleteAuthorWidget.setEnabled(false);
        }
        if (editionMode.equals(EditionMode.DISPLAY)) {
            deleteAuthorWidget.setEnabled(true);
            editAuthorWidget.setEnabled(true);
        }
        if (editionMode.equals(EditionMode.NEW) || editionMode.equals(EditionMode.EDIT)) {
            editAuthorWidget.setEnabled(false);
            saveAuthorWidget.setEnabled(true);
            cancelAuthorWidget.setEnabled(true);
            deleteAuthorWidget.setEnabled(true);
        }
        if (editionMode.equals(EditionMode.NEW)) {
            newAuthorWidget.setEnabled(false);
        }
        if (editionMode.equals(EditionMode.EDIT)) {
            newAuthorWidget.setEnabled(true);
        }
    }

    /**
     * Method that start the edition of a new instance of Author.
     */
    public void editNewAuthor() {
        listViewAuthors.getSelectionModel().deselectAll();
        author.setEditionMode(EditionMode.NEW);
        author.editNew();
        enableAuthorButtonWidgets(EditionMode.NEW);
    }

    /**
     * Method that returns the AuthorsListEditor as a Widget.
     * @return AuthorsListEditorWidget
     */
    @Override
    public Widget asWidget() {
        return authorGridEditorWidget;
    }

    /**
     * Getter that returns the list widget.
     * @return list of authors widget
     */
    @Ignore
    public ListView<Author, String> getListView(){
        return listViewAuthors;
    }

    /**
     * Getter that returns the author entity editor widget.
     * @see gov.nist.hit.ds.docentryeditor.client.editor.widgets.AuthorWidgets.AuthorEditorWidget
     * @return authorEditorWidget - Widget that edits a selected author entity.
     */
    @Ignore
    public AuthorEditorWidget getAuthorWidget(){
        return author;
    }
}
