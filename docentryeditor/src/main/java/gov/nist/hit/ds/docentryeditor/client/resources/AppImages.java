package gov.nist.hit.ds.docentryeditor.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * Application image resources.
 */
public interface AppImages extends ClientBundle {
    AppImages INSTANCE = GWT.create(AppImages.class);

    @Source("baseResources/file.png")
    ImageResource file();

    @Source("baseResources/subset.png")
    ImageResource subset();

    @Source("baseResources/file-plus.png")
    ImageResource filePlus();

    @Source("baseResources/folder-32.png")
    ImageResource newFolder();

    @Source("baseResources/prefilled.png")
    ImageResource preFilled();

    @Source("baseResources/Warning-128.png")
    ImageResource warning();

    @Source("baseResources/load-file.png")
    ImageResource loadFile();

    @Source("baseResources/edit-subSet.png")
    ImageResource editSubSet();

    @Source("baseResources/load-file-16.png")
    ImageResource loadFile12px();

    @Source("baseResources/add.gif")
    ImageResource add();

    @Source("baseResources/clear.gif")
    ImageResource clear();

    @Source("baseResources/delete.gif")
    ImageResource delete();

    @Source("baseResources/checkbox.png")
    ImageResource checkbox();

    @Source("baseResources/comment.png")
    ImageResource help();

    @Source("baseResources/save-disk.png")
    ImageResource save();

    @Source("baseResources/refresh.gif")
    ImageResource refresh();

    @Source("baseResources/home.png")
    ImageResource home();

    @Source("baseResources/save-2.png")
    ImageResource saveBW();

    @Source("baseResources/back-16.png")
    ImageResource back();

    @Source("baseResources/populate.png")
    ImageResource pen();

    @Source("baseResources/link-12.png")
    ImageResource association();

}
