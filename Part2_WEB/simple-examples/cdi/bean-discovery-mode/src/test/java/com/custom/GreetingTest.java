package com.custom;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by olga on 06.04.15.
 */
@RunWith(Arquillian.class)
public class GreetingTest {
    @Deployment
    public static Archive<?> deploy() {
        return ShrinkWrap.create(JavaArchive.class)
                // Annotated.class - for 'annotated' mode
                .addClasses(Greeting.class, SimpleGreeting.class)//, AnnotatedGreeting.class)
                .addAsManifestResource("beans.xml");
    }

// --------------------------------------------------------------------------------
//    for bean-discovery-mode="all"
//    in this case all beans are managed by Weld
    @Inject
    Greeting bean;

    @Test
    public void should_all_bean_be_injected() throws Exception {
        assertThat(bean, is(notNullValue()));
        assertThat(bean, instanceOf(SimpleGreeting.class));
    }


// --------------------------------------------------------------------------------
//    for bean-discovery-mode="none"
/*    @Inject
    BeanManager beanManager;

    @Test
    public void should_none_bean_be_injected() throws Exception {
        // Cannot try to inject the bean because it would fail at deployment time (in WildFly 8)
        Set<Bean<?>> beans = beanManager.getBeans(Greeting.class);
        assertThat(beans, is(empty()));
    }
*/
// --------------------------------------------------------------------------------
//    for bean-discovery-mode="annotated"

/*    @Inject
    Greeting bean;

    @Test
    public void should_bean_be_injected() throws Exception {
        assertThat(bean, is(CoreMatchers.notNullValue()));
    }

    @Test
    public void should_bean_be_annotated() throws Exception {
        // because AnnotatedGreeting is annotated (scope)
        assertThat(bean, instanceOf(AnnotatedGreeting.class));
    }
*/
}