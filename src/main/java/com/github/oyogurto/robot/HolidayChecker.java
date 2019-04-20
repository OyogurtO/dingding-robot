package com.github.oyogurto.robot;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author ZhouJingchun
 * @since 2019/4/20
 **/
public class HolidayChecker {
    boolean isWorkDay(long time) {
        boolean isWorkDay = false;
        BufferedReader in = null;
        StringBuilder sb = new StringBuilder();
        try {
            // 获取今天是否是节假日
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String after14 = sdf.format(time);
            URL url = new URL("http://api.goseek.cn/Tools/holiday?date=" + after14);
            in = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
            String str = null;
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
            //System.out.println(sb.toString());
            //json串转化为json对象
            HolidayCheckResult result = JSON.parseObject(sb.toString(),HolidayCheckResult.class);
            System.out.println(result);
            int data=result.getData();
            isWorkDay = data==0;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                System.out.println(e);
            }
            return isWorkDay;
        }

    }

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019,Calendar.MAY,8);
        System.out.println(new HolidayChecker().isWorkDay(calendar.getTime().getTime()));
        System.out.println(new HolidayChecker().isWorkDay(new Date().getTime()));
    }
}
