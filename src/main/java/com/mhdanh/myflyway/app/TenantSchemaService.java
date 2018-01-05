package com.mhdanh.myflyway.app;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.sql.DataSource;

import com.mhdanh.myflyway.api.MigrationService;


@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class TenantSchemaService {
    
    @Inject
    MigrationService migrationService;

    @Resource(lookup = "java:jboss/datasources/my_multi_tenancy")
    private DataSource dataSource;
    
    public void migrate(String tenantId) {
        migrationService.migrateTenant(dataSource, tenantId);
    }

}
