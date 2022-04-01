package com.hbfintech.repay.center.infrastructure.repository;

import com.hbfintech.repay.center.infrastructure.dao.BaseDao;
import com.hbfintech.repay.center.infrastructure.repository.po.BasePO;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class BaseRepository<D extends BaseDao<E>, E extends BasePO> {

    protected D entityDao;

    protected void setDao(D dao) {
        this.entityDao = dao;
    }

    protected E queryEntity(Long id) {
        return Optional.ofNullable(entityDao)
                .map(d -> d.select(id))
                .orElse(null);
    }

    protected E uniqueQueryWithCondition(Map<String, Object> condition) {
        return Optional.ofNullable(queryWithCondition(condition))
                .flatMap(l -> l.stream().findFirst())
                .orElse(null);
    }

    protected List<E> queryWithCondition(Map<String, Object> condition) {
        return queryWithFilter(condition, (e) -> e.valid == 0);
    }

    protected List<E> queryWithFilter(Map<String, Object> condition, Predicate<E> filter) {
        return Optional.ofNullable(entityDao)
                .map(d -> d.selectEntities(condition).stream()
                        .filter(filter)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}
