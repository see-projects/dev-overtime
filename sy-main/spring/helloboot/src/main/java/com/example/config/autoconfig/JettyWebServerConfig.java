package com.example.config.autoconfig;

import com.example.config.MyAutoConfiguration;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;

@MyAutoConfiguration
@Conditional(JettyWebServerConfig.JettyConditional.class)
public class JettyWebServerConfig {
    @Bean("JettyWebServerConfig")
    public ServletWebServerFactory servletContainer() {
        return new JettyServletWebServerFactory();
    }

    public static class JettyConditional implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return true;
        }
    }
}
