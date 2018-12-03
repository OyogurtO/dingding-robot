package com.github.oyogurto.robot;

import com.dingtalk.chatbot.DingtalkChatbotClient;
import com.dingtalk.chatbot.SendResult;
import com.dingtalk.chatbot.message.TextMessage;

import java.util.Arrays;
import java.util.Date;

/**
 * @author zhoujingchun
 * created on 2018/7/5
 * description: 吃饭回家群机器人
 */
public class DingClient {
    private String webHook = "https://oapi.dingtalk.com/robot/send?access_token=7e447adb2dababec318d5952c1cc209ffc433b53609b9541ef146582fafc9c61";
    private DingtalkChatbotClient client;

    public DingClient() {
        client = new DingtalkChatbotClient();
    }

    /**
     * 提醒订饭
     * @return 返回值
     */
    public SendResult notifyDingFan() throws Exception{
        System.out.println(new Date());
        TextMessage msg = new TextMessage("@17621612606快订饭");
        msg.setAtMobiles(Arrays.asList("17621612606"));
        msg.setIsAtAll(false);
        return client.send(webHook,msg);
    }
}
