package com.hbfintech.repay.center.step;

import com.hbfintech.repay.center.infrastructure.dao.BaseDao;
import com.hbfintech.repay.center.infrastructure.repository.BaseRepository;
import com.hbfintech.repay.center.infrastructure.repository.RepayFlowRepository;
import com.hbfintech.repay.center.infrastructure.repository.po.BasePO;
import com.hbfintech.repay.center.infrastructure.repository.po.ProductRepayFlowPO;
import com.hbfintech.repay.center.infrastructure.util.BeanFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RepayStep {

    private BasePO po;

    @Given("insert following {string} in database by using {string}")
    @SuppressWarnings("unchecked")
    public void insertData(String object, String name, List<Map<String, String>> table) {
        BaseRepository<?, BasePO> repository = (BaseRepository<?, BasePO>) BeanFactory.acquireBean(name);
        table.forEach(d -> {
            ProductRepayFlowPO po = new ProductRepayFlowPO();
            po.setApplyId(Long.parseLong(d.get("repay_apply_id")));
            po.setSerialNo(d.get("internal_order_num"));
            ReflectionTestUtils.invokeMethod(repository, "insert", po);
        });
    }

    @When("I search this flow, which id is {long} with {string}")
    @SuppressWarnings("unchecked")
    public void searchFlow(long id, String name) {
        BaseRepository<?, BasePO> repository = (BaseRepository<?, BasePO>) BeanFactory.acquireBean(name);
        Optional<ProductRepayFlowPO> po = ReflectionTestUtils.invokeMethod(repository, "queryEntity", id);
        Assert.assertTrue(null != po && po.isPresent());
        this.po = po.get();
    }

    @Then("I am be told the field {string} is {string}")
    public void beToldAssertion(String field, String value) {
        Assert.assertEquals(ReflectionTestUtils.getField(po,field), value);
    }
}
