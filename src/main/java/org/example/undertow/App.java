package org.example.undertow;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    /**
     * App entry point.
     * @param args
     */
    public static void main(String[] args) {

        // initialize the Spring context.
        final ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

    }

}
