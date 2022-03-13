package com.hbfintech.repay.center.application;

import com.hbfintech.repay.center.domain.entity.Car;
import com.hbfintech.repay.center.domain.entity.CarDto;
import com.hbfintech.repay.center.domain.entity.CarEntity;
import com.hbfintech.repay.center.domain.entity.RepayFlow;
import com.hbfintech.repay.center.domain.object.OperationType;
import com.hbfintech.repay.center.domain.object.Repayment;
import com.hbfintech.repay.center.domain.root.Contract;
import com.hbfintech.repay.center.domain.service.Procedure;
import com.hbfintech.repay.center.domain.service.factory.FintechDomainDefaultProcedureFactory;
import com.hbfintech.repay.center.domain.service.factory.FintechDomainDefaultValidationFactory;
import com.hbfintech.repay.center.domain.service.factory.FintechFactory;
import com.hbfintech.repay.center.domain.service.strategy.Apply;
import com.hbfintech.repay.center.domain.service.strategy.Operation;
import com.hbfintech.repay.center.domain.service.strategy.Validation;
import com.hbfintech.repay.center.infrastructure.annotation.Indicator;
import com.hbfintech.repay.center.infrastructure.util.BeanFactory;
import com.hbfintech.repay.center.infrastructure.util.BeanMapper;
import ma.glasnost.orika.metadata.TypeFactory;
import org.apache.tomcat.jni.Proc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.TimeUnit;

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

        Operation apply = (@Indicator(2) Apply) repayment -> {};
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
                .operationPoxy(OperationType.APPLY, (Apply) repayment -> System.out.println("test"))
                .validationPoxy(OperationType.APPLY, repayment -> {System.out.println("validation proxy");
                        return true;})
                .commit();

        flow.startTransaction(null);
        System.out.println(".....proxy done");

//        Procedure procedure = new Procedure();
//        procedure.setOperation((Operation & Cloneable) () -> System.out.println("test"));
//        Procedure copy = BeanFactory.copyObject(procedure);

//        Class<?> clazz = procedure.getClass();
//        Field field = clazz.getDeclaredField("operation");
//        System.out.println(field.getType().asSubclass(Apply.class));
//        System.out.println(clazz.getGenericSuperclass());
//        System.out.println(clazz.getTypeName());
//        Arrays.stream(clazz.getTypeParameters()).forEach(p -> System.out.println(p.getGenericDeclaration()));

//        Optional<Car> test = BeanMapper.mapping(carDto, Car.class);
//        System.out.println(test.getName());
//        System.out.println(test.getOwner());
//        System.out.println(test.getOnlineUser());
//        System.out.println(test.getUser());

//        System.out.println(test);



    }
}
