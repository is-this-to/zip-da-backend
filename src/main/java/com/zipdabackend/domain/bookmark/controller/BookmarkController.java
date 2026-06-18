package com.zipdabackend.domain.bookmark.controller;

import com.zipdabackend.domain.bookmark.request.BookmarkCreateRequest;
import com.zipdabackend.domain.bookmark.entity.Bookmark;
import com.zipdabackend.domain.bookmark.response.BookmarkResponse;
import com.zipdabackend.domain.bookmark.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping("/bookmarks")
    public boolean toggleBookmark(@RequestBody BookmarkCreateRequest req) {
        return bookmarkService.toggleBookmark(
                req.userId(),
                req.propertyId()
        );
    }

    @GetMapping("/bookmarks")
    public List<Bookmark> getBookmarks(@RequestParam Long userId) {
        return bookmarkService.getUserBookmarks(userId);
    }
}