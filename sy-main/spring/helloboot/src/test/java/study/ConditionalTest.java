package study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

public class ConditionalTest {

    @Test
    void conditional() {
        // true
        ApplicationContextRunner applicationContextRunner = new ApplicationContextRunner();
        applicationContextRunner.withUserConfiguration(Config1.class).run(context -> {
            Assertions.assertThat(context).hasSingleBean(MyBean.class);
            Assertions.assertThat(context).hasSingleBean(Config1.class);

        });
//        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
//        ac.register(Config1.class);
//        ac.refresh();
//
//        MyBean bean = ac.getBean(MyBean.class);
        //false
        new ApplicationContextRunner().withUserConfiguration(Config2.class).run(context -> {
            Assertions.assertThat(context).doesNotHaveBean(MyBean.class);
            Assertions.assertThat(context).doesNotHaveBean(Config2.class);
        });
//        AnnotationConfigApplicationContext ac2 = new AnnotationConfigApplicationContext();
//        ac.register(Config1.class);
//        ac.refresh();
//
//        MyBean bean2 = ac.getBean(MyBean.class);
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional(BooleanCondition.class)
    @interface BooleanConditional {
        boolean value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional(TrueCondition.class)
    @interface TrueConditional {}

    @Configuration
    @BooleanConditional(true)
    static class Config1 {
        @Bean
        MyBean Bean1() {
            return new MyBean();
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional(FalseCondition.class)
    @interface FalseConditional {}

    @Configuration
    @BooleanConditional(false)
    static class Config2 {
        @Bean
        MyBean Bean2() {
            return new MyBean();
        }
    }

    static class MyBean {};

    static class TrueCondition implements Condition {

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return true;
        }
    }

    static class FalseCondition implements Condition {

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return false;
        }
    }

    static class BooleanCondition implements Condition {

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(BooleanConditional.class.getName());
            return (Boolean) annotationAttributes.get("value");
        }
    }

}
