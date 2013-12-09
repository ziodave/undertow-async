package org.example.undertow.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.Callable;

@Controller
@RequestMapping("/ping")
public class PingController {

    @RequestMapping(method= RequestMethod.GET)
    public @ResponseBody String ping() {
        return "pong";
    }

    @RequestMapping(value="/async", method= RequestMethod.GET)
    public @ResponseBody Callable<String> pingAsync() {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "pong";
            }
        };
    }
}
