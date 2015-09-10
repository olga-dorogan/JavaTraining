package demo.rest;

import demo.hateoas.BookmarkResource;
import demo.model.AccountRepository;
import demo.model.Bookmark;
import demo.model.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by olga on 17.07.15.
 */
@RestController
@RequestMapping("/{userId}/bookmarks")
public class BookmarkRestController {
    private final BookmarkRepository bookmarkRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public BookmarkRestController(BookmarkRepository bookmarkRepository,
                                  AccountRepository accountRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.accountRepository = accountRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> save(@PathVariable String userId, @RequestBody Bookmark bookmarkBody) {
        this.validateUser(userId);
        return this.accountRepository
                .findByUsername(userId)
                .map(account -> {
                    Bookmark bookmark = bookmarkRepository.save(new Bookmark(account,
                            bookmarkBody.getUri(), bookmarkBody.getDescription()));
                    HttpHeaders httpHeaders = new HttpHeaders();
                    Link forOneBookmark = new BookmarkResource(bookmark).getLink("self");
                    httpHeaders.setLocation(URI.create(forOneBookmark.getHref()));
                    return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
                }).get();

    }

    @RequestMapping(value = "/{bookmarkId}", method = RequestMethod.GET)
    public BookmarkResource readBookmark(@PathVariable String userId, @PathVariable Long bookmarkId) {
        this.validateUser(userId);
        return new BookmarkResource(this.bookmarkRepository.findOne(bookmarkId));
    }

    @RequestMapping(method = RequestMethod.GET)
    public Resources<BookmarkResource> readBookmarks(@PathVariable String userId) {
        this.validateUser(userId);
        List<BookmarkResource> bookmarkResources = this.bookmarkRepository.findByAccountUsername(userId).stream()
                .map(BookmarkResource::new)
                .collect(Collectors.toList());
        return new Resources<>(bookmarkResources);
    }

    private void validateUser(String userId) {
        accountRepository.findByUsername(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }
}
