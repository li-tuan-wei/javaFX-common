package ldh.fx.component.table;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import ldh.common.*;
import ldh.common.Pagination;
import ldh.fx.component.table.function.LoadData;
import ldh.fx.component.table.function.Searchable;
import ldh.fx.util.DialogUtil;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.MasterDetailPane;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ldh on 2017/4/10.
 */
public class GridTable<S> extends StackPane {

    protected TableView<S> tableView = new TableView();

    private MaskerPane maskerPane = new MaskerPane();
    private PageablePane pageablePane = new PageablePane();
    private VBox container = new VBox();
    private MasterDetailPane masterDetailPane = new MasterDetailPane(Side.TOP);
    private ToolBar toolBar = new ToolBar();
    private LoadData loadData;

    public GridTable() {
        maskerPane.setVisible(false);
        createBottom();
        
        VBox vbox = new VBox();
        vbox.getChildren().addAll(tableView, pageablePane);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        masterDetailPane.setShowDetailNode(false);
        masterDetailPane.setMasterNode(vbox);

        container.getChildren().addAll(toolBar, masterDetailPane);
        VBox.setVgrow(masterDetailPane, Priority.ALWAYS);

        this.getChildren().addAll(container, maskerPane);
//        this.load(new Pagination(1, 10));

        tableView.getStyleClass().add("table");
        this.getStyleClass().add("grid-table");
    }

    public final ObservableList<TableColumn<S,?>> getColumns() {
        return tableView.getColumns();
    }

    public final ObservableList<Node> getItems() {
        return toolBar.getItems();
    }

    public final TableView<S> getTableView() {
        return tableView;
    }

    public final MaskerPane getMaskerPane() {
        return maskerPane;
    }

    public final ToolBar getToolBar() {
        return toolBar;
    }

    public final PageablePane getPageablePane() {
        return pageablePane;
    }

    public final MasterDetailPane getMasterDetailPane() {
        return masterDetailPane;
    }

    public void setShowToolbar(boolean isShow) {
        toolBar.setVisible(isShow);
        toolBar.setManaged(isShow);
    }

    private void createBottom() {
//        pageablePane.setPageSizes(gridTableModel.getPageSizes());
//        pageablePane.setPageSize(gridTableModel.getDefaultPageSize());
//        pageablePane.setLoadData(this);
    }

    public void setLoadData(LoadData loadData) {
        this.loadData = loadData;
        pageablePane.setLoadData(loadData);
    }

    public void expandPane() {
        Region region = (Region) masterDetailPane.getDetailNode();
        masterDetailPane.setDividerPosition(region.getPrefHeight()/masterDetailPane.getHeight());
        masterDetailPane.setShowDetailNode(!masterDetailPane.isShowDetailNode());
    }

//    @Override
    public void load(Pageable pageable) {
        Pageable searchPagination = pageable;
        maskerPane.setVisible(true);

        Task<Void> task = new Task() {
            @Override
            protected Void call() throws Exception {
//                initPageable(paramMap, searchPagination);
                PageResult<S> pageResult = loadPageResult(pageable);
                Platform.runLater(()->setData(pageResult));
                return null;
            }
        };
        task.setOnSucceeded(event -> {loadingEnd(task);});
        task.setOnFailed(event->{loadingEnd(task);});
        new Thread(task).start();
    }

    public PageResult<S> loadPageResult(Pageable pageable) {
        throw new RuntimeException("null");
    }

    private void initPageable(Map<String, Object> paramMap, Pageable searchPagination) {
        if (searchPagination == null) return;
        paramMap.put("pageSize", searchPagination.getPageSize());
        paramMap.put("pageNo", searchPagination.getPageNo());
    }

    private void loadingEnd(Task task) {
        maskerPane.textProperty().unbind();
        maskerPane.progressProperty().unbind();
        maskerPane.setVisible(false);
        if (task.getException() != null) {
            task.getException().printStackTrace();
            DialogUtil.show(Alert.AlertType.CONFIRMATION, "错误", task.getException().getMessage());
        }
    }

    public void setData(PageResult pageResult) {
        if (pageResult != null) {
            tableView.getItems().clear();
            tableView.getItems().addAll(pageResult.getBeans());
            tableView.refresh();
            pageablePane.setPageResult(pageResult);
        }
    }

    public void setSearchPane(String fxml) {
        Region region = buildSearchContainer(fxml);
        masterDetailPane.setDetailNode(region);
        expandPane();
    }

    private Region buildSearchContainer(String fxml) {
        Region region = null;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
        try {
            region = fxmlLoader.load();
//            Object controller = fxmlLoader.getController();
//            if (controller instanceof Searchable) {
//                this.searchable = (Searchable) controller;
//            }
//            if (controller instanceof CrudForm) {
//                CrudForm crudForm = (CrudForm) controller;
//                crudForm.setLoadData(this);
//            }
//
//            parent.setPadding(new Insets(5));
//            hbox.getChildren().add(parent);
//            parent.prefWidthProperty().bind(hbox.widthProperty());
//            hbox.setPrefHeight(parent.getPrefHeight() <= 0 ? 130 : parent.prefHeight(-1));
//            hbox.widthProperty().addListener(b->{
//                System.out.println(tableView.getPrefWidth() + ":" + tableView.getWidth());
//                masterDetailPane.setDividerPosition(parent.getPrefHeight()/tableView.getHeight());
//            });
//            masterDetailPane.setDividerPosition(0.4);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return region;
    }
}
