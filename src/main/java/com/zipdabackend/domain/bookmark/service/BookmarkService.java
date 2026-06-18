package com.zipdabackend.domain.bookmark.service;

import com.zipdabackend.domain.bookmark.entity.Bookmark;
import com.zipdabackend.domain.bookmark.mapper.BookmarkMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkMapper bookmarkMapper;

    public boolean toggleBookmark(Long userId, Long propertyId) {
        long count = bookmarkMapper.countByUserIdAndPropertyId(userId,propertyId);

        if (count>0) {
        bookmarkMapper.deleteBookmark(userId, propertyId);
        return false;
    } else {
            Bookmark bookmark = Bookmark.builder()
            .userId(userId)
            .propertyId(propertyId)
            .build();

        bookmarkMapper.insertBookmark(bookmark);
        return true;
        }
    }
    public List<Bookmark> getUserBookmarks(Long userId) {
        return bookmarkMapper.findByUserId(userId);
    }

}


