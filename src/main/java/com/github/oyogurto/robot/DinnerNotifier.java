package com.github.oyogurto.robot;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoujingchun
 * created on 2018/10/17
 * description:
 */
public class DinnerNotifier {
    private ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
    private long aDayTimeCount = 24*60*60*1000;
    private long targetTimeCount = 8*60*60*1000;

    public void start(){
        System.out.println("start notify dingFan");
        try {
            if (new HolidayChecker().isWorkDay(new Date().getTime())) {
                new DingClient().notifyDingFan();
            } else {
                System.out.println(new Date() + " today is holiday day.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.scheduleAtFixedRate(() -> {
            try {
                if (new HolidayChecker().isWorkDay(new Date().getTime())) {
                    new DingClient().notifyDingFan();
                } else {
                    System.out.println(new Date() + " today is holiday day.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        },getNextTime(new Date().getTime())-new Date().getTime(), aDayTimeCount, TimeUnit.MILLISECONDS);
    }

    private long getNextTime(long curTime){
        return ((curTime-targetTimeCount)/aDayTimeCount+1)*aDayTimeCount+targetTimeCount;
    }
}
