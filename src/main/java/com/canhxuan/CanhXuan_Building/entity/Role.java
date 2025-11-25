package com.canhxuan.CanhXuan_Building.entity;

import java.util.Set;

public enum Role {
    USER(Set.of(
            Permission.CONTRACT_READ_OWN,
            Permission.CONTRACT_CREATE,
            Permission.CONTRACT_UPDATE_OWN,
            Permission.CONTRACT_DELETE_OWN
    )),

    ADMIN(Set.of(
            Permission.CONTRACT_READ_ALL,
            Permission.CONTRACT_CREATE,
            Permission.CONTRACT_UPDATE_ALL,
            Permission.CONTRACT_DELETE_ALL,
            Permission.CUSTOMER_MANAGE,
            Permission.SERVICE_MANAGE,
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
