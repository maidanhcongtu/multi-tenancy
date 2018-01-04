package com.mhdanh.multitenancy;

public interface TenantCreator {
    void createTenantForTenantId(String tenantId);
}
