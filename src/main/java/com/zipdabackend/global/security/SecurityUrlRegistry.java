package com.zipdabackend.global.security;

public class SecurityUrlRegistry {

    private SecurityUrlRegistry() {} // 인스턴스 생성 방지

    // ----------------------------------------
    // 블랙리스트 (인증인가 방드시 필요)
    // ----------------------------------------

    // -------------- USER, AGENT 공통 기능 ----------------------
    public static final String[] USER_AGENT_GET_URLS = {
            "/api/users/me",
            "/api/users/me/properties",
            "/api/users/me/bookmarks",
            "/api/users/me/saved-searches",
            "/api/users/me/reports",
            "/api/agents/me"
    };

    public static final String[] USER_AGENT_DELETE_URLS = {
            "/api/auth/sessions",
            "/api/users/me",
            "/api/properties/*",
            "/api/bookmarks/*",
            "/api/saved-searches/*"
    };

    public static final String[] USER_AGENT_POST_URLS = {
            "/api/property-images",
            "/api/agent-images",
            "/api/bookmarks",
            "/api/saved-searches",
            "/api/reports",
            "/api/properties" // sourceType으로 user, agent가 올린 매물 구분
    };

    public static final String[] USER_AGENT_PATCH_URLS = {
            "/api/users/me",
            "/api/properties/*",
            "/api/properties/*/status",
            "/api/saved-searches/*",
    };

    // ------------------ USER만 가능한 기능(AGENTㄴㄴ) ------------------
    public static final String[] USER_POST_URLS = {
            "/api/agents"
    };

    public static final String[] USER_GET_URLS = {

    };

    // ------------------ ADMIN만 가능한 기능 ------------------
    public static final String[] ADMIN_DELETE_URLS = {
            "/api/admin/auth/sessions"
    };

    public static final String[] ADMIN_GET_URLS = {
            "/api/admin/agents",
            "/api/admin/reports"
    };

    public static final String[] ADMIN_PATCH_URLS = {
            "/api/admin/reports/*",
            "/api/admin/agents/*"
    };

}
