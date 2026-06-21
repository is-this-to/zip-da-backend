package com.zipdabackend.domain.bookmark.controller;

import com.zipdabackend.domain.bookmark.request.BookmarkCreateRequest;
import com.zipdabackend.domain.bookmark.response.BookmarkResponse;
import com.zipdabackend.domain.bookmark.service.BookmarkService;
import com.zipdabackend.global.response.GlobalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookmarkController {
  private final BookmarkService bookmarkService;

  @PostMapping("/bookmarks/toggle")
  public ResponseEntity<GlobalResponse<BookmarkResponse>> toggleBookmark(
      @RequestBody BookmarkCreateRequest bookmarkCreateRequest
  ) {
    BookmarkResponse bookmarkResponse =
        bookmarkService.toggleBookmark(bookmarkCreateRequest);
    return ResponseEntity.ok(
        GlobalResponse.<BookmarkResponse>builder()
            .code("00")
            .message("찜 상태가 변경되었습니다.")
            .data(bookmarkResponse)
            .build()
    );
  }
}