package org.example.undertow;

import io.undertow.Undertow;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ListenerInfo;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletException;

import static io.undertow.servlet.Servlets.deployment;
import static io.undertow.servlet.Servlets.servlet;
import static io.undertow.servlet.Servlets.defaultContainer;

/**
 * A server implementation.
 */
public class UndertowServer {

    /**
     * Initialize, configure and start a server implementation.
     *
     * @param contextPath
     * @param deploymentName
     * @param servletName
     * @param contextConfigLocation
     * @param mapping
     * @param host
     * @param port
     */
    public UndertowServer(final String contextPath, final String deploymentName, final String servletName, final String contextConfigLocation, final String mapping, final String host, final Integer port) {


        try {

            final DeploymentInfo servletBuilder = deployment()
                    .setClassLoader(App.class.getClassLoader())
                    .setContextPath(contextPath)
                    .setDeploymentName(deploymentName)
                    .setMajorVersion(3)
                    .setMinorVersion(0)
                    .addInitParameter("contextConfigLocation", "classpath:WEB-INF/root-context.xml")
                    .addListener(new ListenerInfo(ContextLoaderListener.class))
                    .addServlet(
                            servlet(servletName, DispatcherServlet.class)
                                    .addInitParam("contextConfigLocation", contextConfigLocation)
                                    .addMapping(mapping)
                                    .setLoadOnStartup(1)
                                    .setAsyncSupported(true));

            final DeploymentManager manager = defaultContainer().addDeployment(servletBuilder);
            manager.deploy();

            final Undertow server = Undertow.builder()
                    .addListener(port, host)
                    .setHandler(manager.start())
                    .build();

            server.start();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
