package com.zipdabackend.domain.bookmark.entity;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bookmark {
    private long bookmarkId;
    private  long userId;
    private long propertyId;
    private String createdAt;

}
