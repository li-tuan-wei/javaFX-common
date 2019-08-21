package ldh.common.service;

import java.util.Date;

/**
 * Created by ldh on 2016/12/24.
 */
public interface ClockService {

    void setCurrentDate(Date date);

    Date getCurrentDate();
}
