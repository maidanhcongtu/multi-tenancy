package com.mhdanh.myflyway.postgres;


import javax.annotation.ManagedBean;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;

import com.mhdanh.myflyway.api.MigrationService;


@ManagedBean
public class FlywayMigrationService implements MigrationService {

    @Override
    public void migrateTenant(DataSource dataSource, String tenantId) {
        Flyway flyway = configureFlyway(dataSource, tenantId);
        runMigration(flyway);
    }

    private Flyway configureFlyway(DataSource dataSource, String tenantId) {
    	Flyway flyway = new Flyway();
        flyway.setLocations("db/migration");
        flyway.setDataSource(dataSource);
        flyway.setMixed(true);
        flyway.setIgnoreMissingMigrations(true);
        flyway.setValidateOnMigrate(false);
        flyway.setSchemas(tenantId);
        flyway.setSkipDefaultCallbacks(true);
        flyway.setIgnoreFutureMigrations(false);
        flyway.setCallbacks(new SetRoleToTenantFlywayCallback(tenantId));
        return flyway;
    }
    
    private void runMigration(Flyway flyway) {
        flyway.migrate();
    }
}
