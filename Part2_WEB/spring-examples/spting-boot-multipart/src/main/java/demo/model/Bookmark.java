package demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by olga on 17.07.15.
 */
@Entity
public class Bookmark {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Account account;
    private String uri;
    private String description;

    public Bookmark() {
    }

    public Bookmark(String uri, String description) {
        this.uri = uri;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
