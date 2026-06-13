package com.sheikh.oms_api.model;

//current state of the order
public enum OrderStatus {
    NEW,

    PENDING_RISK,

    RISK_APPROVED,

    RISK_REJECTED,

    SENT_TO_EXECUTION,

    PARTIALLY_FILLED,

    FILLED,

    CANCELLED
}