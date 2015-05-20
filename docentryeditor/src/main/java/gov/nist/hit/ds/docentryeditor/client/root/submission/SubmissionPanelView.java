package gov.nist.hit.ds.docentryeditor.client.root.submission;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.text.shared.AbstractSafeHtmlRenderer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.SimpleSafeHtmlCell;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.*;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.sencha.gxt.widget.core.client.tree.Tree;
import gov.nist.hit.ds.docentryeditor.client.editor.association.AssociationEditorPlace;
import gov.nist.hit.ds.docentryeditor.client.editor.properties.XdsAssociationProperties;
import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractView;
import gov.nist.hit.ds.docentryeditor.client.resources.AppImages;
import gov.nist.hit.ds.docentryeditor.client.resources.ToolTipResources;
import gov.nist.hit.ds.docentryeditor.client.widgets.uploader.FileUploadDialog;
import gov.nist.hit.ds.docentryeditor.shared.model.*;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by onh2 on 7/11/2014.
 */
public class SubmissionPanelView extends AbstractView<SubmissionPanelPresenter> {
    private static final int RIGHT_N_LEFT_LBL_MARGIN = 5;
    private static final int UP_N_DOWN_LBL_MARGIN = 10;
    private static final int FILTER_LABEL_WIDTH = 50;

    // ~ Tree structure for submission set, folder and document entries
    private final TreeStore<SubmissionMenuData> submissionTreeStore = new TreeStore<SubmissionMenuData>(SubmissionMenuData.PROPS.key());
    private  Tree<SubmissionMenuData, SubmissionMenuData> submissionTree;
    private final ToolBar toolbar = new ToolBar();

    private final TextButton uploadFileButton = new TextButton();
    private final TextButton removeDocEntryButton = new TextButton();
    private final TextButton clearDocEntriesButton = new TextButton();
    private final TextButton saveDocEntriesButton = new TextButton();
    private final TextButton submissionHelpButton = new TextButton();

    private final MenuItem addEmptyDocEntry = new MenuItem("Create an empty document entry");
    private final MenuItem addPrefilledDocEntry = new MenuItem("Create a pre-filled document entry");

    // ~ List of Associations
    private ListView<XdsAssociation,String>  associationListView;
    private ListStore<XdsAssociation> associationListStore;
    private ToolBar associationPanelToolbar=new ToolBar();
    private final TextButton addAssociationButton = new TextButton();
    private final TextButton removeAssociationButton = new TextButton();
    private final TextButton clearAssociationsButton = new TextButton();
    private final TextButton associationHelpButton = new TextButton();

    @Inject
    private FileUploadDialog fileUploadDialog;
    private SimpleComboBox<String> filterComboBox;

    @Override
    protected Map<String, Widget> getPathToWidgetsMap() {
        return new HashMap<String, Widget>();
    }

