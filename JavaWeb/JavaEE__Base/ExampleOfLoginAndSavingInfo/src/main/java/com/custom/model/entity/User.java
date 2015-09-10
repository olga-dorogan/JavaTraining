package com.custom.model.entity;

import com.custom.model.vo.UserInfoVO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 15.05.15.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    private String firstName;
    private String lastName;
    private String email;
    private String clientId;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

    public User() {

    }

    public User(String firstName, String lastName, String email, String clientId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.clientId = clientId;
    }

    public User(UserInfoVO userInfoVO) {
        this.id = userInfoVO.getId();
        this.firstName = userInfoVO.getFirstName();
        this.lastName = userInfoVO.getLastName();
        this.email = userInfoVO.getEmail();
        this.clientId = userInfoVO.getClientId();
        List<Boolean> tasksStates = userInfoVO.getTasksStates();
        if (tasksStates != null) {
            for (int i = 0; i < tasksStates.size(); i++) {
                if (this.tasks.size() <= i) {
                    this.tasks.add(new Task(tasksStates.get(i)));
                } else {
                    this.tasks.get(i).setState(tasksStates.get(i));
                }
            }
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

    @Override
    public String toString() {
        return "UserInfo {" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        return !(email != null ? !email.equals(user.email) : user.email != null);

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
