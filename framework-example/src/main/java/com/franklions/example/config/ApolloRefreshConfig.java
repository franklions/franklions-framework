package com.franklions.example.config;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;


/**
 * @author flsh
 * @version 1.0
 * @date 2019-03-29
 * @since Jdk 1.8
 */
@Configuration
public class ApolloRefreshConfig implements ApplicationContextAware {

    private Logger iotLogger = LoggerFactory.getLogger(ApolloRefreshConfig.class);

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * config change listener for namespace application
     * @param changeEvent
     */
    @ApolloConfigChangeListener("application")
    private void appOnChange(ConfigChangeEvent changeEvent) {

        iotLogger.debug("changeEvent:"+changeEvent.changedKeys().toString());

        if(this.applicationContext != null){
            this.applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));
        }else {

            iotLogger.warn("applicationContext is null.");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
