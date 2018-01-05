package com.mhdanh.myflyway.api;

import javax.sql.DataSource;

public interface MigrationService {
        void migrateTenant(DataSource dataSource, String tenantId);
}
