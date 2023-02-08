package com.justudy.backend.util;

import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.JPQLQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
@SuppressWarnings("unchecked")
@RequiredArgsConstructor
public class PagingUtil {

    private final EntityManager entityManager;

    private Querydsl getQuerydsl(Class clazz) {
        PathBuilder<?> builder = new PathBuilderFactory().create(clazz);
        return new Querydsl(entityManager, builder);
    }

    public <T> PageImpl<T> getPageImpl(Pageable pageable, JPQLQuery<T> query, Class clazz) {    // 2)
        long totalCount = query.fetchCount();
        List<T> results = getQuerydsl(clazz).applyPagination(pageable, query).fetch();
        return new PageImpl<>(results, pageable, totalCount);
    }

    public <T> SliceImpl<T> getSliceImpl(Pageable pageable, JPQLQuery<T> query, Class clazz) {    // 2)
        boolean hasNext = false;
        List<T> results = getQuerydsl(clazz).applyPagination(pageable, query).fetch();
        //todo work?
        if (results.size() > pageable.getPageSize()) {
            hasNext = true;
            results.remove(pageable.getPageSize());
        }
        return new SliceImpl(results, pageable, hasNext);
    }
}
