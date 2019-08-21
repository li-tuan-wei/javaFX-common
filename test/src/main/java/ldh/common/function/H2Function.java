package ldh.common.function;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Puhui on 2016/12/8.
 */
public class H2Function {

    private static final Map<String, String> MAP = new HashMap<String, String>();
    static {
        MAP.put("%H", "HH");
        MAP.put("%h", "hh");
        MAP.put("%i", "hh");
        MAP.put("%i", "mm");
        MAP.put("%k", "H");
        MAP.put("%m", "MM");
        MAP.put("%k", "H");
        MAP.put("%Y", "yyyy");
        MAP.put("%s", "ss");
        MAP.put("%S", "ss");
        MAP.put("%d", "dd");
    }

    public static String toDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String date_format(Date date, String fromat) {
        String ff = fromat;
        for (Map.Entry<String, String> entry : MAP.entrySet()) {
            ff = ff.replace(entry.getKey(), entry.getValue());
        }
        SimpleDateFormat sdf = new SimpleDateFormat(ff);
        return sdf.format(date);
    }
}
