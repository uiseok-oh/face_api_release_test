package com.justudy.backend.community.domain;

import com.justudy.backend.member.domain.MemberEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
@Table(name = "community_comment")
@Entity
public class CommunityCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_seq")
    private Long sequence;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "member_seq", name = "member_seq")
    private MemberEntity member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "community_seq", name = "community_seq")
    private CommunityEntity community;
    @Column(name = "comment_content")
    private String content;
    @Column(name = "comment_created_time")
    private LocalDateTime createdTime;
    @Column(name = "comment_modified_time")
    private LocalDateTime modifiedTime;
    @Column(name = "comment_is_deleted", columnDefinition = "TINYINT(1)")
    private Boolean isDeleted;
    @Column(name = "comment_group")
    private Integer group;
    @Column(name = "comment_order")
    private Integer order;
    @Column(name = "comment_parent_seq")
    private Long parentSeq;
    @Column(name = "comment_step")
    private Integer step;
    @Column(name = "comment_child_number")
    private Integer childNumber;

    public void changeIsDeleted(boolean value) {
        this.isDeleted = value;
    }
}
