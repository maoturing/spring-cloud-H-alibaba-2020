package springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author mao  2021/2/25 2:11
 */
@Configuration
public class ApplicationContextConfig {
    /**
     * 注入 RestTemplate bean
     * @return
     */
    @LoadBalanced   // 开启RestTemplate负载均衡, 也使得可以通过微服务名称来调用服务
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
