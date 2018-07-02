package com.shengwu.cloud.survey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 服务启动。
 * Created by shengwu on 2017/9/14.
 */
public class Application {
    public static final String SHUTDOWN_HOOK_KEY = "dubbo.shutdown.hook";
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    private static volatile boolean running = true;

    /**
     * 启动方法。
     *
     * @param args args
     */
    public static void main(String[] args) {
        try {
            ClassPathXmlApplicationContext context
                    = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/application-context.xml"});
            logger.info("application-context.xml has been loaded...");
            context.start();
            logger.info("Spring Context started...");

            if ("true".equals(System.getProperty(SHUTDOWN_HOOK_KEY))) {
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    public void run() {
                        synchronized (Application.class) {
                            running = false;
                            Application.class.notify();
                        }
                    }
                });
            }

            System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date())
                    + " Dubbo service server started!");
        } catch (RuntimeException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            System.exit(1);
        }

        synchronized (Application.class) {
            while (running) {
                try {
                    Application.class.wait();
                } catch (Throwable e) {
                    // Do Nothing.
                }
            }
        }
    }
}
