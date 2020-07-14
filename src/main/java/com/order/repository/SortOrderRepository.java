package com.order.repository;

import org.jooq.DSLContext;
//import static com.order.database.jooq.tables.records.


public class SortOrderRepository {

    private final DSLContext dslContext;

    public SortOrderRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public void create(){
//        dslContext.insertInto(SORT_ORDER)
    }
}
