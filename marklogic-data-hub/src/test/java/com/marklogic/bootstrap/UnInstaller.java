package com.marklogic.bootstrap;

import com.marklogic.hub.HubTestBase;
import com.marklogic.hub.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.PostConstruct;

@EnableAutoConfiguration
public class UnInstaller extends HubTestBase {

    private static Logger logger = LoggerFactory.getLogger(UnInstaller.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(new Class[]{UnInstaller.class, ApplicationConfig.class});
        app.setWebApplicationType(WebApplicationType.NONE);
        ConfigurableApplicationContext ctx = app.run(new String[] { "--hubProjectDir=" + PROJECT_PATH });
    }

    @PostConstruct
    public void teardownHub() {
        super.init();
        dataHub.uninstall();
        if (isCertAuth() || isSslRun()) {
            sslCleanup();
        }
        try {
            deleteProjectDir();
        }
        catch(Exception e) {
            logger.warn("Unable to delete the project directory", e);
        }
    }

}