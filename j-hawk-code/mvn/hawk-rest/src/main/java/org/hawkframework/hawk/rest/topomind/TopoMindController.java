package org.hawkframework.hawk.rest.topomind;

import org.hawkframework.hawk.rest.service.IHawkRestInterpreterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class TopoMindController {

    @Autowired
    private IHawkRestInterpreterService interpreter;

    @PostMapping("/executeHawk")
    public Map<String, Object> execute(@RequestBody Map<String, String> request) {

        String code = request.get("code");

        System.out.println("\n================ GENERATED HAWK CODE ================\n");
        System.out.println(code);
        System.out.println("\n=====================================================\n");

        return Map.of(
                "tool_name", "executeHawk",
                "tool_version", "1.0",
                "status", "success",
                "output", Map.of("code", code),
                "error", null,
                "latency_ms", 0,
                "stability_signal", 1.0
        );
    }
}
