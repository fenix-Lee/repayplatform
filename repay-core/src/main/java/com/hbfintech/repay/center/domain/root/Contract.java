package com.hbfintech.repay.center.domain.root;

import com.hbfintech.repay.center.domain.entity.RepayFlow;
import com.hbfintech.repay.center.infrastructure.annotation.Entity;
import com.hbfintech.repay.center.infrastructure.util.BeanFactory;
import lombok.Data;
import org.springframework.stereotype.Component;

@Entity
@Data
public class Contract {

    private Long contractIndex;


}
