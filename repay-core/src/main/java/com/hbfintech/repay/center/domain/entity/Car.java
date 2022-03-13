package com.hbfintech.repay.center.domain.entity;

import com.hbfintech.repay.center.infrastructure.annotation.FieldMapping;
import com.hbfintech.repay.center.infrastructure.annotation.Mapper;
import com.hbfintech.repay.center.infrastructure.annotation.Mappers;
import com.hbfintech.repay.center.infrastructure.annotation.Mappings;
import lombok.Data;
import org.springframework.context.annotation.Scope;

@Mappers(
        { @Mapper(target = CarDto.class),
          @Mapper(target = CarEntity.class)
        })
@Data
public class Car {
    private String name;

    private String owner;

    @Mappings(
            { @FieldMapping(scope = CarDto.class, fieldNames = {"onlineUser", "user"}),
              @FieldMapping(scope = CarEntity.class, fieldNames = "user") })
    private String username;
}
