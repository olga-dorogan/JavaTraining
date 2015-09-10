package com.custom.service;

import com.custom.system.ApplicationProperty;
import com.custom.system.GitLabPropKeys;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * Created by olga on 04.08.15.
 */
@Dependent
public class GitLabService {
    @Inject
    @ApplicationProperty(name = "gitlab_host")
    private String gitLabHost;
    @Inject
    @ApplicationProperty(name = GitLabPropKeys.PROP_LOGIN)
    private String gitLabLogin;
    @Inject
    @ApplicationProperty(name = GitLabPropKeys.PROP_PSWRD)
    private String gitLabPswrd;
    @Inject
    @ApplicationProperty(name = GitLabPropKeys.PROP_EMAIL)
    private String gitLabEmail;

    public GitLabService() {
    }

    @PostConstruct
    public void init() {
        System.out.println("Read properties: ");
        System.out.println("====> GitLab host: " + gitLabHost);
        System.out.println("====> GitLab login: " + gitLabLogin);
        System.out.println("====> GitLab email: " + gitLabEmail);
        System.out.println("====> GitLab password: " + gitLabPswrd);
    }
}