    @Override
    protected Widget buildUI() {
        ContentPanel cp = new ContentPanel();
        cp.setHeadingText("Submission");
        cp.setHeaderVisible(true);
        cp.setBorders(false);

        submissionTree = new Tree<SubmissionMenuData, SubmissionMenuData>(submissionTreeStore, new SubmissionMenuDataProperties.SubmissionValueProvider());
        submissionTree.setCell(new SubmissionTreeCellRenderer());

        VerticalLayoutContainer vlc = new VerticalLayoutContainer();

        uploadFileButton.setIcon(AppImages.INSTANCE.loadFile12px());
        uploadFileButton.setToolTip(ToolTipResources.INSTANCE.getUploadFileTooltip());

        TextButton addDocEntryButton = new TextButton();
        addDocEntryButton.setIcon(AppImages.INSTANCE.add());
        addDocEntryButton.setToolTip("Create a new Document Entry.");

        addEmptyDocEntry.setToolTip(ToolTipResources.INSTANCE.getNewDocEntryTooltip());
        addPrefilledDocEntry.setToolTip(ToolTipResources.INSTANCE.getNewPrefilledDocEntryTooltip());

        Menu addDocEntryMenu = new Menu();
        addDocEntryMenu.add(addEmptyDocEntry);
        addDocEntryMenu.add(addPrefilledDocEntry);
        addDocEntryButton.setMenu(addDocEntryMenu);
        removeDocEntryButton.setIcon(AppImages.INSTANCE.delete());
        removeDocEntryButton.setToolTip(ToolTipResources.INSTANCE.getGridDeleteToolTip().split("(s)\\.")[0]);
        clearDocEntriesButton.setIcon(AppImages.INSTANCE.clear());
        clearDocEntriesButton.setToolTip(ToolTipResources.INSTANCE.getClearSubmissionSetToolTip());
        saveDocEntriesButton.setIcon(AppImages.INSTANCE.save());
        saveDocEntriesButton.setToolTip(ToolTipResources.INSTANCE.getSaveButtonToolTip());
        submissionHelpButton.setIcon(AppImages.INSTANCE.help());
        submissionHelpButton.setToolTip(ToolTipResources.INSTANCE.getHelpButtonToolTip());
        toolbar.add(uploadFileButton);
        toolbar.add(addDocEntryButton);
        toolbar.add(removeDocEntryButton);
        toolbar.add(clearDocEntriesButton);
        toolbar.add(saveDocEntriesButton);
        toolbar.add(submissionHelpButton);
        vlc.add(toolbar);
        getPresenter().initSubmissionSet();
        submissionTree.setIconProvider(new XdsSubmissionTreeNodeIconProvider());
        submissionTree.expandAll();
        submissionTree.setAutoExpand(true);

        // Association
        associationListStore=new ListStore<XdsAssociation>(XdsAssociationProperties.PROPS.key());
        associationListView=new ListView<XdsAssociation,String>(associationListStore,XdsAssociationProperties.PROPS.id());
        associationListView.setBorders(false);
        associationListView.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);
        associationListView.setCell(new SimpleSafeHtmlCell<String>(new AssociationListItemRenderer()));

        ContentPanel associationPanel = new ContentPanel();
        associationPanel.setHeadingText("Associations");
        associationPanel.setBorders(false);
        associationPanel.setBodyBorder(false);
        associationPanel.setCollapsible(false);

        addAssociationButton.setIcon(AppImages.INSTANCE.add());
        addAssociationButton.setToolTip(ToolTipResources.INSTANCE.getGridAddToolTip());
        removeAssociationButton.setIcon(AppImages.INSTANCE.delete());
        removeAssociationButton.setToolTip(ToolTipResources.INSTANCE.getGridDeleteToolTip().split("(s)\\.")[0]);
        clearAssociationsButton.setIcon(AppImages.INSTANCE.clear());
        clearAssociationsButton.setToolTip(ToolTipResources.INSTANCE.getClearAssociationsListToolTip());
        associationHelpButton.setIcon(AppImages.INSTANCE.help());
        associationHelpButton.setToolTip(ToolTipResources.INSTANCE.getHelpButtonToolTip());
        associationPanelToolbar.add(addAssociationButton);
        associationPanelToolbar.add(removeAssociationButton);
        associationPanelToolbar.add(clearAssociationsButton);
        associationPanelToolbar.add(associationHelpButton);

        filterComboBox=new SimpleComboBox<String>(new StringLabelProvider<String>());
        filterComboBox.add("All associations");
        filterComboBox.add("Internal asso. only");
        filterComboBox.add("External asso. only");
        filterComboBox.setMinChars(3);
        filterComboBox.setValue(filterComboBox.getStore().get(0));
        filterComboBox.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
        FieldLabel filterFL=new FieldLabel(filterComboBox,"Filter");
        filterFL.setLabelWidth(FILTER_LABEL_WIDTH);
        filterFL.addStyleName("topBorder");

