package gov.nist.hit.ds.docentryeditor.client.generics;

/**
 * Factory to handle model related action for a grid.
 * Created by onh2 on 7/17/2014.
 */
public interface GridModelFactory<M> {
    /**
     * Factory method to return a new instance of grid element.
     * This method must return GWT.create("M class".class)
     *
     * @return M instance using GWT.create()
     */
    public M newInstance();
}
