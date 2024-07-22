package com.qa.verivox.core.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "browser")
public class BrowserConfig {

    private String name;
    private boolean Headless;
    private boolean DriverService;
    private boolean GetConsoleLog;
    private boolean Maximize;
    private String pageLoadingStrategy;
    private long implicitlyWait;
    private long explicitlyWait;
    private long pageLoadTimeout;
    private String scriptTimeout;
    private String chromePath;
    private String firFoxPath;
    private String appUrl;

}