        VerticalLayoutContainer associationWidgetsContainer=new VerticalLayoutContainer();
        associationWidgetsContainer.add(associationPanelToolbar, new VerticalLayoutContainer.VerticalLayoutData(-1, -1));
        associationWidgetsContainer.add(associationListView, new VerticalLayoutContainer.VerticalLayoutData(-1, 1));
        associationWidgetsContainer.add(filterFL, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(0, 5, 0, 5)));
        associationPanel.add(associationWidgetsContainer);

        ////////////// TODO REMOVE THIS BLOCK //////////////////////
        XdsAssociation asso=new XdsAssociation();
        asso.setId(new String256("Id2"));
        XdsAssociation asso2=new XdsAssociation();
        asso2.setId(new String256("Id3"));
        associationListStore.add(asso);
        associationListStore.add(asso2);
        ////////////////////////////////////////////////////////////

        vlc.add(submissionTree, new VerticalLayoutContainer.VerticalLayoutData(-1, 0.6));
        vlc.add(associationPanel, new VerticalLayoutContainer.VerticalLayoutData(-1, 0.4));

        cp.setWidget(vlc);

        return cp;
    }

    /**
     * This method ties the widgets with their actions in the view or with actions defined in the presenter.
     */
    @Override
    protected void bindUI() {
        bindSubmissionToolbar();
        bindAssociationToolbar();
        submissionTree.getSelectionModel().addSelectionHandler(new SelectionHandler<SubmissionMenuData>() {
            @Override
            public void onSelection(SelectionEvent<SubmissionMenuData> event) {
                presenter.loadSelectedTreeEntryEditor(event.getSelectedItem());
            }
        });
        associationListView.getSelectionModel().addSelectionHandler(new SelectionHandler<XdsAssociation>() {
            @Override
            public void onSelection(SelectionEvent<XdsAssociation> selectionEvent) {
                presenter.loadSelectedAssociationEditor(selectionEvent.getSelectedItem());
            }
        });
        filterComboBox.addSelectionHandler(new SelectionHandler<String>() {
            @Override
            public void onSelection(final SelectionEvent<String> event) {
                associationListStore.removeFilters();
                associationListStore.addFilter(new Store.StoreFilter<XdsAssociation>() {
                    @Override
                    public boolean select(Store<XdsAssociation> store, XdsAssociation parent, XdsAssociation item) {
                        switch (event.getSelectedItem()) {
                            case "Internal asso. only":
                                return item.getSubmissionSetStatus().equals(SubmissionSetStatus.ORIGINAL);
                            case "External asso. only":
                                return item.getSubmissionSetStatus().equals(SubmissionSetStatus.REFERENCED);
                            default:
                                return true;
                        }
                    }
                });
                associationListStore.setEnableFilters(true);
            }
        });
    }

    /**
     * This method ties the association toolbar buttons with their actions in the view or with actions defined in the presenter.
     */
    private void bindAssociationToolbar() {
        addAssociationButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                presenter.createNewAssociation();
            }
        });
        removeAssociationButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                associationListStore.remove(associationListView.getSelectionModel().getSelectedItem());
                if (presenter.getCurrentPlace() instanceof AssociationEditorPlace) {
                    presenter.goToHomePage();
                }
            }
        });
        clearAssociationsButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                presenter.clearAssociationStore();
                if (presenter.getCurrentPlace() instanceof AssociationEditorPlace) {
                    presenter.goToHomePage();
                }
            }
        });
    }

    /**
     * This method ties the submission panel toolbar buttons with their actions in the view or with actions defined in the presenter.
     */
    private void bindSubmissionToolbar() {
        addEmptyDocEntry.addSelectionHandler(new SelectionHandler<Item>() {
            @Override
            public void onSelection(SelectionEvent<Item> event) {
                presenter.createNewDocumentEntry();
            }
        });
        removeDocEntryButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                submissionTree.getStore().remove(submissionTree.getSelectionModel().getSelectedItem());
                submissionTree.getSelectionModel().select(submissionTree.getStore().getFirstChild(presenter.getSubmissionSetTreeNode()), false);
            }
        });
        clearDocEntriesButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                presenter.clearSubmissionSet();
                presenter.goToHomePage();
            }
        });
        addPrefilledDocEntry.addSelectionHandler(new SelectionHandler<Item>() {
            @Override
            public void onSelection(SelectionEvent<Item> itemSelectionEvent) {
                getPresenter().createPreFilledDocumentEntry();
            }
        });
        uploadFileButton.addSelectHandler(new SelectEvent.SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                fileUploadDialog.init();
                fileUploadDialog.show();
            }

        });
        saveDocEntriesButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent selectEvent) {
                presenter.doSave();
            }
        });
    }

    /**
     * This method open new browser window with the ebRim XML file
     * representing the XDS Metadata being saved.
     * @param fileNameOnServer name of the ebRim XML file of XDS Metadata.
     */
    public void openFileSavedPopup(String fileNameOnServer) {
        String fileURI=GWT.getHostPageBaseURL() + "files/" + fileNameOnServer;
        Window.open(fileURI, fileNameOnServer + " Metadata File", "enabled");
        Dialog d = new Dialog();
        HTMLPanel htmlP = new HTMLPanel("<a href='" + fileURI + "'>" + fileURI + "</a>");
        VerticalLayoutContainer vp = new VerticalLayoutContainer();
        vp.add(new Label("Your download is in progress, please allow this application to open popups with your browser..."),
                new VerticalLayoutContainer.VerticalLayoutData(1, 1, new Margins(UP_N_DOWN_LBL_MARGIN, RIGHT_N_LEFT_LBL_MARGIN, UP_N_DOWN_LBL_MARGIN, RIGHT_N_LEFT_LBL_MARGIN)));
        vp.add(htmlP, new VerticalLayoutContainer.VerticalLayoutData(1, 1, new Margins(UP_N_DOWN_LBL_MARGIN, RIGHT_N_LEFT_LBL_MARGIN, UP_N_DOWN_LBL_MARGIN, RIGHT_N_LEFT_LBL_MARGIN)));
        d.add(vp);
        d.setPredefinedButtons(Dialog.PredefinedButton.OK);
        d.setButtonAlign(BoxLayoutContainer.BoxLayoutPack.CENTER);
        d.setHideOnButtonClick(true);
        d.setHeadingText("XML Metadata File Download");
        d.show();
    }

    /**
     * This method returns the submission set Tree widget.
     *
     * @return submission set tree
     */
    public Tree<SubmissionMenuData, SubmissionMenuData> getSubmissionTree() {
        return submissionTree;
    }

    /**
     * This method returns the submission set TreeStore, which contains all the data.
     *
     * @return submission set tree store
     */
    public TreeStore<SubmissionMenuData> getSubmissionTreeStore() {
        return submissionTreeStore;
    }

    /**
     * This method returns the list widget, which contains all the associations involved
     * in the submission.
     *
     * @return list widget of associations
     */
    public ListView<XdsAssociation, String> getAssociationList() {
        return associationListView;
    }

    /**
     * This method returns the association list store, which contains all the association data.
     *
     * @return association list store
     */
    public ListStore<XdsAssociation> getAssociationStore() {
        return associationListStore;
    }

    /**
     * This class is an icon provider for the Xds Submission Tree.
     * It provides a different icon to be displayed before each object in the tree
     * regarding the type of this object.
     */
    public class XdsSubmissionTreeNodeIconProvider implements IconProvider<SubmissionMenuData> {

        /**
         * Returns the icon for the given model.
         *
         * @param model the target model
         * @return the icon
         */
        @Override
        public ImageResource getIcon(SubmissionMenuData model) {
            if (model.getModel() instanceof XdsDocumentEntry){
                return AppImages.INSTANCE.file();
            }
            if (model.getModel() instanceof XdsSubmissionSet){
                return AppImages.INSTANCE.subset();
            }
            return submissionTree.getStyle().getNodeOpenIcon();
        }
    }

    /**
     * HTML Renderer for each cell of the association list widget.
     * It adds an icon before the "id" of the association.
     */
    public class AssociationListItemRenderer extends AbstractSafeHtmlRenderer<String> {
        @Override
        public SafeHtml render(final String s) {
            return new SafeHtml() {
                @Override
                public String asString() {
                    return "<div style=\"margin-left:5px;\"><img style=\"margin-right:5px;\" src=\"" + AppImages.INSTANCE.association().getSafeUri().asString() + "\"/>" + s + "</div>";
                }
            };
        }
    }
}
