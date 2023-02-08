package com.justudy.backend.community.service;

import com.justudy.backend.community.domain.CommunityCommentEntity;
import com.justudy.backend.community.dto.request.CommunityCommentCreate;
import com.justudy.backend.community.dto.request.CommunityCommentEdit;
import com.justudy.backend.community.dto.response.CommunityCommentResponse;
import com.justudy.backend.community.exception.CommentNotFound;
import com.justudy.backend.community.exception.CommunityNotFound;
import com.justudy.backend.community.repository.CommunityCommentRepository;
import com.justudy.backend.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityCommentService {
    private final CommunityCommentRepository repository;

    private final CommunityRepository communityRepository;

    @Transactional
    public CommunityCommentResponse createComment(long id, CommunityCommentCreate request) {
        //community 탐색 후 널이면 에러
        communityRepository.findById(id)
                .orElseThrow(CommunityNotFound::new);

        //최대 그룹 찾기
        Integer commentGroup = repository.findByGroup(request.getCommunity().getSequence());
        //댓글 작성
        if (request.getParentSeq() == null || request.getParentSeq() == 0L) {
            CommunityCommentEntity savedComment = repository.save(CommunityCommentEntity.builder()
                    .member(request.getMember())
                    .community(request.getCommunity())
                    .content(request.getContent())
                    .createdTime(LocalDateTime.now())
                    .modifiedTime(null)
                    .isDeleted(false)
                    .group(commentGroup + 1)
                    .order(0)
                    .parentSeq(0L)
                    .step(0)
                    .childNumber(0)
                    .build());
            return CommunityCommentResponse.makeBuilder(savedComment);

        } else {//대댓글 작성
            //댓글이 없음 에러
            CommunityCommentEntity parentComment = repository.findById(request.getParentSeq())
                    .orElseThrow(CommentNotFound::new);

            Integer orderResult = orderAndUpdate(parentComment);
            //null 이면 대댓글 작성 오류
            if (orderResult == null)
                return null;
            //대댓글 저장
            CommunityCommentEntity savedComment = repository.save(CommunityCommentEntity.builder()
                    .member(request.getMember())
                    .community(request.getCommunity())
                    .content(request.getContent())
                    .createdTime(LocalDateTime.now())
                    .modifiedTime(null)
                    .isDeleted(false)
                    .group(parentComment.getGroup())
                    .order(orderResult)
                    .parentSeq(request.getParentSeq())
                    .step(parentComment.getStep() + 1)
                    .childNumber(0)
                    .build());

            //부모의 자식 수 업데이트
            repository.updateChildNumber(parentComment.getSequence(), parentComment.getChildNumber());
            return CommunityCommentResponse.makeBuilder(savedComment);
        }
    }

    @Transactional
    private Integer orderAndUpdate(CommunityCommentEntity parentComment) {
        Integer step = parentComment.getStep() + 1;
        Integer order = parentComment.getOrder();
        Integer childNumber = parentComment.getChildNumber();
        Integer group = parentComment.getGroup();

        Integer childNumberSum = repository.findByChildNumberSum(group);
        Integer maxStep = repository.findByMaxStep(group);

        if (step < maxStep)
            return childNumberSum + 1;
        else if (step == maxStep) {
            repository.updateOrderPlus(group, order + childNumber);
            return order + childNumber + 1;
        } else if (step > maxStep) {
            repository.updateOrderPlus(group, order);
            return order + 1;
        }
        return null;
    }

    //한건의 댓글 읽기
    public CommunityCommentResponse readComment(Long id) {
        return CommunityCommentResponse.makeBuilder(repository.findById(id)
                .orElseThrow(CommentNotFound::new));
    }

    @Transactional
    public void UpdateComment(long id, long commentId, CommunityCommentEdit request) {
        repository.findById(commentId)
                .orElseThrow(CommentNotFound::new);
        repository.save(request.toEntity(commentId));
    }

    @Transactional
    public void deleteComment(long id, long commentId) {
        CommunityCommentEntity entity = repository.findById(commentId)
                .orElseThrow(CommentNotFound::new);
        //isDelete true로 변환
        entity.changeIsDeleted(true);
//        repository.save(entity);
    }

    public List<CommunityCommentResponse> readAllComment(long id) {
        return repository.readAllComment(id).stream().map(CommunityCommentResponse::makeBuilder).collect(Collectors.toList());
    }

}
