/*
package gherkin_junit5;

import eu.venthe.pipeline.orchestrator.Application;
import gherkin_junit5.annotations.Expression;
import gherkin_junit5.annotations.StepDefinition;
import glue.FeatureFileLoader;
import io.cucumber.cucumberexpressions.CucumberExpression;
import io.cucumber.cucumberexpressions.ParameterTypeRegistry;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
// TODO: Rewrite as extension
@SpringBootTest(classes = {Application.class, GherkinConfiguration.class})
class CucumberTest {
    @Autowired
    ApplicationContext applicationContext;

    @TestFactory
    Iterator<DynamicTest> name() throws IOException {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(StepDefinition.class);
        beansWithAnnotation.forEach((a, b) -> {
            var aClass = b.getClass();
            var declaredMethods = aClass.getDeclaredMethods();
            Set<Method> collect = Arrays.stream(declaredMethods).filter(e -> Arrays.stream(e.getDeclaredAnnotations())
                            .anyMatch(ann -> ann.annotationType().isAssignableFrom(Expression.class)))
                    .collect(Collectors.toSet());

            System.out.println();
        });

        return new FeatureFileLoader().list().stream()
                .filter(envelope -> envelope.getPickle().isPresent())
                .map(e -> Map.entry(e, e.getPickle().orElseThrow()))
                .map(e -> DynamicTest.dynamicTest(
                        e.getValue().getName(),
                        () -> e.getValue().getSteps().stream()
                                .map(p -> mapToStep(p.getText(), Locale.of(e.getValue().getLanguage())))
                                .forEach(Runnable::run)
                ))
                .iterator();
    }

    private Runnable mapToStep(String text, Locale locale) {
        CucumberExpression cucumberExpression = new CucumberExpression(text, new ParameterTypeRegistry(locale));
        return null;
    }
}
*/
