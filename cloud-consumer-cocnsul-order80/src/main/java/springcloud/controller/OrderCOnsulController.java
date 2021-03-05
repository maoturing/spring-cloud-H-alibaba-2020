package springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author mao  2021/2/25 2:09
 */
@RestController
public class OrderCOnsulController {

    // 自动去consul注册中心去查找PAYMENT服务, 调用PAYMENT服务8006
    public static final String PAYMENT_URL = "http://consul-provider-payment";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/consul")
    public String paymentInfo() {
        String result = restTemplate.getForObject(PAYMENT_URL + "/payment/consul", String.class);
        return result;
    }
}
