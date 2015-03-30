package gov.nist.hit.ds.docentryeditor.client.generics;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.fx.client.Draggable;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.*;
import com.sencha.gxt.widget.core.client.form.Field;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.editing.ClicksToEdit;
import com.sencha.gxt.widget.core.client.grid.editing.GridRowEditing;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import gov.nist.hit.ds.docentryeditor.client.resources.AppImages;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Generic widget for Row Grid editing on double click. For each editable
 * field of the grid the method
 * {@link #addColumnEditorConfig(ColumnConfig, Field) addColumnEditorConfig}
 * should be called.
 * <p/>
 * <br/>
 * <br/>
 * Example:
 * <p/>
 * <p/>
 * <pre>
 * <code>
 *      InternationalStringProperties isprops = GWT.create(InternationalStringProperties.class);
 *
 *      List<ColumnConfig<InternationalString, ?>> columnsConfigs = new ArrayList<ColumnConfig<InternationalString, ?>>();
 *      ColumnConfig<InternationalString, LanguageCode> cc1 = new ColumnConfig<InternationalString, LanguageCode>(isprops.langCode(), 15, "Language Code");
 *      ColumnConfig<InternationalString, String> cc2 = new ColumnConfig<InternationalString, String>(isprops.value(), 85, "Title");
 *      columnsConfigs.add(cc1);
 *      columnsConfigs.add(cc2);
 *      ColumnModel<InternationalString> columnModel = new ColumnModel<InternationalString>(columnsConfigs);
 *
 *      GenericEditableGrid<InternationalString> grid=new GenericEditableGrid<InternationalString>(InternationalString.class,"Title",new ListStore<InternationalString>(isprops.key()), columnModel);
 *      // grid.setCheckBoxSelectionModel();
 *
 *      LanguageCodeComboBox languageCodeComboBox = new LanguageCodeComboBox();
 *      languageCodeComboBox.setAllowBlank(false);
 *      // languageCodeComboBox.setToolTip("...");
 *      TextField tf = new TextField();
 *      tf.setAllowBlank(false);
 *      // tf.setToolTip("...");
 *
 *      grid.addColumnEditorConfig(cc1,languageCodeComboBox);
 *      grid.addColumnEditorConfig(cc2,tf);
 * </code>
 * </pre>
 *
 * @see Grid <p/>
 * Created by onh2 on 6/10/2014.
 */
public abstract class GenericEditableGrid<M> extends Grid<M> {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    // private Class<M> clazzM;

    // --- UI Widgets.
    private final VerticalLayoutContainer gridContainer = new VerticalLayoutContainer();
    // Toolbar elements.
    private final ToolBar toolBar = new ToolBar();
    private final TextButton newItemButton = new TextButton();
    private final TextButton deleteItemsButton = new TextButton();
    private final TextButton clearButton = new TextButton();
    private final TextButton helpButton = new TextButton();
    private final ContentPanel pane = new ContentPanel();
    private final ToolTipConfig helpTooltipConfig = new ToolTipConfig();
    // grid editing.
    protected GridRowEditing<M> editing;

    // widget configuration variables.
    private int storeMaxLength = 0;
    private boolean checkBoxEnabled = false;
    private boolean hasExtraWidget = false;
    // toolbar configuration variables.
    private boolean hasToolbar = true;
    private boolean hasHelpButtonEnabled = false;
    private boolean toolTipAutoShow = true;

    /**
     * Generic editable grid (abstract class) constructor.
     * @param gridTitle the title of the widget (for the grid's panel header).
     * @param listStore the store that will handle the list a values (object) manipulated by the grid.
     */
    public GenericEditableGrid(/* Class<M> parametrizedClass, */String gridTitle, ListStore<M> listStore) {
        super(listStore, new ColumnModel<M>(new ArrayList<ColumnConfig<M, ?>>()));

        this.cm = buildColumnModel();

        // clazzM = parametrizedClass;
        pane.setHeadingText(gridTitle);
        pane.setBorders(false);
        pane.setCollapsible(true);

        this.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);

        this.getView().setAutoFill(true);
        this.setBorders(false);

        this.addStyleName("grid-minheight");
        this.setHeight(250);

        // toolbar configuration.
        newItemButton.setIcon(AppImages.INSTANCE.add());
        newItemButton.setToolTip("Add an new element");
        toolBar.add(newItemButton);
        deleteItemsButton.setIcon(AppImages.INSTANCE.delete());
        deleteItemsButton.setToolTip("Delete selected element(s)");
        toolBar.add(deleteItemsButton);
        clearButton.setIcon(AppImages.INSTANCE.clear());
        clearButton.setToolTip("Clear all elements");
        toolBar.add(clearButton);
        helpButton.setIcon(AppImages.INSTANCE.help());
        helpButton.setTitle("Help?");

        // widget information help panel configuration.
        helpTooltipConfig.setTitleText("Widget help");
        helpTooltipConfig.setAnchor(Style.Side.LEFT);
        helpTooltipConfig.setCloseable(true);
        helpTooltipConfig.setMouseOffsetX(0);
        helpTooltipConfig.setMouseOffsetY(0);

        gridContainer.add(toolBar);
        gridContainer.add(super.asWidget(), new VerticalLayoutContainer.VerticalLayoutData(1, 1)); // VerticalLayoutData does not work here why?
        pane.setWidget(gridContainer);

        // makes the grid editable.
        setEditable();
        buildEditingFields();

        // some tries to make grid fit panel's height.
        this.getView().setAdjustForHScroll(false);
        this.getView().setForceFit(true);
        pane.forceLayout();
        gridContainer.forceLayout();

        bindUI();
    }

    /**
     * Abstract method meant to build the model that will define the different columns of the grid.
     * @return grid column model.
     */
    protected abstract ColumnModel<M> buildColumnModel();

    /**
     * Abstract method meant to set a field widget for each column of the grid in edition mode.
     */
    protected abstract void buildEditingFields();

    /**
     * This method adds an extra widget to the widget just after the grid.
     * @param widget widget to add to the widget.
     */
    protected void addWidget(Widget widget) {
        hasExtraWidget = true;
        widget.addStyleName("topBorder");
        gridContainer.add(widget, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(0, 4, 1, 4)));
    }

    /**
     * This method adds extra widgets to the widget just after the grid.
     * @param widgets multiple widgets to add to the widget.
     */
    protected void addWidgets(Widget... widgets) {
        hasExtraWidget = true;
        boolean firstDone = false;
        for (Widget w : widgets) {
            if (firstDone == false) {
                w.addStyleName("topBorder");
                firstDone = true;
            }
            gridContainer.add(w, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(0, 4, 1, 4)));
        }
    }

    /**
     * Abstract method meant to return the desired ModelFactory for the editable grid.
     * It is used for the create of a new "Model" instance.
     * @return specific GridModelFactory
     */
    protected abstract GridModelFactory<M> getModelFactory();

    /**
     * Needs to be used to get the Grid Editable. It configures how the columns
     * will be edited.
     *
     * @param columnConfig Grid's Column Configuration which will be associated to a type
     *                     of editable field
     * @param field        Editable field which will be used to edit the grid's Column
     */
    public <N> void addColumnEditorConfig(ColumnConfig<M, N> columnConfig, Field<N> field) {
        editing.addEditor(columnConfig, field);
    }

    /**
     * This method renders the widget.
     * @return Editable grid widget.
     */
    public Widget getDisplay() {
        pane.setResize(true);
        return pane.asWidget();
    }

    /**
     * Method that binds the actions related to the help panel and the tooltip button of the widget.
     */
    private void bindToolTips() {
        helpButton.getToolTip().addShowHandler(new ShowEvent.ShowHandler() {
            @Override
            public void onShow(ShowEvent event) {
                if (toolTipAutoShow)
                    helpButton.getToolTip().hide();
            }
        });
        helpButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                if (toolTipAutoShow) {
                    toolTipAutoShow = false;
                    helpButton.getToolTip().show();
                } else {
                    helpButton.getToolTip().hide();
                }
            }
        });
        helpButton.getToolTip().addHideHandler(new HideEvent.HideHandler() {
            @Override
            public void onHide(HideEvent event) {
                toolTipAutoShow = true;
            }
        });
    }

    /**
     * Method that binds the widgets of the UI with actions.
     */
    private void bindUI() {
        newItemButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent selectEvent) {
                // try to fire an event
//                logger.info("current list size: " + getStore().size() + "\nStore max size: " + storeMaxLength);
                if (getStore().size() < storeMaxLength || storeMaxLength == 0) {
                    editing.cancelEditing();
                    // this method should work I don't understand why there is a problem.
                    // M element = GWT.create(clazzM);
                    M element = getModelFactory().newInstance();
                    getStore().add(0, element);
                    int index = 0;
                    if (isCheckBoxEnabled()) {
                        index = 1;
                    }
                    editing.startEditing(new Grid.GridCell(getStore().indexOf(element), index));
                    if (getStore().size() >= storeMaxLength && storeMaxLength != 0) {
                        disableNewButton();
                    }
                } else {
                    MessageBox mb = new MessageBox("Error: list size limit reached",
                            "You can not add more items to that list. This list can contain only " + storeMaxLength
                                    + " items.");
                    mb.show();
                }
            }
        });
        deleteItemsButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                final ConfirmMessageBox d = new ConfirmMessageBox("Confirm delete action",
                        "Are you sure you want to delete these values?");
                d.show();
                d.addDialogHideHandler(new DialogHideEvent.DialogHideHandler() {
                    @Override
                    public void onDialogHide(DialogHideEvent event) {
                        if (event.getHideButton() == Dialog.PredefinedButton.YES) {
                            deleteSelectedItemAction();
                            if (getStore().size() < storeMaxLength && storeMaxLength != 0) {
                                enableNewButton();
                            }
                        }
                    }
                });
            }
        });
        clearButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                final ConfirmMessageBox d = new ConfirmMessageBox("Confirm clear action",
                        "Are you sure you want to delete all values from this grid?");
                d.show();
                d.addDialogHideHandler(new DialogHideEvent.DialogHideHandler() {
                    @Override
                    public void onDialogHide(DialogHideEvent event) {
                        if (event.getHideButton() == Dialog.PredefinedButton.YES) {
                            clearStoreAction();
                            enableNewButton();
                        }
                    }
                });
            }
        });
    }

    /**
     * Method that clears the grid store.
     */
    protected void clearStoreAction() {
        getStore().clear();
        getStore().commitChanges();
    }

    /**
     * Method that delete the selected item from the grid.
     */
    protected void deleteSelectedItemAction() {
        for (M e : getSelectionModel().getSelectedItems()) {
            getStore().remove(e);
            getStore().commitChanges();
        }
    }

    /**
     * This method disable the editing option of the grid, which will not be editable anymore.
     */
    public void disableEditing() {
        disableToolbar();
        // this.disable();
        editing.clearEditors();
    }

    /**
     * This method disables the new button of the widget (toolbar).
     */
    public void disableNewButton() {
        newItemButton.disable();
    }

    /**
     * This method enables the new button of the widget (toolbar).
     */
    public void enableNewButton() {
        newItemButton.enable();
    }

    /**
     * This method "removes" the toolbar of the grid widget.
     */
    public void disableToolbar() {
        hasToolbar = false;
        toolBar.disable();
        toolBar.setVisible(false);
    }

    /**
     * This method returns the toolbar of the widget.
     * @return grid's toolbar.
     */
    public ToolBar getToolbar() {
        return toolBar;
    }

    public boolean isCheckBoxEnabled() {
        return checkBoxEnabled;
    }

    /**
     * This method makes the grid editable on double click on an entry of the grid (Default behavior).
     */
    protected void setEditable() {
        setEditable(ClicksToEdit.TWO);
    }

    /**
     * This method makes the grid editable on the specified number of click on an entry of the grid (ONE or TWO).
     * @param clicksToEdit number of clicks on value to enter the grid edition mode.
     */
    protected void setEditable(ClicksToEdit clicksToEdit) {
        // EDITING //
        editing = new GridRowEditing<M>(this);
        editing.setClicksToEdit(clicksToEdit);
        editing.addCompleteEditHandler(new CompleteEditEvent.CompleteEditHandler<M>() {
            @Override
            public void onCompleteEdit(CompleteEditEvent<M> completeEditEvent) {
                getStore().commitChanges();
            }
        });
    }

    /**
     * This method enables to show or hide the widget header.
     * @param visible
     */
    public void setHeaderVisible(boolean visible) {
        pane.setHeaderVisible(visible);
    }

    /**
     * This method enables to change the height of the widget.
     * @param height
     */
    public void setHeight(int height) {
        gridContainer.setHeight(height);
        pane.setHeight(height);
    }

    /**
     * This method calculates approximately the height of the grid widget based on the number of elements
     * supposed to be registered in the grid.
     * @param storeMaxLength estimated number of elements supposed to be stored in the grid.
     */
    protected void setStoreMaxLength(int storeMaxLength) {
        this.storeMaxLength = storeMaxLength;
        if (storeMaxLength == 1) {
            this.setHeight(85 + (hasToolbar == true ? 35 : 0) + (hasExtraWidget == true ? 30 : 0));
        } else {
            if (storeMaxLength < 11) {
                this.setHeight((30 * storeMaxLength) + (hasToolbar == true ? 35 : 0) + (hasExtraWidget == true ? 30 : 0));
            }
        }
    }

    /**
     * This method sets the content of the widget help panel.
     * @param helpContent
     */
    public void setWidgetHelpContent(String helpContent) {
        helpTooltipConfig.setBodyHtml(helpContent);
        if (!hasHelpButtonEnabled) {
            toolBar.add(helpButton);
            hasHelpButtonEnabled = true;
        }
        helpButton.setToolTipConfig(helpTooltipConfig);
        Draggable d = new Draggable(helpButton.getToolTip());
        d.setUseProxy(false);
        bindToolTips();
    }

    /**
     * This method sets the widget help panel.
     * @param helpContent
     */
    public void setToolbarHelpButtonTooltip(ToolTipConfig helpContent) {
        helpTooltipConfig.setBodyHtml(helpContent.getBodyHtml());
        helpTooltipConfig.setTitleHtml(helpContent.getTitleHtml());
        if (!hasHelpButtonEnabled) {
            toolBar.add(helpButton);
            hasHelpButtonEnabled = true;
        }
        helpButton.setToolTipConfig(helpTooltipConfig);
        Draggable d = new Draggable(helpButton.getToolTip());
        d.setUseProxy(false);
        bindToolTips();
    }

    /**
     * This method return the maximum number of elements that can be stored in the grid.
     * @return number of elements that can be stored in the grid.
     */
    public int getStoreMaxSize() {
        return storeMaxLength;
    }

    //--------------------------------------------------------------------------------------------------------
    //----  SHOULD NOT BE USED WITH GIRD ROW EDITING, ONLY WITH GRID INLINE EDITING
    /**
     * This Method enables the grid's selection checkbox column (for
     * multiselection).
     */
    public void setCheckBoxSelectionModel() {
        checkBoxEnabled = true;
        List<ColumnConfig<M, ?>> columnsConfigs = new ArrayList<ColumnConfig<M, ?>>();
        IdentityValueProvider<M> identityValueProvider = new IdentityValueProvider<M>();
        CheckBoxSelectionModel<M> selectColumn = new CheckBoxSelectionModel<M>(identityValueProvider);
        selectColumn.setSelectionMode(Style.SelectionMode.MULTI);
        columnsConfigs.add(selectColumn.getColumn());
        columnsConfigs.addAll(cm.getColumns());

        this.cm = new ColumnModel<M>(columnsConfigs);

        this.setSelectionModel(selectColumn);

        this.getSelectionModel().addSelectionChangedHandler(new SelectionChangedEvent.SelectionChangedHandler<M>() {
            @Override
            public void onSelectionChanged(SelectionChangedEvent<M> event) {
                // TODO whatever you have to do
            }
        });

        setEditable();
    }
    //--------------------------------------------------------------------------------------------------------
}
