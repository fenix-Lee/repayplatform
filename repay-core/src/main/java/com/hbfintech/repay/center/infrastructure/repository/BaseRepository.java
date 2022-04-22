package com.hbfintech.repay.center.infrastructure.repository;

import com.hbfintech.repay.center.infrastructure.dao.BaseDao;
import com.hbfintech.repay.center.infrastructure.repository.po.BasePO;
import org.springframework.util.ObjectUtils;

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

    protected Optional<E> queryEntity(Long id) {
        return Optional.ofNullable(entityDao)
                .map(d -> d.select(id));
    }

    protected Optional<E> uniqueQueryWithCondition(Map<String, Object> condition) {
        return queryWithCondition(condition)
                .map(l -> {
                    int size;
                    if ((size=l.size()) > 1) {
                        throw new RuntimeException("expected only one entity but got " + size);
                    }
                    return size == 0? null : l.get(0);
                });
    }

    protected Optional<List<E>> queryWithCondition(Map<String, Object> condition) {
        return queryWithFilter(condition, (e) -> e.valid == 0);
    }

    protected Optional<List<E>> queryWithFilter(Map<String, Object> condition, Predicate<E> filter) {
        return Optional.ofNullable(entityDao)
                .map(d -> d.selectEntities(condition).stream()
                        .filter(filter)
                        .collect(Collectors.toList()));
    }

    protected int insert(E po) {
        return entityDao.insert(po);
    }

    private static class Sigma {

        private int value;

        private static final Sigma INSTANCE = new Sigma();

        public static Sigma init() {
            return INSTANCE;
        }

        public int setAndGet(int value) {
            this.value = value;
            return value;
        }

        public int getValue() {
            return value;
        }
    }
}
