package com.zipdabackend.domain.bookmark.mapper;

import com.zipdabackend.domain.bookmark.entity.Bookmark;
import com.zipdabackend.domain.bookmark.response.BookmarkCardResponse;
import com.zipdabackend.domain.bookmark.response.BookmarkTop3;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface BookmarkMapper {
  Bookmark findByUserIdAndPropertyId(Bookmark bookmark);
  // userId와 propertyId로 이미 찜한 데이터가 있는지 조회(있으면 북마크반환, 없으면 널)

  int insertBookmark(Bookmark bookmark);

  int deleteByUserIdAndPropertyId(Bookmark bookmark);

  long countByPropertyId(Long propertyId);
  // 매물별 찜 개수 조회

  List<BookmarkCardResponse> findCardsByUserId(Long userId);
  // 마이페이지 찜 카드 목록 조회

  List<BookmarkTop3> top3Properties();
}