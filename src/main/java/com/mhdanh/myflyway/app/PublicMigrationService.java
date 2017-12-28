package com.mhdanh.myflyway.app;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.sql.DataSource;

import com.mhdanh.myflyway.api.MigrationManager;


@Singleton
@Startup
@TransactionManagement(TransactionManagementType.BEAN)
public class PublicMigrationService {
    
    @Resource(lookup = "java:jboss/datasources/my_multi_tenancy")
    private DataSource dataSource;

    @PostConstruct
    public void startup() throws Exception {
        MigrationManager.getInstance(dataSource).runMigration();
    }
}
