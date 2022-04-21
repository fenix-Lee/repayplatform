package com.hbfintech.repay.center.step;

import com.hbfintech.repay.center.infrastructure.repository.BaseRepository;
import com.hbfintech.repay.center.infrastructure.repository.po.BasePO;
import com.hbfintech.repay.center.infrastructure.repository.po.ProductRepayFlowPO;
import com.hbfintech.repay.center.infrastructure.util.BeanFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.val;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.ClassUtils;
import org.junit.Assert;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RepayStep {

    private BasePO po;

    @Given("insert following {string} in database by using {string}")
    @SuppressWarnings("unchecked")
    public void insertData(String beanName, String name, List<List<String>> table) throws Exception {
        Class<?> clazz = BeanFactory.acquireBean(beanName).getClass();
        BaseRepository<?, BasePO> repository = (BaseRepository<?, BasePO>) BeanFactory.acquireBean(name);
        List<String> columNames = table.get(0);
        for (int i = 1; i < table.size(); i++) {
            List<String> data = table.get(i);
            BasePO base = (BasePO) getInstance(clazz);
            for (int j = 0; j < columNames.size(); j++) {
                assert base != null;
                ReflectionTestUtils.invokeSetterMethod(base,
                        columNames.get(j),
                        getValue(clazz, columNames.get(j), data.get(j)));
            }
            ReflectionTestUtils.invokeMethod(repository, "insert", base);
        }

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

    private Object getInstance(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    private Object getValue(Class<?> clazz, String fieldName, String value) throws Exception {
        Class<?> typeClazz;
        Field field = ReflectionUtils.findField(clazz, fieldName);
        assert field != null;
        if ((typeClazz = field.getType()).isPrimitive()) {
            Class<?> wrapper = ClassUtils.primitiveToWrapper(typeClazz);
            return ConvertUtils.convert(value, wrapper);
        }

        if (!ObjectUtils.isEmpty(ClassUtils.wrapperToPrimitive(typeClazz))) {
            return ConvertUtils.convert(value, typeClazz);
        }

        if (typeClazz.equals(String.class)) {
            return value;
        }

        return clazz.getDeclaredConstructor(String.class).newInstance(value);
    }
}
