package com.mhdanh.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.mhdanh.multitenancy.TenantCreator;


@RequestScoped
@Transactional
@Path("/{company-tenant-id}")
public class SchemaResource {
	
	@Inject
    private TenantCreator tenants;
	
	@POST
    @Path("schema")
    public Response createTenantSchema(@PathParam("company-tenant-id") String companyTenantId) {
            tenants.createTenantForTenantId(companyTenantId);
        	return Response.ok().build();
    }
}
