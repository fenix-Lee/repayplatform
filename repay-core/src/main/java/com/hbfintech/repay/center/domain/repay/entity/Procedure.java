package com.hbfintech.repay.center.domain.repay.entity;

import com.hbfintech.repay.center.domain.repay.object.ModuleProposal;
import com.hbfintech.repay.center.domain.repay.service.AbstractBusiness;
import com.hbfintech.repay.center.infrastructure.framework.*;
import com.hbfintech.repay.center.infrastructure.util.BeanFactory;
import lombok.EqualsAndHashCode;
import org.springframework.util.ObjectUtils;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Entity
@OverrideClone
public class Procedure extends AbstractBusiness<Operation> {

    public void business(Validation validation, ModuleProposal proposal) {
        if (!proposal.validateState())
            return;

        Operation module = getModule();
        if (ObjectUtils.isEmpty(validation)) {
            module.handle(proposal);
        } else if (validation.validate(proposal)) {
            module.handle(proposal);
        } else {
            proposal.reset(ModuleProposal.FLOW_FAIL_STATE);
        }
    }

    @Override
    public Procedure clone() throws CloneNotSupportedException {
        Procedure procedureCopy = (Procedure) super.clone();
        if (makeAccessible(procedureCopy.getClass(), "module")) {
            procedureCopy.setModule(BeanFactory.copyObject(getModule()));
        } else {
            System.out.println("cannot setup modules.....");
        }
        return procedureCopy;
    }
}
