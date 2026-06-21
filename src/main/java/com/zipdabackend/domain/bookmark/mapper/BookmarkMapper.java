package com.zipdabackend.domain.bookmark.mapper;

import com.zipdabackend.domain.bookmark.entity.Bookmark;
import com.zipdabackend.domain.bookmark.request.BookmarkCreateRequest;
import com.zipdabackend.domain.bookmark.response.BookmarkCardResponse;

import java.util.List;

public interface BookmarkMapper {
  Bookmark findByUserIdAndPropertyId(BookmarkCreateRequest bookmarkCreateRequest);
  // userId와 propertyId로 이미 찜한 데이터가 있는지 조회(있으면 북마크반환, 없으면 널)

  void insertBookmark(Bookmark bookmark);
  void deleteByUserIdAndPropertyId(BookmarkCreateRequest bookmarkCreateRequest);

  long countByPropertyId(Long propertyId);
  // 찜 개수 조회

  List<BookmarkCardResponse> findCardsByUserId(Long userId);
  // 마이페이지 찜 카드 목록 조회
}