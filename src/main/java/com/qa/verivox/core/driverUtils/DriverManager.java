package com.qa.verivox.core.driverUtils;

import com.qa.verivox.core.conf.DriverConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class DriverManager {
    private List<Driver> drivers = Collections.synchronizedList(new ArrayList<>());
    private DriverConfig config;

    public DriverManager(DriverConfig config) {
        this.config = config;
    }

    public Driver getDriver() {
        return getDriver(config.getName());
    }

    public Driver getDriver(String name) {
        Driver driver = DriverFactory.fromValue(name).getDriver(config);
        drivers.add(driver);
        return driver;
    }

//    private static class SingletonHelper {
//        private static final DriverManager INSTANCE = Injector.getInjector().getInstance(DriverManager.class);
//
//        static {
//            Runtime.getRuntime().addShutdownHook(new Thread(INSTANCE::quitAllDrivers));
//        }
//    }
//
//    public static DriverManager getInstance() {
//        return SingletonHelper.INSTANCE;
//    }

    public void quitAllDrivers() {
        drivers.forEach((Driver::quit));
        drivers.clear();
    }


}


