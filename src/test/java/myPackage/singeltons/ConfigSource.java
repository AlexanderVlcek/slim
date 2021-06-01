package myPackage.singeltons;

import com.typesafe.config.ConfigFactory;

import java.io.File;

public enum ConfigSource {

    INSTANCE;

    public com.typesafe.config.Config config;

    ConfigSource(){
        com.typesafe.config.Config basic = ConfigFactory.load();
        config = System.getProperty("config") != null
                ? ConfigFactory.parseFile(new File(System.getProperty("config"))).withFallback(basic).resolve()
                : basic;
    }


}

