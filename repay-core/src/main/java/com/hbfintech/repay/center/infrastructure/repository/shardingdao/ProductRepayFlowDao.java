package com.hbfintech.repay.center.infrastructure.repository.shardingdao;

import com.hbfintech.repay.center.infrastructure.dao.BaseDao;
import com.hbfintech.repay.center.infrastructure.repository.po.ProductRepayFlowPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface ProductRepayFlowDao extends BaseDao<ProductRepayFlowPO> {

    @Override
    ProductRepayFlowPO select(@Param("id")Long id);

    @Override
    List<ProductRepayFlowPO> selectEntities(Map<String, Object> conditions);

    @Override
    int insert(ProductRepayFlowPO PlainObject);
}
