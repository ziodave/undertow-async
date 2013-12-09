package org.example.undertow;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.servlet.DispatcherServlet;


/**
 * A server implementation.
 */
public class JettyServer {

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
    public JettyServer(final String contextPath, final String deploymentName, final String servletName, final String contextConfigLocation, final String mapping, final String host, final Integer port) {

        try {

            final ServletHolder servletHolder = new ServletHolder(servletName, DispatcherServlet.class);
            servletHolder.setDisplayName(deploymentName);
            servletHolder.setInitParameter("contextConfigLocation", contextConfigLocation);
            servletHolder.setInitOrder(0);
            servletHolder.setAsyncSupported(true);
            org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server();
            ServerConnector connector = new ServerConnector(server);
            connector.setHost(host);
            connector.setPort(port);
            server.setConnectors(new Connector[]{connector});
            ServletContextHandler context = new ServletContextHandler();
            context.setContextPath(contextPath);
            context.addServlet(servletHolder, mapping);
            HandlerCollection handlers = new HandlerCollection();
            handlers.setHandlers(new Handler[]{context, new DefaultHandler()});
            server.setHandler(handlers);
            server.start();
            server.join();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
