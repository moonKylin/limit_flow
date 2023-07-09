package com.xxy.limit_flow.Controller;

import com.xxy.limit_flow.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/test")
public class Controller {

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/sendReq")
    public String acceptRequest(){
        // 1. 设置限流的键和请求次数
        String key = "requestKey";
        long limit = 5;

        // 2. 增加计数器并获取当前值
        Long count = redisUtil.increment(key, 1);
        System.out.println(count);

        // 3. 获取键的过期时间
        Long expire = redisUtil.getExpire(key);
        System.out.println(expire);

        try {
            // 4. 判断是否触发限流
            if (count > limit && expire > 0) {
                return "请稍后再试"+"--发起请求数："+count+"--限制倒计时："+expire;
            } else {
                // 5. 重置计数器的值和过期时间
                if (expire <= 0) {
                    // 过期时间已失效，重置计数器
                    redisUtil.reset(key, 1, 5);
                }
                // 7. 返回请求成功
                return "请求成功";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "请稍后再试";
        }
    }
}
