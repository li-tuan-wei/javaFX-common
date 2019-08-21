package ldh.fx.cell;

import com.sun.javafx.property.PropertyReference;
import com.sun.javafx.scene.control.Logging;
import javafx.beans.NamedArg;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by ldh123 on 2018/6/3.
 */
public class FunctionPropertyValueFactory <S,T> extends PropertyValueFactory<S, T> {

    private final String data;
    private BiFunction<String, T, ?> bigFunction = null;


    /**
     * Creates a default PropertyValueFactory to extract the value from a given
     * TableView row item reflectively, using the given property name.
     *
     * @param property The name of the property with which to attempt to
     *      reflectively extract a corresponding value for in a given object.
     */
    public FunctionPropertyValueFactory(@NamedArg("property") String property, @NamedArg(value = "data", defaultValue = "") String data) {
        super(property);
        this.data = data;
    }

    /** {@inheritDoc} */
    @Override public ObservableValue<T> call(TableColumn.CellDataFeatures<S,T> param) {
        ObservableValue<T> value = super.call(param);
        if (value != null && bigFunction != null) {

        }
        return value;
    }
}
