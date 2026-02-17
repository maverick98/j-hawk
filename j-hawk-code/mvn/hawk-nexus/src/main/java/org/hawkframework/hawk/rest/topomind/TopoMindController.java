package org.hawkframework.hawk.rest.topomind;

import org.hawkframework.hawk.rest.service.IHawkRestInterpreterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@RestController
public class TopoMindController {

    private static final Logger logger =
            LoggerFactory.getLogger(TopoMindController.class);

    @Autowired
    private IHawkRestInterpreterService interpreter;

    

    // --------------------------------------------------
    // Hawk Execution Endpoint
    // --------------------------------------------------
    @PostMapping("/executeHawk")
    public Map<String, Object> execute(@RequestBody Map<String, String> request) {

        long start = System.currentTimeMillis();

        try {
            String code = request.get("hawk_dsl");


            if (code == null || code.isBlank()) {
                throw new RuntimeException("Missing Hawk DSL code.");
            }

            logger.info("Executing Hawk DSL:\n{}", code);

            Object result = interpreter.interpret(code);

            String safeResult = result != null ? result.toString() : "null";
            System.out.println("safeResult is "+safeResult);
            long latency = System.currentTimeMillis() - start;

            return Map.of(
                    "tool_name", "executeHawk",
                    "tool_version", "1.0",
                    "status", "success",
                    "output", Map.of("result", safeResult),
                    "error", null,
                    "latency_ms", latency,
                    "stability_signal", 1.0
            );

        } catch (Exception e) {

            long latency = System.currentTimeMillis() - start;

            logger.error("Hawk execution failed", e);

            return Map.of(
                    "tool_name", "executeHawk",
                    "tool_version", "1.0",
                    "status", "failure",
                    "output", null,
                    "error", e.getMessage(),
                    "latency_ms", latency,
                    "stability_signal", 0.0
            );
        }
    }
}
