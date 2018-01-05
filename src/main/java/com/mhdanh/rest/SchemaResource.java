package com.mhdanh.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.mhdanh.multitenancy.TenantCreator;
import com.mhdanh.myflyway.app.TenantSchemaService;


@RequestScoped
@Transactional
@Path("/{company-tenant-id}")
public class SchemaResource {
	
	@Inject
    private TenantCreator tenants;
	
	@Inject
    private TenantSchemaService migrationService;
	
	@POST
    @Path("schema")
    public Response createTenantSchema(@PathParam("company-tenant-id") String companyTenantId) {
            tenants.createTenantForTenantId(companyTenantId);
        	return Response.ok().build();
    }
	
	@POST
    @Path("schema/migrate")
    public Response createTablesInTenantSchema(@PathParam("company-tenant-id") String companyTenantId) {
			migrationService.migrate(companyTenantId);
        	return Response.ok().build();
    }
	
	
}
