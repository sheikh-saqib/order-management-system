package com.sheikh.oms_api.model;

//something that happened (History)
public enum OrderEventType {
    ORDER_CREATED,

    ORDER_SENT_TO_RISK,

    ORDER_APPROVED,

    ORDER_REJECTED,

    ORDER_SENT_TO_EXECUTION,

    ORDER_PARTIALLY_FILLED,

    ORDER_FILLED,

    ORDER_CANCELLED
}
