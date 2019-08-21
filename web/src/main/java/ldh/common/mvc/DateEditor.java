package ldh.common.mvc;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateEditor extends PropertyEditorSupport {

    private String formatStr = "yyyy-MM-dd HH:mm:ss";

    public DateEditor() {
    }

    public DateEditor(String format) {
        this.formatStr = format;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.trim().equals("")) return;
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = format.parse(text);
        } catch (ParseException e) {
            format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                date = format.parse(text);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        setValue(date);
    }
}
