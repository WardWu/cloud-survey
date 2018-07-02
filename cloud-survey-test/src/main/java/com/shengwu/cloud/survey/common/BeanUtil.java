package com.shengwu.cloud.survey.common;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.shengwu.cloud.survey.util.SurveyClientFactory;

import java.util.HashMap;
import java.util.Map;

public class BeanUtil {
    public static Map<String, ReferenceConfig> SERVICE_MAP = new HashMap();
    private static ApplicationConfig application;
    private static RegistryConfig registry;
    private static ConsumerConfig consumer;

    public BeanUtil() {
    }

    public static <T> T getBean(Class<T> clazz, String id) {
        SurveyClientFactory.initHeader();
        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        return cache.get(getReference(clazz, id));
    }

    public static <T> ReferenceConfig<T> getReference(Class<T> clazz, String id) {
        if (SERVICE_MAP.get(id) != null) {
            return (ReferenceConfig) SERVICE_MAP.get(id);
        } else {
            ReferenceConfig<T> reference = new ReferenceConfig();
            if (application == null) {
                application = new ApplicationConfig();
                application.setName(SurveyClientFactory.getAppName());
            }

            if (registry == null) {
                registry = new RegistryConfig();
                registry.setAddress(SurveyClientFactory.getServerUrl());
            }

            if (consumer == null) {
                consumer = new ConsumerConfig();
                consumer.setTimeout(Integer.valueOf(30000));
            }

            reference.setApplication(application);
            reference.setRegistry(registry);
            reference.setConsumer(consumer);
            reference.setProtocol("dubbo");
            reference.setInterface(clazz);
            reference.setId(id);
            SERVICE_MAP.put(id, reference);
            return reference;
        }
    }
}