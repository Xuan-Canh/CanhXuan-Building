package com.canhxuan.CanhXuan_Building.entity;

import java.util.Set;

public enum Role {
    USER(Set.of(
            Permission.BUILDING_READ,
            Permission.ROOM_READ,
            Permission.CONTRACT_READ_OWN,
            Permission.CONTRACT_UPDATE_OWN,
            Permission.INVOICE_READ_OWN,
            Permission.USER_PROFILE_OWN
    )),

    ADMIN(Set.of(
            Permission.BUILDING_MANAGE,
            Permission.ROOM_MANAGE,
            Permission.CUSTOMER_MANAGE,
            Permission.CONTRACT_MANAGE,
            Permission.SERVICE_MANAGE,
            Permission.INVOICE_MANAGE,
            Permission.USER_MANAGE,
            Permission.DASHBOARD_VIEW
    ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }
}
