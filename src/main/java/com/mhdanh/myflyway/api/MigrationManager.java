package com.mhdanh.myflyway.api;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;

/**
 * A MigrationManager is responsible for performing a database update, typically
 * when deploying a new application release.<br>
 * The MigrationManager looks per default in the "db/public" resource directory for the sql migration scripts to run.
 *
 */
public class MigrationManager {

    private static final Logger LOG = Logger.getLogger(MigrationManager.class.getName());

    private final DataSource dataSource;
    private String scriptsLocation = "db/public";
    private boolean ignoreBaselineIfTablesPresent = true;
    private final Set<String> schemaNames = new HashSet<>();

    private MigrationManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Returns an instance of the MigrationManager that can run the database
     * migration scripts contained contained in the migration script
     * location.<br>
     * The datasource connection's user must have enough priviledge for running
     * ddl. If not a MigrationException will be thrown at migration time.
     *
     * @param dataSource the datasource pointing to the database that has to be
     * migrated. Cannot be null.
     * @return an instance of the MigrationManager
     */
    public static MigrationManager getInstance(DataSource dataSource) {
        return new MigrationManager(dataSource);
    }

    /**
     * Performs the migration.
     * @throws Exception 
     *
     * @throws MigrationException
     */
    public void runMigration() throws Exception {
        Flyway flyway = new Flyway();
        flyway.setLocations(this.scriptsLocation);
        flyway.setDataSource(dataSource);
        flyway.setMixed(true);
        if (!this.schemaNames.isEmpty()) {
            flyway.setSchemas(this.schemaNames.stream().toArray(String[]::new));
        }
        flyway.setBaselineOnMigrate(this.ignoreBaselineIfTablesPresent);
        flyway.setIgnoreFutureMigrations(true);
        for (MigrationInfo migrationInfo : flyway.info().pending()) {
            LOG.log(Level.INFO, "migrate task: {0} : {1} from file: {2}", new Object[]{migrationInfo.getVersion(), migrationInfo.getDescription(), migrationInfo.getScript()});
        }
        try {
            flyway.migrate();
        } catch (Exception ex) {
            throw new Exception("An Exception occurred while performing the migration on the datasource " + dataSource.toString(), ex);
        }
    }
}
