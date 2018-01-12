package com.mhdanh.produce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.hibernate.jdbc.Work;

import com.mhdanh.annotation.CurrentTenant;
import com.mhdanh.constant.Queries;

@RequestScoped
@Transactional
public class MultiTenancyEntityManagerFactory {

	private static final Logger log = Logger.getLogger(MultiTenancyEntityManagerFactory.class.getName());
	
    @PersistenceContext
    EntityManager em;

    @Inject
	HttpServletRequest request;

    @Produces
    @CurrentTenant
    @RequestScoped
    public EntityManager create() {
    	return hasTenant() ? appliedTenant() : appliedPublic();
    }

    private EntityManager appliedTenant() {
        org.hibernate.Session hibernateSession = em.unwrap(org.hibernate.Session.class);
        hibernateSession.doWork(new Work(){
			@Override
			public void execute(Connection connection) throws SQLException {
				try {
		            connection.createStatement().execute(Queries.RESET_ROLE);
		            connection.createStatement().execute("SET ROLE " + getTenantId());
		        } catch (SQLException e) {
		            throw new RuntimeException(e);
		        }
			}
        });
        return em;
    }
    
    private EntityManager appliedPublic() {
        org.hibernate.Session hibernateSession = em.unwrap(org.hibernate.Session.class);
        hibernateSession.doWork(new Work(){
			@Override
			public void execute(Connection connection) throws SQLException {
				try {
		            connection.createStatement().execute(Queries.RESET_ROLE);
		            connection.createStatement().execute("SET ROLE public_role");
		        } catch (SQLException e) {
		            throw new RuntimeException(e);
		        }
			}
        });
        return em;
    }
    
    private boolean hasTenant() {
    	return request.getHeader("tenantid") != null && !request.getHeader("tenantid").isEmpty();
    }

    public void resetConnection(@Disposes @CurrentTenant EntityManager em) {
        org.hibernate.Session hibernateSession = em.unwrap(org.hibernate.Session.class);

        hibernateSession.doWork(new Work(){
			@Override
			public void execute(Connection connection) throws SQLException {
				try {
			        PreparedStatement statement = connection.prepareStatement(Queries.RESET_ROLE);
			        statement.execute();
			    } catch (SQLException e) {
			        log.log(Level.SEVERE, "Failed to reset tenant connection. "
			                + "Attempting to close connection to prevent data leak.", e);
			        try {
			        	connection.close();
			        } catch (SQLException ce) {
			            log.log(Level.SEVERE, "Also failed to close tenant connection.", ce);
			        }
			    }
			}
        });
    }

    private String getTenantId() {
    	return request.getHeader("tenantid");
    }
}
