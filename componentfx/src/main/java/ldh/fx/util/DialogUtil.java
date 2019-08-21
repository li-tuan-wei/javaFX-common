package ldh.fx.util;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import ldh.fx.StageUtil;
import ldh.fx.component.DialogModel;
import ldh.fx.component.LDialog;
import ldh.fx.StageUtil;
import ldh.fx.component.DialogModel;
import ldh.fx.component.LdhDialog;
import ldh.fx.component.LxDialog;
import ldh.fx.ui.util.RegionUtil;

/**
 * Created by ldh on 2017/2/26.
 */
public class DialogUtil {

    public static void show(Alert.AlertType type, String title, String info) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(info);
        alert.setContentText(null);
        alert.showAndWait();
    }

    public static void info(String title, String info, double width, double height) {
        LxDialog window = new LxDialog(StageUtil.STAGE, title, DialogModel.Normal, width, height);
//        window.getScene().getStylesheets().add(DialogUtil.class.getResource("/component/LDialog.css").toExternalForm());
        Label label = new Label(info);
        label.setPadding(new Insets(5));
        window.setContentPane(label);
        window.isShowingMinButton(false);
        window.isShowingMaxButton(true);
        window.show();
    }

    public static void info(String title, String info, double width, double height, Node parentNode) {
        LxDialog window = new LxDialog(StageUtil.STAGE, title, DialogModel.Normal, width, height);
//        window.getScene().getStylesheets().add(DialogUtil.class.getResource("/component/LDialog.css").toExternalForm());
        Label label = new Label(info);
        label.setPadding(new Insets(5));
        window.setContentPane(label);
        window.isShowingMinButton(false);
        window.isShowingMaxButton(true);
        if (parentNode != null) {
            double anchorX = RegionUtil.anchorX(parentNode);
            double anchorY = RegionUtil.anchorY(parentNode);
            if (parentNode instanceof Region) {
                anchorX = anchorX + (((Region) parentNode).getWidth() - width) /2;
                anchorY = anchorY + (((Region) parentNode).getHeight() - height) /2;
            }
            window.setLayoutX(anchorX);
            window.setLayoutY(anchorY);
        }
        window.show();
    }

    public static void modelInfo(String title, String info, double width, double height) {
        LxDialog window = new LxDialog(StageUtil.STAGE, title, DialogModel.Application_model, width, height);
        window.getScene().getStylesheets().add("/component/LxDialog.css");
        Label label = new Label(info);
        label.setPadding(new Insets(5));
        window.setContentPane(label);
        window.setPrefSize(width, height);
        window.isShowingMinButton(false);
        window.isShowingMaxButton(false);
//        window.setModel(true);
        window.show();
    }
}

