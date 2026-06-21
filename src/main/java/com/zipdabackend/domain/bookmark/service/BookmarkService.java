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

  public BookmarkResponse toggleBookmark(
    Long userId, BookmarkCreateRequest bookmarkCreateRequest) {
    Long propertyId = bookmarkCreateRequest.propertyId();
    Bookmark bookmark = Bookmark.builder()
        .userId(userId)
        .propertyId(propertyId)
        .build();

    Bookmark existingBookmark =
        bookmarkMapper.findByUserIdAndPropertyId(bookmark);
    boolean isFavorite;

    if (existingBookmark != null) {
      bookmarkMapper.deleteByUserIdAndPropertyId(bookmark);
      isFavorite = false;
    } else {
      bookmarkMapper.insertBookmark(bookmark);
      isFavorite = true;
    }
    long favoriteCount =
        bookmarkMapper.countByPropertyId(propertyId);

    return new BookmarkResponse(
        propertyId,
        isFavorite,
        favoriteCount
    );
  }
  public List<BookmarkCardResponse> getUserBookmarkCards(Long userId) {
    return bookmarkMapper.findCardsByUserId(userId);
  }
}