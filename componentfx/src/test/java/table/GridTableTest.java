package table;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ldh.fx.StageUtil;
import ldh.fx.component.table.CrudGridTable;
import ldh.fx.component.table.GridTable;
import ldh.fx.util.DialogUtil;

/**
 * Created by ldh123 on 2018/5/21.
 */
public class GridTableTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        StageUtil.STAGE = primaryStage;
        GridTable gridTable = FXMLLoader.load(GridTableTest.class.getResource("/fxml/GridTableDemo.fxml"));
        gridTable.setSearchPane("/fxml/SearchDemo.fxml");
        VBox vbox = new VBox();
        vbox.getChildren().add(gridTable);
        vbox.setPadding(new Insets(15,15,15,15));
        Scene scene = new Scene(vbox, 600, 400);
        scene.getStylesheets().add("/component/GridTable.css");
        scene.getStylesheets().add("/component/LDialog.css");
        primaryStage.setScene(scene);
        primaryStage.show();
//        gridTable.setSearchPane("/fxml/SearchDemo.fxml");
    }
}
