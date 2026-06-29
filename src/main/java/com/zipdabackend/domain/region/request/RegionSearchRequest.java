package com.zipdabackend.domain.region.request;

public record RegionSearchRequest(
    String province
    , String city
    , String district
) {
}
