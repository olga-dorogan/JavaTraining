package com.custom.random;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by olga on 06.04.15.
 */
@RunWith(Arquillian.class)
public class RandomFactoryTest {
    @Deployment
    public static Archive<?> deploy() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RandomFactory.class.getPackage())
                .addAsManifestResource("beans.xml");
    }

    @Inject
    @Random
    Instance<Integer> randomInst;

    @Test
    public void should_generate_at_least_half_dif_nums() {
        assertThat(randomInst, notNullValue());
        final int NUMS = 10;
        Set<Integer> generatedNums = new HashSet<>();
        for (int i = 0; i < NUMS; i++) {
            generatedNums.add(randomInst.get());
        }
        assertThat(generatedNums.size(), not(lessThan(NUMS / 2)));
    }
}