package table;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ldh.fx.component.datetimepicker.DateTimePicker;

public class DateTimePickerTest3 extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		final VBox vBox = new VBox();
		vBox.getChildren().add(new Label("Date/Time"));
        DateTimePicker d = new DateTimePicker();
        d.setTimeSelector(DateTimePicker.TimeSelector.SLIDER);
		vBox.getChildren().add(d);

		final Scene scene = new Scene(vBox);

		primaryStage.setScene(scene);
		primaryStage.sizeToScene();

		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
