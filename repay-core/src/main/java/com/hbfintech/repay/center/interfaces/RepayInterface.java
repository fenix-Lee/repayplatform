package com.hbfintech.repay.center.interfaces;

import com.hbfintech.repay.center.domain.repay.entity.Car;
import com.hbfintech.repay.center.domain.repay.entity.CarEntity;
import com.hbfintech.repay.center.domain.repay.entity.RepayFlow;
import com.hbfintech.repay.center.domain.repay.object.OperationType;
import com.hbfintech.repay.center.domain.repay.entity.Contract;
import com.hbfintech.repay.center.domain.Apply;
import com.hbfintech.repay.center.domain.Filter;
import com.hbfintech.repay.center.domain.Operation;
import com.hbfintech.repay.center.infrastructure.framework.Indicator;
import com.hbfintech.repay.center.infrastructure.util.BeanFactory;
import com.hbfintech.repay.center.infrastructure.util.BeanMapper;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class RepayInterface {

    @GetMapping("/test")
    public void getEntity() throws NoSuchFieldException, CloneNotSupportedException {
//        BeanMapper.mapperRegister(Car.class, CarDto.class, null);
        Car car = new Car();
        car.setName("tesla");
        car.setOwner("chang");
        car.setUsername("test");

        CarEntity carEntity = BeanMapper.mapping(car, CarEntity.class);
        carEntity.setName("test");

        Operation apply = (Apply) repayment -> {};
        System.out.println(apply.getClass().getAnnotation(Indicator.class));
        Arrays.stream(apply.getClass().getAnnotatedInterfaces()).forEach(System.out::println);
        Arrays.stream(apply.getClass().getAnnotations()).forEach(System.out::println);
        Arrays.stream(apply.getClass().getDeclaredAnnotations()).forEach(System.out::println);
        Indicator springIndicator = AnnotationUtils.findAnnotation(apply.getClass(), Indicator.class);
//        System.out.println(reflectionIndicator);
        System.out.println(springIndicator);

//        FintechDomainDefaultProcedureFactory factory = FintechFactory.INSTANCE
//                .getFactoryInstance(FintechDomainDefaultProcedureFactory.class);
//
//        FintechDomainDefaultValidationFactory validationFactory = FintechFactory.INSTANCE
//                .getFactoryInstance(FintechDomainDefaultValidationFactory.class);
//        Map<OperationType, Validation> maps = validationFactory.fabricate();

//        factory.getProcedures().forEach(System.out::println);

        Contract contract = BeanFactory.acquireBean(Contract.class);
        contract.setContractIndex(34L);
        RepayFlow flow = RepayFlow.createRepayFlow();
//        flow.startTransaction(null);
        flow.setContract(contract);
        flow.transform()
                .operationPoxy(OperationType.APPLY, (Apply) repayment -> System.out.println("apply proxy"))
                .validationPoxy(OperationType.APPLY, repayment -> {System.out.println("validation proxy");
                        return true;})
                .filterPoxy((Filter<Operation>) (o) -> OperationType.convert(o).equals(OperationType.CALCULATION) ||
                        OperationType.convert(o).equals(OperationType.RECHARGE))
                .commit();

        flow.startTransaction(null);
        System.out.println(".....proxy done");

        Optional<Contract> optionalContract = flow.getOptionalContract();
    }
}
