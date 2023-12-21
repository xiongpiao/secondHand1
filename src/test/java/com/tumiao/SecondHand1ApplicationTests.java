package com.tumiao;

import com.tumiao.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecondHand1ApplicationTests {

    @Test
    void contextLoads() {
        String token = JwtUtils.encode("用户名", "123456");
//        boolean parse = JwtUtils.parse(token);
//        System.out.println(parse);
    }

}
