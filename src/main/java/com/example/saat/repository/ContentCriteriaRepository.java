package com.example.saat.repository;

import com.example.saat.models.Content;
import com.example.saat.models.ContentPage;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ContentCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public ContentCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Content> findAllWithFilters(ContentPage contentPage, Content content){
        CriteriaQuery<Content> criteriaQuery = criteriaBuilder.createQuery(Content.class);
        Root<Content> contentRoot = criteriaQuery.from(Content.class);
        Predicate predicate = getPredicate(content, contentRoot);
        criteriaQuery.where(predicate);

        TypedQuery<Content> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(contentPage.getPageNumber() + contentPage.getPageSize());
        typedQuery.setMaxResults(contentPage.getPageSize());

        return new PageImpl<>(typedQuery.getResultList());
    }
    private Predicate getPredicate(Content content, Root<Content> contentRoot){
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(content.getName())){
            predicates.add(criteriaBuilder.like(contentRoot.get("name"),
                    "%" + content.getName() + "%"));
        }
        if(Objects.nonNull(content.getStatus())){
            predicates.add(criteriaBuilder.like(contentRoot.get("status"),
                    "%" + content.getStatus() + "%"));
        }
        if(Objects.nonNull(content.getLicenses())){
            predicates.add(criteriaBuilder.like(contentRoot.get("licenses"),
                    "%" + content.getLicenses() + "%"));
        }
        if(Objects.nonNull(content.getYear())){
            predicates.add(criteriaBuilder.like(contentRoot.get("year"),
                    "%" + content.getYear() + "%"));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
