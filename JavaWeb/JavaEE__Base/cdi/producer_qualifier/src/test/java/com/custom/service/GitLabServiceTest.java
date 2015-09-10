package com.custom.service;

import com.custom.system.PropertyFileResolver;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by olga on 04.08.15.
 */
@RunWith(Arquillian.class)
public class GitLabServiceTest {
    @Deployment
    public static Archive<?> deploy() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(GitLabService.class.getPackage())
                .addPackage(PropertyFileResolver.class.getPackage())
                .addAsManifestResource("beans.xml", "beans.xml");
    }

    @Inject
    private GitLabService gitLabService;

    @Test
    public void should_all_bean_be_injected() throws Exception {
        assertThat(gitLabService, is(notNullValue()));
    }

}