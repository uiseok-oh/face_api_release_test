package com.justudy.backend.member.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.justudy.backend.member.domain.MemberEntity;
import com.justudy.backend.common.enum_util.Region;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberCreate {

    @NotBlank
    private String userId;

    @NotBlank
    private String password;

    @NotBlank
    private String passwordCheck;

    @NotBlank
    private String username;

    @NotBlank
    private String nickname;

    @NotBlank
    private String ssafyId;

    @NotBlank
    private String phone;

    @NotBlank
    private String email;

    @NotBlank
    private String mmId;

    private String region;

    private String dream;

    private String[] category;

    private String introduction;

    @Builder
    @JsonCreator
    public MemberCreate(@JsonProperty("userId") String userId,
                        @JsonProperty("password") String password,
                        @JsonProperty("passwordCheck") String passwordCheck,
                        @JsonProperty("username") String username,
                        @JsonProperty("nickname") String nickname,
                        @JsonProperty("ssafyId") String ssafyId,
                        @JsonProperty("phone") String phone,
                        @JsonProperty("email") String email,
                        @JsonProperty("mmId") String mmId,
                        @JsonProperty("region") String region,
                        @JsonProperty("dream") String dream,
                        @JsonProperty("category") String[] category,
                        @JsonProperty("introduction") String introduction) {
        this.userId = userId;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.username = username;
        this.nickname = nickname;
        this.ssafyId = ssafyId;
        this.phone = phone;
        this.email = email;
        this.mmId = mmId;
        this.region = region;
        this.dream = dream;
        this.category = category;
        this.introduction = introduction;
    }

    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .userId(userId)
                .password(password)
                .username(username)
                .nickname(nickname)
                .ssafyId(ssafyId)
                .phone(phone)
                .email(email)
                .region(Region.valueOf(region))
                .dream(dream)
                .introduction(introduction)
                .mmId(mmId)
                .build();
    }
}
