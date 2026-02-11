
import java.util.Map;
import org.hawkframework.hawk.rest.service.IHawkRestInterpreterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopoMindController {

    @Autowired
    private IHawkRestInterpreterService interpreter;

    @PostMapping("/executeHawk")
    public Map<String, Object> execute(@RequestBody Map<String, String> request) {
        System.out.println("Hurray!!!");
        long start = System.currentTimeMillis();

        try {
            String code = request.get("code");

            if (code == null || code.isBlank()) {
                throw new RuntimeException("Missing Hawk DSL code.");
            }

            System.out.println("\n================ GENERATED HAWK CODE ================\n");
            System.out.println(code);
            System.out.println("\n=====================================================\n");

            //  Actual DSL Execution
            Object result = interpreter.interpret(code);

            long latency = System.currentTimeMillis() - start;

            return Map.of(
                    "tool_name", "executeHawk",
                    "tool_version", "1.0",
                    "status", "success",
                    "output", Map.of("result", result),
                    "error", null,
                    "latency_ms", latency,
                    "stability_signal", 1.0
            );

        } catch (Exception e) {

            long latency = System.currentTimeMillis() - start;

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
