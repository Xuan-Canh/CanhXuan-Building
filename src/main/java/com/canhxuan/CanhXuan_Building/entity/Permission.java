package com.canhxuan.CanhXuan_Building.entity;

public enum Permission {
    // Building permissions
    BUILDING_MANAGE,
    BUILDING_READ,

    // Room permissions
    ROOM_MANAGE,
    ROOM_READ,

    // Contract permissions
    CONTRACT_MANAGE,
    CONTRACT_READ_OWN,
    CONTRACT_UPDATE_OWN,

    // Customer permissions
    CUSTOMER_MANAGE,

    // Service permissions
    SERVICE_MANAGE,

    // Invoice permissions
    INVOICE_READ_OWN,
    INVOICE_MANAGE,

    // User permissions
    USER_MANAGE,
    USER_PROFILE_OWN,

    // Dashboard permissions
    DASHBOARD_VIEW
}
