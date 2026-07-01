package com.zipdabackend.domain.user.mapper;

import com.zipdabackend.domain.user.entity.User;
import com.zipdabackend.domain.user.request.MyProfileUpdateRequest;
import com.zipdabackend.domain.user.response.MyPropertyCardResponse;
import com.zipdabackend.domain.user.response.MyReportResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserProfileMapper {

    // 로그인한 회원의 현재 정보를 조회한다.
    User findMyProfile(Long userId);

    // 이메일, 비밀번호는 제외하고 이름/닉네임/휴대폰 번호만 수정한다.
    int updateProfile(Long userId, MyProfileUpdateRequest request);

    // 회원 탈퇴는 실제 삭제가 아니라 deleted_at만 채우는 soft delete로 처리한다.
    int softDeleteUser(Long userId);

    // 비밀번호 변경용. 프론트에서는 아직 연결하지 않는다.
    int updatePassword(Long userId, String encodedPassword);

    // 공인중개사 프로필 사진 조회. 일반 유저는 사용하지 않는다.
    String findAgentProfileImageUrl(Long userId);

    // 공인중개사 프로필 사진이 이미 있으면 update, 없으면 insert하기 위해 개수를 확 인한다.
    int countAgentProfileImage(Long userId);

    // 공인중개사 프로필 사진을 최초 등록한다.
    int insertAgentProfileImage(Long userId, String profileImageUrl);

    // 공인중개사 프로필 사진을 변경한다.
    int updateAgentProfileImage(Long userId, String profileImageUrl);

    // 내가 올린 매물 목록 조회. USER/AGENT 둘 다 조회 가능하다.
    List<MyPropertyCardResponse> findMyProperties(Long userId);

    // 내가 접수한 신고 내역 조회.
    List<MyReportResponse> findMyReports(Long userId);
}