package com.bigeventbackend;

import com.bigeventbackend.utils.Md5Util;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BigeventBackendApplicationTests {

    //写一个测试，测试一下你的项目是否能够正常运行
    @Test
    public void testProjectRunning() {
        String password = "123456";
        System.out.println(Md5Util.getMD5String(password));
        System.out.println(Md5Util.getMD5String(password).equals(Md5Util.getMD5String(password)));
    }
}



