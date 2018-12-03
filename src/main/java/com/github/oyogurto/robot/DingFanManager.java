package com.github.oyogurto.robot;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoujingchun
 * created on 2018/10/17
 * description:
 */
public class DingFanManager {
    private ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
    private long aDayTimeCount = 24*60*60*1000;
    private long targetTimeCount = 8*60*60*1000;

    public void start(){
        System.out.println("start notify dingFan");
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek==Calendar.SUNDAY||dayOfWeek==Calendar.SATURDAY){
            System.out.println("not weekday, skip");
            return;
        }
        try {
            new DingClient().notifyDingFan();
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.scheduleAtFixedRate(() -> {
            try {
                new DingClient().notifyDingFan();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },getNextTime(new Date().getTime())-new Date().getTime(), aDayTimeCount, TimeUnit.MILLISECONDS);
    }

    private long getNextTime(long curTime){
        return ((curTime-targetTimeCount)/aDayTimeCount+1)*aDayTimeCount+targetTimeCount;
    }
}
