package demo;

import demo.model.Account;
import demo.model.AccountRepository;
import demo.model.Bookmark;
import demo.model.BookmarkRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class DemoApplication {
    @Bean
    CommandLineRunner init(AccountRepository accountRepository,
                           BookmarkRepository bookmarkRepository) {
        return (evt) -> Arrays.asList(
                "first,second,third".split(","))
                .forEach(
                        a -> {
                            Account account = accountRepository.save(new Account("123", a));
                            bookmarkRepository.save(new Bookmark(account,
                                    "http://localhost:8080/" + a + "/bookmarks", "A description, account: " + account.getUsername()));
                            bookmarkRepository.save(new Bookmark(account,
                                    "http://localhost:8080/" + a + "/bookmarks", "A description, account: " + account.getUsername()));
                        });
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
