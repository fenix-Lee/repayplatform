package com.hbfintech.repay.center.domain.repay.entity;

import com.hbfintech.repay.center.infrastructure.framework.FieldMapping;
import com.hbfintech.repay.center.infrastructure.framework.Mapper;
import com.hbfintech.repay.center.infrastructure.framework.Mappers;
import com.hbfintech.repay.center.infrastructure.framework.Mappings;
import lombok.Data;

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
