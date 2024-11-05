package eu.venthe.platform.config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import eu.venthe.platform.shared_kernel.dynamic_value.DynamicValue;
import eu.venthe.platform.shared_kernel.dynamic_value.IntegerDynamicValue;
import eu.venthe.platform.shared_kernel.dynamic_value.StringDynamicValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;

@Configuration
public class JacksonConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilder dynamicValueJacksonModule() {
        return new Jackson2ObjectMapperBuilder().modules(
                new DynamicValueMapperModule(),
                new Jdk8Module()
        );
    }

    private static class DynamicValueMapperModule extends SimpleModule {
        public DynamicValueMapperModule() {
            addDeserializer(DynamicValue.class, new DynamicValueDeserializer());
        }
    }

    private static class DynamicValueDeserializer extends JsonDeserializer<DynamicValue> {

        @Override
        public DynamicValue deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonToken token = p.currentToken();

            if (token == JsonToken.VALUE_STRING) {
                return new StringDynamicValue(p.getText());
            } else if (token == JsonToken.VALUE_NUMBER_INT) {
                return new IntegerDynamicValue(p.getValueAsInt());
            }

            throw new InvalidFormatException(p, "Invalid DynamicValue", token, DynamicValue.class);
        }
    }
}
