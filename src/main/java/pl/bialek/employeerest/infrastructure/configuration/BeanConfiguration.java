package pl.bialek.employeerest.infrastructure.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    /**
     * Bean that configures and returns a customized {@link ObjectMapper} instance.
     * <p>
     * The customization includes:
     * <ul>
     *     <li>Registration of the {@link JavaTimeModule} to handle Java 8 time types.</li>
     *     <li>Registration of the {@link Jdk8Module} to handle other Java 8 types, such as {@link Optional}.</li>
     *     <li>Configuration to not write date keys as timestamps, ensuring ISO8601 string output for date types.</li>
     *     <li>Configuration to not fail on unknown properties during deserialization, adding flexibility to JSON parsing.</li>
     *     <li>Setting serialization inclusion to ignore null fields, reducing the size of the serialized JSON.</li>
     * </ul>
     *
     * @return A configured {@link ObjectMapper} instance.
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .registerModule(new Jdk8Module())
                .configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    }


}
