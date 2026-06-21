package com.zipdabackend.domain.bookmark.controller;

import com.zipdabackend.domain.bookmark.request.BookmarkCreateRequest;
import com.zipdabackend.domain.bookmark.response.BookmarkCardResponse;
import com.zipdabackend.domain.bookmark.response.BookmarkResponse;
import com.zipdabackend.domain.bookmark.service.BookmarkService;
import com.zipdabackend.global.response.GlobalResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookmarkController {
  private final BookmarkService bookmarkService;

  @PostMapping("/bookmarks")
  public ResponseEntity<GlobalResponse<BookmarkResponse>> toggleBookmark(
      @AuthenticationPrincipal Claims claims,
      @RequestBody BookmarkCreateRequest bookmarkCreateRequest
  ) {

    Long userId = Long.parseLong(claims.getSubject());

    BookmarkResponse bookmarkResponse =
        bookmarkService.toggleBookmark(userId, bookmarkCreateRequest);

    return ResponseEntity.ok(
        GlobalResponse.<BookmarkResponse>builder()
            .code("00")
            .message("찜 상태가 변경되었습니다.")
            .data(bookmarkResponse)
            .build()
    );
  }
  //SecurityUrlRegistry에 등록된주소
  @GetMapping("/users/me/bookmarks")
  public ResponseEntity<GlobalResponse<List<BookmarkCardResponse>>> getUserBookmarkCards(
      @AuthenticationPrincipal Claims claims
  ) {
    Long userId = Long.parseLong(claims.getSubject());  // JWT subject에서 로그인한 userId 꺼내기

    List<BookmarkCardResponse> bookmarkCards =
        bookmarkService.getUserBookmarkCards(userId);
    // Service에게 userId가 찜한 카드 목록 조회를 맡김


    return ResponseEntity.ok(
        GlobalResponse.<List<BookmarkCardResponse>>builder()
            .code("00")
            .message("마이페이지 찜 목록 조회 성공")
            .data(bookmarkCards)
            .build()
    );
  }
  }