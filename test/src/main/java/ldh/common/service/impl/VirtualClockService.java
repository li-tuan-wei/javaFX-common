package ldh.common.service.impl;

import ldh.common.service.ClockService;

import java.util.Date;

/**
 * Created by ldh on 2016/12/24.
 */
public class VirtualClockService implements ClockService {

    private Date currentDate;

    @Override
    public void setCurrentDate(Date date) {
        currentDate = date;
    }

    @Override
    public Date getCurrentDate() {
        if (currentDate == null) {
            currentDate = new Date();
        }
        return currentDate;
    }
}
