package com.atguigu.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 以REST的风格暴露配置文件
 *
 * @author mao  2021/3/2 18:40
 */
// 当配置中心的yml修改时, 执行post请求 curl -X POST "http://localhost:3355/actuator/refresh"
// 配置文件会自动同步到当前客户端, 访问 http://localhost:3355/configInfo 会发现配置已更新
// 艹, 这个 curl 请求就是重启当前项目 ConfigCenterMain3344
@RefreshScope
@RestController
public class ConfigClientController {
    /**
     * 通过这种方式，可以直接读取ConfigServer中的配置信息
     * 虽然当前项目的yml中没有该信息, 但是在bootstrap中配置了远程配置文件 http://config-3344.com:3344/master/config-dev.yml
     * 去配置中心获取, 配置中心会再次跳转到git, 读取加载config-dev.yml, 这一步日志信息: Located property source:.....https://github.com/maoturing/springcloud-config.git/config-dev.yml
     * 而该配置文件中有  config.info="master branch.........", 就可以注入成功, 然后创建bean configClientController
     * 否则会报错创建bean失败, 无法解析'config.info2' Error creating bean with name 'configClientController': Injection of autowired dependencies failed, Could not resolve placeholder 'config.info2' in value "${config.info2}"
     */
    @Value("${config.info}")
    private String configInfo;

    /**
     * 读取当前项目配置属性 config.info
     * <p>
     * http://localhost:3355/configInfo
     *
     * @return
     */
    @GetMapping("/configInfo")
    public String getConfigInfo() {
        return configInfo;
    }
}
