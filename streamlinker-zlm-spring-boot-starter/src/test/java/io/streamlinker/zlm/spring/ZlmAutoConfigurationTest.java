package io.streamlinker.zlm.spring;

import io.streamlinker.zlm.api.ZlmClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class ZlmAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(JacksonAutoConfiguration.class, ZlmAutoConfiguration.class));

    @Test
    void shouldAutoConfigureZlmClientWhenApiUrlPresent() {
        contextRunner
                .withPropertyValues("streamlinker.zlm.api-url=http://localhost:8000")
                .run(context -> assertThat(context).hasSingleBean(ZlmClient.class));
    }
}
