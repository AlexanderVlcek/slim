package myPackage.singeltons;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;

public enum ConfigSource {

    INSTANCE;

    public Config config;

    ConfigSource() {
        Config basic = ConfigFactory.load();
        config = System.getProperty("config") != null
                ? ConfigFactory.parseFile(new File(System.getProperty("config"))).withFallback(basic).resolve()
                : basic;
    }
}

