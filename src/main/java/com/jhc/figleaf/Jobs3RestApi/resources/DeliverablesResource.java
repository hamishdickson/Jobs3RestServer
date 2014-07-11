package com.jhc.figleaf.Jobs3RestApi.resources;

import com.google.gson.Gson;
import com.jhc.figleaf.Jobs3RestApi.database.RealTracey;
import com.jhc.figleaf.Jobs3RestApi.models.Deliverable;
import com.jhc.figleaf.Jobs3RestApi.models.DeliverableKey;
import com.jhc.figleaf.Jobs3RestApi.models.Job;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DicksonH on 11/07/2014.
 *
 * Deliverables information
 */
@Path("/deliverables")
@Api( value = "/deliverables", description = "Open API to the deliverables system" )
public class DeliverablesResource {
    @GET
    @Path("/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get all deliverables for a user",
            notes = "Not a lot to say about this really ",
            response = Response.class,
            responseContainer = "JSON"
    )
    public Response getUsersDeliverables(@ApiParam(value = "User id", required = true) @PathParam("userId") String userId) {
        try {
            List<DeliverableKey> deliverableKeyList = RealTracey.getDeliverablesForUser(userId);

            return Response.ok().entity(new Gson().toJson(deliverableKeyList)).build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDeliverable(@QueryParam("jobNumber") int jobNumber, @QueryParam("id") int id) {
        try {
            Deliverable deliverable = RealTracey.getDeliverable(jobNumber, id);

            return Response.ok().entity(new Gson().toJson(deliverable)).build();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
