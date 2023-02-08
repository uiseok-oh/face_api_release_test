package com.justudy.backend.study.dto.response;

import com.justudy.backend.study.domain.StudyEntity;
import com.justudy.backend.study.domain.StudyResumeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Builder
public class StudyDetailResponse {
    private Long sequence;
    private String name;
    private String leader;
    private Integer population;
    private String topCategory;
    private String bottomCategory;
    private String introduction;
    private List<StudyMemberResponse> member;
    private List<StudyFrequencyResponse> frequency;
    private String level;
    private String meeting;
    private Boolean isOpen;
    private String github;
    private String notion;
    private String startTime;
    private List<Long> resumeSeq;
    private Long leaderSeq;
    private Long imageSequence;

    public static StudyDetailResponse makeBuilder(StudyEntity entity) {
        //todo imagefile
        return StudyDetailResponse.builder()
                .sequence(entity.getSequence())
                .member(entity.getStudyMembers()
                        .stream()
                        .map(StudyMemberResponse::makeBuilder)
                        .sorted(Comparator.comparing(StudyMemberResponse::getBadge).reversed())
                        .collect(Collectors.toList()))
                .resumeSeq(entity.getResumes()
                        .stream()
                        .map(StudyResumeEntity::getSequence)
                        .collect(Collectors.toList()))
                .frequency(entity.getFrequency()
                        .stream()
                        .map(StudyFrequencyResponse::makeBuilder)
                        .collect(Collectors.toList()))
                .topCategory(entity.getCategory().getParentCategory().getValue())
                .bottomCategory(entity.getCategory().getValue())
                .name(entity.getName())
                .leaderSeq(entity.getLeaderSeq())
                .introduction(entity.getIntroduction())
                .population(entity.getPopulation())
                .level(entity.getLevel())
                .meeting(entity.getMeeting())
                .isOpen(entity.getIsOpen())
                .github(entity.getGithub())
                .notion(entity.getNotion())
//                .imageSequence(entity.getImageFile().getSequence())
                .startTime(entity.getStartTime())
                .build();
    }
}
