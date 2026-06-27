package com.zipdabackend.domain.report.mapper;

import com.zipdabackend.domain.report.entity.Report;
import com.zipdabackend.domain.report.response.ReportManageResponse;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface ReportMapper {
    List<ReportManageResponse> getReport(int size, int offset);
    long searchTotal(); // 페이지네이션 : 총 수

    int deleteProperty(Long prpertyId);
    int updateStatus(Long reportId, String status);
    Report findByUserProperty(Long userId, Long propertyId);
    int insertReport(Report report);
}
