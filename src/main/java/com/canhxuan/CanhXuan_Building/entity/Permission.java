package com.canhxuan.CanhXuan_Building.entity;

public enum Permission {
    // Building permissions
    BUILDING_READ,
    BUILDING_CREATE,
    BUILDING_UPDATE,
    BUILDING_DELETE,

    // Room permissions
    ROOM_READ,
    ROOM_CREATE,
    ROOM_UPDATE,
    ROOM_DELETE,

    // Contract permissions
    CONTRACT_READ_ALL,
    CONTRACT_READ_OWN,
    CONTRACT_CREATE,
    CONTRACT_UPDATE_OWN,
    CONTRACT_UPDATE_ALL,
    CONTRACT_DELETE_OWN,
    CONTRACT_DELETE_ALL,

    // Customer permissions
    CUSTOMER_MANAGE,
    CUSTOMER_READ,
    CUSTOMER_CREATE,
    CUSTOMER_UPDATE,
    CUSTOMER_DELETE,

    // Service permissions
    SERVICE_READ,
    SERVICE_MANAGE,

    // Invoice permissions
    INVOICE_READ_ALL,
    INVOICE_READ_OWN,
    INVOICE_MANAGE,

    // User permissions
    USER_READ,
    USER_MANAGE,

    // Dashboard permissions
    DASHBOARD_VIEW
}
