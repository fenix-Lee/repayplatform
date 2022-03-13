package com.hbfintech.repay.center.domain.service.factory;

import java.util.List;

public interface DomainFactory<T> {

    default List<T> manufacture() {return null;}

    default T fabricate() { return null;}
}
