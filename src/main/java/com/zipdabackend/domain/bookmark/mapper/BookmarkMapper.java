package com.zipdabackend.domain.bookmark.mapper;

import com.zipdabackend.domain.bookmark.entity.Bookmark;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface BookmarkMapper  {

    long countByUserIdAndPropertyId(
            long userId,
            long propertyId
    );
    void insertBookmark(Bookmark bookmark);

    void deleteBookmark(
        long userId,
        long propertyId
    );

    List<Bookmark> findByUserId(Long userId);
}
