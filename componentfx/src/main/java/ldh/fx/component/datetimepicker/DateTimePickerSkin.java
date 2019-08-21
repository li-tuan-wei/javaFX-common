package ldh.fx.component.datetimepicker;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ldh.fx.component.datetimepicker.util.CustomBinding;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimePickerSkin extends DatePickerSkin {
    private final ObjectProperty<LocalTime> timeObjectProperty;
    private final Node popupContent;
    private final DateTimePicker dateTimePicker;
    private final Node timeSpinner;
    private final Node timeSlider;

    DateTimePickerSkin(DateTimePicker dateTimePicker) {
        super(dateTimePicker);
        this.dateTimePicker = dateTimePicker;
        timeObjectProperty = new SimpleObjectProperty<>(this, "displayedTime", LocalTime.from(dateTimePicker.getDateTimeValue()));

        CustomBinding.bindBidirectional(dateTimePicker.dateTimeValueProperty(), timeObjectProperty,
                LocalDateTime::toLocalTime,
                lt -> dateTimePicker.getDateTimeValue().withHour(lt.getHour()).withMinute(lt.getMinute())
        );

        popupContent = super.getPopupContent();
        popupContent.getStyleClass().add("date-time-picker-popup");

        timeSpinner = getTimeSpinner();
        timeSlider = getTimeSlider();

        dateTimePicker.timeSelectorProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                switch (oldValue) {
                    case SPINNER:
                        ((VBox) popupContent).getChildren().remove(timeSpinner);
                        break;
                    case SLIDER:
                        ((VBox) popupContent).getChildren().remove(timeSlider);
                        break;
                }
                switch (newValue) {
                    case SPINNER:
                        ((VBox) popupContent).getChildren().add(timeSpinner);
                        break;
                    case SLIDER:
                        ((VBox) popupContent).getChildren().add(timeSlider);
                        break;
                }
            }
        });

        if (dateTimePicker.getTimeSelector() == DateTimePicker.TimeSelector.SPINNER) {
            ((VBox) popupContent).getChildren().add(timeSpinner);
        } else if (dateTimePicker.getTimeSelector() == DateTimePicker.TimeSelector.SLIDER) {
            ((VBox) popupContent).getChildren().add(timeSlider);
        }

    }

    private Node getTimeSpinner() {
        if (timeSpinner != null) {
            return timeSpinner;
        }
        final HourMinuteSpinner spinnerHours =
                new HourMinuteSpinner(0, 23, dateTimePicker.getDateTimeValue().getHour());
        CustomBinding.bindBidirectional(timeObjectProperty, spinnerHours.valueProperty(),
                LocalTime::getHour,
                hour -> timeObjectProperty.get().withHour(hour)
        );
        final HourMinuteSpinner spinnerMinutes =
                new HourMinuteSpinner(0, 59, dateTimePicker.getDateTimeValue().getMinute());
        CustomBinding.bindBidirectional(timeObjectProperty, spinnerMinutes.valueProperty(),
                LocalTime::getMinute,
                minute -> timeObjectProperty.get().withMinute(minute)
        );
        final Label labelTimeSeperator = new Label(":");
        HBox hBox = new HBox(5, new Label("Time:"), spinnerHours, labelTimeSeperator, spinnerMinutes);
        hBox.setPadding(new Insets(8));
        hBox.setAlignment(Pos.CENTER_LEFT);
        dateTimePicker.minutesSelectorProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                if (newValue) {
                    hBox.getChildren().add(labelTimeSeperator);
                    hBox.getChildren().add(spinnerMinutes);
                } else {
                    hBox.getChildren().remove(labelTimeSeperator);
                    hBox.getChildren().remove(spinnerMinutes);
                }
            }
        });
//        registerChangeListener(dateTimePicker.valueProperty(), e -> {
//            LocalDateTime dateTimeValue = dateTimePicker.getDateTimeValue();
//            timeObjectProperty.set((dateTimeValue != null) ? LocalTime.from(dateTimeValue) : LocalTime.MIDNIGHT);
//            dateTimePicker.fireEvent(new ActionEvent());
//        });
        ObservableList<String> styleClass = hBox.getStyleClass();
        styleClass.add("month-year-pane");
        styleClass.add("time-selector-pane");
        styleClass.add("time-selector-spinner-pane");
        return hBox;
    }

    private Node getTimeSlider() {
        if (timeSlider != null) {
            return timeSlider;
        }
        final HourMinuteSlider sliderHours =
                new HourMinuteSlider(0, 23, dateTimePicker.getDateTimeValue().getHour(), 6, 5);
        CustomBinding.bindBidirectional(timeObjectProperty, sliderHours.valueProperty(),
                LocalTime::getHour,
                hour -> timeObjectProperty.get().withHour(hour.intValue())
        );
        final HourMinuteSlider sliderMinutes =
                new HourMinuteSlider(0, 59, dateTimePicker.getDateTimeValue().getMinute(), 10, 9);
        CustomBinding.bindBidirectional(timeObjectProperty, sliderMinutes.valueProperty(),
                LocalTime::getMinute,
                minute -> timeObjectProperty.get().withMinute(minute.intValue())
        );
        final VBox vBox = new VBox(5, sliderHours, sliderMinutes);
        vBox.setPadding(new Insets(8));
        dateTimePicker.minutesSelectorProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                if (newValue) {
                    vBox.getChildren().add(sliderMinutes);
                } else {
                    vBox.getChildren().remove(sliderMinutes);
                }
            }
        });
        ObservableList<String> styleClass = vBox.getStyleClass();
        styleClass.add("month-year-pane");
        styleClass.add("time-selector-pane");
        styleClass.add("time-selector-slider-pane");
        return vBox;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node getPopupContent() {
        return popupContent;
    }


}
