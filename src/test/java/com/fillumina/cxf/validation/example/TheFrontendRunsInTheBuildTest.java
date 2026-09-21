package com.fillumina.cxf.validation.example;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

/**
 * What the build generated, read from the build itself.
 *
 * <p>The generated sources are compiled by the same build, so a frontend that writes something that
 * does not compile fails here before this test runs. What the test adds is that the interface is
 * there and carries the annotation: the plugin was found, the option was read, and the annotation
 * landed where the option asked.
 */
class TheFrontendRunsInTheBuildTest {

    private static final Path INTERFACE =
            Path.of("target", "generated-sources", "cxf", "com", "example", "weather",
                    "WeatherServicePortType.java");

    @Test
    void theGeneratedInterfaceCarriesTheAnnotation() throws Exception {
        assertTrue(Files.exists(INTERFACE), "the cxf-codegen-plugin wrote nothing at " + INTERFACE);

        String generated = Files.readString(INTERFACE);
        assertTrue(generated.contains("import jakarta.validation.Valid;"), generated);

        int signature = generated.indexOf("getWeather(");
        assertTrue(signature > 0, generated);
        assertTrue(generated.substring(0, signature).contains("@Valid"),
                "the method carries the annotation: " + generated);
        assertTrue(generated.substring(signature, generated.indexOf(");", signature))
                        .contains("@Valid"),
                "the parameter carries the annotation: " + generated);
    }
}
