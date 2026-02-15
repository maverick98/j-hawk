/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawkframework.hawk.rest;

/**
 *
 * @author reemeeka
 */
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.hawkframework.hawk.rest.service.IHawkRestInterpreterService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class HawkRestController {

    @Autowired
    private IHawkRestInterpreterService hawkRestInterpreterService;

    @GetMapping("/health")
    public String home() {
        return "? Hawk REST is running. Version 26.03";
    }

    @GetMapping("/version")
    public String version() {
        return "26.03";
    }

    @PostMapping("/run")
    public String run(@RequestBody String hawkCode) throws Exception {
        return hawkRestInterpreterService.interpret(hawkCode);

    }

    @PostMapping("/compile")
    public String compile(@RequestBody String hawkCode) throws Exception {
        return hawkRestInterpreterService.compile(hawkCode);

    }

}
