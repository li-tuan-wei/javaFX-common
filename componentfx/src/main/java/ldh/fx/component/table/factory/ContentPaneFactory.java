package ldh.fx.component.table.factory;

import javafx.scene.Node;
import ldh.fx.component.table.CrudGridTable;
import ldh.fx.component.table.model.GridTableModel;
import ldh.fx.util.JsonParse;

/**
 * Created by ldh on 2017/4/14.
 */
public class ContentPaneFactory {

    public Node create(String name) {
        GridTableModel tableModel = null;
        try {
            tableModel = JsonParse.parseTableModel("/data/table/" + name + ".json");
            return new CrudGridTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
