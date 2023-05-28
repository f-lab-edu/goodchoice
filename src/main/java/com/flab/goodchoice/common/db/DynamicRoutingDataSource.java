package com.flab.goodchoice.common.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import static org.springframework.transaction.support.TransactionSynchronizationManager.isCurrentTransactionReadOnly;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return isCurrentTransactionReadOnly() ? "slave" : "master";
    }

}
