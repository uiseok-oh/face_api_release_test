package com.justudy.backend.study.dto.response;

import com.justudy.backend.study.domain.StudyEntity;
import com.justudy.backend.study.domain.StudyMemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Slf4j
@AllArgsConstructor
@Builder
public class StudyResponse {
    private Long sequence;
    private String name;
    private String leader;
    private List<StudyFrequencyResponse> frequency;
    private Integer population;
    private String topCategory;
    private String bottomCategory;
    private List<Long> member;
    private String level;
    private String meeting;
    private Boolean isOpen;
    private String startTime;
    private Long imageSequence;


    public static StudyResponse makeBuilder(StudyEntity entity) {
//        log.info("슬라이드5 {}",entity.getStudyMembers().size());
//        entity.getStudyMembers().forEach(studyMember->log.info("슬라이스2 info : {}", studyMember.getMember().getSequence()));
//        entity.getStudyMembers().forEach(studyMember->log.info("슬라이스2 info : {}", studyMember.getMember().getNickname()));
//
//        String leader = entity.getStudyMembers()
//                .stream()
//                .filter(studyMemberEntity -> entity.getLeaderSeq().longValue() == studyMemberEntity.getMember().getSequence().longValue())
//
//                .findFirst()
//                .orElseThrow(InvalidRequest::new)
//                .getMember().getNickname();

        //todo image 추가
        return StudyResponse.builder()
                .sequence(entity.getSequence())
                .member(entity.getStudyMembers()
                        .stream()
                        .map(StudyMemberEntity::getSequence)
                        .collect(Collectors.toList()))
                .frequency(entity.getFrequency()
                        .stream()
                        .map(StudyFrequencyResponse::makeBuilder)
                        .collect(Collectors.toList()))
                .topCategory(entity.getCategory().getParentCategory().getValue())
                .bottomCategory(entity.getCategory().getValue())
                .name(entity.getName())
                .population(entity.getPopulation())
                .level(entity.getLevel())
                .meeting(entity.getMeeting())
                .isOpen(entity.getIsOpen())
//                .imageSequence(entity.getImageFile().getSequence())
                .startTime(entity.getStartTime())
                .leader(null)
                .build();
    }

}
