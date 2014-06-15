package com.jhc.figleaf.Jobs3RestApi.resources;

import com.google.gson.Gson;
import com.jhc.figleaf.Jobs3RestApi.database.RealTracey;
import com.jhc.figleaf.Jobs3RestApi.models.Job;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * Created by hamishdickson on 15/06/2014.
 *
 */
@Path("/jobNotes")
@Api( value = "/jobNotes", description = "Notes attached to a specific job" )
public class JobNotesResource {
    @GET
    @Path("/{jobNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get the job notes for a given job number",
            notes = "Where the given job does not have notes, 'no notes' will be returned",
            response = Response.class,
            responseContainer = "JSON"
    )
    public Response getTestJob(@ApiParam(value = "Job number", required = true) @PathParam("jobNumber") int jobNumber) {
        try {
            Job job = RealTracey.getJob(jobNumber);

            return Response.ok().entity(new Gson().toJson(job)).build();
        } catch (SQLException e) {
            // meh
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
