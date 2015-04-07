package gov.nist.hit.ds.docentryeditor.client.root.submission;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.sencha.gxt.widget.core.client.tree.Tree;
import gov.nist.hit.ds.docentryeditor.client.event.MetadataEditorEventBus;
import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractView;
import gov.nist.hit.ds.docentryeditor.client.resources.AppImages;
import gov.nist.hit.ds.docentryeditor.client.resources.ToolTipResources;
import gov.nist.hit.ds.docentryeditor.client.widgets.uploader.FileUploadDialog;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by onh2 on 7/11/2014.
 */
public class SubmissionPanelView extends AbstractView<SubmissionPanelPresenter> {
    private final TreeStore<SubmissionMenuData> treeStore = new TreeStore<SubmissionMenuData>(SubmissionMenuData.PROPS.key());
    private final Tree<SubmissionMenuData, String> tree = new Tree<SubmissionMenuData, String>(treeStore, SubmissionMenuData.PROPS.value());
    private final ToolBar toolbar = new ToolBar();

    private final TextButton uploadFileButton = new TextButton();
    private final TextButton removeDocEntryButton = new TextButton();
    private final TextButton clearDocEntriesButton = new TextButton();
    private final TextButton saveDocEntriesButton = new TextButton();
    private final TextButton helpButton = new TextButton();

    private final MenuItem addEmptyDocEntry = new MenuItem("Create an empty document entry");
    private final MenuItem addPrefilledDocEntry = new MenuItem("Create a pre-filled document entry");

    @Inject
    private MetadataEditorEventBus eventBus;
    @Inject
    private FileUploadDialog fileUploadDialog;

    @Override
    protected Map<String, Widget> getPathToWidgetsMap() {
        Map<String, Widget> map = new HashMap<String, Widget>();
        return map;
    }

    @Override
    protected Widget buildUI() {
        ContentPanel cp = new ContentPanel();
        cp.setHeadingText("Submission");
        cp.setHeaderVisible(true);
        cp.setBorders(false);

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
        helpButton.setIcon(AppImages.INSTANCE.help());
        helpButton.setToolTip(ToolTipResources.INSTANCE.getHelpButtonToolTip());
        toolbar.add(uploadFileButton);
        toolbar.add(addDocEntryButton);
        toolbar.add(removeDocEntryButton);
        toolbar.add(clearDocEntriesButton);
        toolbar.add(saveDocEntriesButton);
        toolbar.add(helpButton);
        vlc.add(toolbar);
        getPresenter().initSubmissionSet();
        tree.getStyle().setLeafIcon(AppImages.INSTANCE.file());
        tree.expandAll();
        tree.setAutoExpand(true);
        vlc.add(tree);

        cp.setWidget(vlc);

        return cp;
    }

    /**
     * This method ties the widgets with their actions in the view or with actions defined in the presenter.
     */
    @Override
    protected void bindUI() {
        addEmptyDocEntry.addSelectionHandler(new SelectionHandler<Item>() {
            @Override
            public void onSelection(SelectionEvent<Item> event) {
                presenter.createNewDocumentEntry();
            }
        });
        removeDocEntryButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                tree.getStore().remove(tree.getSelectionModel().getSelectedItem());
                tree.getSelectionModel().select(tree.getStore().getFirstChild(presenter.getSubmissionSetTreeNode()), false);
            }
        });
        clearDocEntriesButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                presenter.clearSubmissionSet();
                presenter.goToHomePage();
            }
        });
        tree.getSelectionModel().addSelectionHandler(new SelectionHandler<SubmissionMenuData>() {
            @Override
            public void onSelection(SelectionEvent<SubmissionMenuData> event) {
                presenter.loadSelectedEntryEditor(event.getSelectedItem());
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
     * This method returns the submission set Tree widget.
     *
     * @return submission set tree
     */
    public Tree<SubmissionMenuData, String> getTree() {
        return tree;
    }

    /**
     * This method returns the submission set TreeStore, which contains all the data.
     *
     * @return submission set tree store
     */
    public TreeStore<SubmissionMenuData> getTreeStore() {
        return treeStore;
    }

}
