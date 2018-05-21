package com.codecool.webroutes;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Test {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String path = t.getRequestURI().getPath();
            String requestMethod = t.getRequestMethod();
            System.out.println(t.getRequestHeaders().entrySet());
            Method[] routeMethods = Route.class.getMethods();
            for (Method method : routeMethods) {
                WebRoute annotation = method.getAnnotation(WebRoute.class);
                if (annotation.path().equals(path) && annotation.method().toString().equals(requestMethod)) {
                    try {
                        invokeMethod(method, t);
                    } catch (InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private void invokeMethod(Method method, HttpExchange t) throws IOException, InvocationTargetException, IllegalAccessException {
            Route route = new Route();

            String response = (String) method.invoke(route);
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}


