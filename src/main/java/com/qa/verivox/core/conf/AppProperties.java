package com.qa.verivox.core.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "test.app")
public class AppProperties {

    private String testEnv;
    private String appUrl;
    private String appPassword;
    private String appUserName;
    private String dataSourcePath;

    private String baseUrl;
    private String token;

}
