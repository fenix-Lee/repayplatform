package com.hbfintech.repay.center.infrastructure.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BaseDao<P> {

    default P select(@Param("id")Long id) {return null;}

    default List<P> selectEntities(Map<String, Object> condition) {return null;}

    default int insert(P PlainObject) {return 0;}

    default int update(Map<String, Object> condition) {return 0;}
}
