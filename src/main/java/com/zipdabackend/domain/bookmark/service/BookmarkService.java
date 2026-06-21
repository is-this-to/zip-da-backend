package com.zipdabackend.domain.bookmark.service;

import com.zipdabackend.domain.bookmark.entity.Bookmark;
import com.zipdabackend.domain.bookmark.mapper.BookmarkMapper;
import com.zipdabackend.domain.bookmark.request.BookmarkCreateRequest;
import com.zipdabackend.domain.bookmark.response.BookmarkCardResponse;

import com.zipdabackend.domain.bookmark.response.BookmarkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {
  private final BookmarkMapper bookmarkMapper;

  public BookmarkResponse toggleBookmark(BookmarkCreateRequest bookmarkCreateRequest) {

    Bookmark existingBookmark =
        bookmarkMapper.findByUserIdAndPropertyId(bookmarkCreateRequest);
    boolean isFavorite;

    if (existingBookmark != null) {
      bookmarkMapper.deleteByUserIdAndPropertyId(bookmarkCreateRequest);
      isFavorite = false;
    } else {
      Bookmark bookmark = Bookmark.builder()
          .userId(bookmarkCreateRequest.userId())
          .propertyId(bookmarkCreateRequest.propertyId())
          .build();

      bookmarkMapper.insertBookmark(bookmark);
      isFavorite = true;
    }
    long favoriteCount =
        bookmarkMapper.countByPropertyId(bookmarkCreateRequest.propertyId());

    return new BookmarkResponse(
        bookmarkCreateRequest.propertyId(),
        isFavorite,
        favoriteCount
    );
  }
  public List<BookmarkCardResponse> getUserBookmarkCards(Long userId) {
    return bookmarkMapper.findCardsByUserId(userId);
  }
}