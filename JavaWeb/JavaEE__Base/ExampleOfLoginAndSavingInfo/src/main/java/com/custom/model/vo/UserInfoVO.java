package com.custom.model.vo;

import com.custom.model.entity.Task;
import com.custom.model.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 15.05.15.
 */
public class UserInfoVO {
    private Integer id;

    private String firstName;
    private String lastName;
    private String email;
    private String clientId;

    public UserInfoVO(String familyName, String name, String email, String clientId) {
        this.firstName = name;
        this.lastName = familyName;
        this.email = email;
        this.clientId = clientId;
    }

    private List<Boolean> tasksStates;

    public List<Boolean> getTasksStates() {
        return tasksStates;
    }

    public void setTasksStates(List<Boolean> tasksStates) {
        this.tasksStates = tasksStates;
    }



    public UserInfoVO(UserInfo userInfo) {
        this.id = userInfo.getId();
        this.firstName = userInfo.getFirstName();
        this.lastName = userInfo.getLastName();
        this.email = userInfo.getEmail();
        this.clientId = userInfo.getClientId();
        List<Task> tasksList = userInfo.getTasks();
        this.tasksStates = new ArrayList<>(tasksList.size());
        for (Task tasks : tasksList) {
            this.tasksStates.add(tasks.isState());
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
