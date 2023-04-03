package com.laudwilliam.keyvaluedatabase.client.http;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestMyService {
    @Test
    void test() {
        MyService myService = new MyService();
        myService.makeGetRequest();
    }
}
