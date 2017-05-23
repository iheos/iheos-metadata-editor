package gov.nist.hit.ds.docentryeditor.client.widgets.browser;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.TreeViewModel;
import gov.nist.hit.ds.docentryeditor.shared.util.ExtCacheFile;

import java.util.List;

/**
 * The model that defines the nodes in the tree (browser widget).
 * Created by onh2 on 5/10/17.
 */
public class ExtCacheBrowserTreeViewModel implements TreeViewModel {
    private List<ExtCacheFile> rootChildrenList;

    public ExtCacheBrowserTreeViewModel(String s){
        super();
    }

    /**
     * Get the {@link NodeInfo} that provides the children of the specified value.
     * @param value
     * @param <T>
     * @return
     */
    @Override
    public <T> NodeInfo<?> getNodeInfo(T value) {
        // Create a Cell to display the content of the data provider.
        ExtCacheFileCell cell=new ExtCacheFileCell();
        if (value==null){
            // We passed null as the root value.
            // Create a data provider that contains the list of files.
            ListDataProvider<ExtCacheFile> dataProvider= new ListDataProvider<ExtCacheFile>(rootChildrenList);
            // return a node info that pairs the data provider and the cell
            return new DefaultNodeInfo<ExtCacheFile>(dataProvider,cell);
        }else if(value instanceof ExtCacheFile){
            // Create a data provider that contains the list of files.
            ListDataProvider<ExtCacheFile> dataProvider = new ListDataProvider<ExtCacheFile>(((ExtCacheFile) value).getChildren());
            // return a node info that pairs the data provider and the cell.
            return new DefaultNodeInfo<ExtCacheFile>(dataProvider,cell);
        }
        return null;
    }

    @Override
    public boolean isLeaf(Object value) {
        // The leaf nodes are the files that are not a directory.
        if (value instanceof  ExtCacheFile){
            ExtCacheFile file = (ExtCacheFile) value;
            if (file.getChildren().isEmpty()){
                // if the file does not have any children, it means its not a directory
                return true;
            }
        }
        return false;
    }

    private class ExtCacheFileCell extends AbstractCell<ExtCacheFile>{

        @Override
        public void render(Context context, ExtCacheFile value, SafeHtmlBuilder sb) {
            if (value!=null){
                sb.appendEscaped(value.getName());
            }
        }
    }
}
