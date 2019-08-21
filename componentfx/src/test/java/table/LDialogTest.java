package table;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ldh.fx.component.DialogModel;
import ldh.fx.component.LDialog;
import ldh.fx.component.LxDialog;

/**
 * Created by ldh123 on 2018/6/16.
 */
public class LDialogTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        HBox hbox = new HBox();
        Button b1 = new Button("bb1");
        b1.setOnAction(e->{
            LxDialog lDialog = new LxDialog(primaryStage, "demo", DialogModel.Application_model, 200d, 100d);
            lDialog.getScene().getStylesheets().add("/component/LxDialog.css");
            lDialog.show();

        });
        hbox.getChildren().addAll(b1);
        Scene scene = new Scene(hbox, 200, 300);
        scene.getStylesheets().add("/component/LxDialog.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
