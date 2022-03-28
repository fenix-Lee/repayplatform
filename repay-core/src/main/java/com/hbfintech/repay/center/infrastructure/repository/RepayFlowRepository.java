package com.hbfintech.repay.center.infrastructure.repository;

import com.hbfintech.repay.center.infrastructure.annotation.Repository;
import com.hbfintech.repay.center.infrastructure.repository.dao.ProductRepayFlowDao;
import com.hbfintech.repay.center.infrastructure.repository.po.ProductRepayFlowPO;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class RepayFlowRepository extends
        BaseRepository<ProductRepayFlowDao, ProductRepayFlowPO> {

    @Autowired
    public RepayFlowRepository(ProductRepayFlowDao dao) {
        setDao(dao);
    }

    /**
     * find repay flow by primary key
     *
     * @param id primary key
     * @return repay flow
     */
    public ProductRepayFlowPO searchRepayFlow(Long id) {
        return queryEntity(id);
    }

    /**
     * find repay flow by serial number
     *
     * @param serial serial number
     * @return repay flow
     */
    public ProductRepayFlowPO searchRepayFlow(String serial) {
       Condition condition = Condition.createCondition()
                .addParameter("serial", serial);
        return uniqueQueryWithCondition(condition.getParameters());
    }

}
