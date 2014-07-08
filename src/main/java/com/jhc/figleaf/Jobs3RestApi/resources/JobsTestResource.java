package com.jhc.figleaf.Jobs3RestApi.resources;

import com.google.gson.Gson;
import com.jhc.figleaf.Jobs3RestApi.models.Job;
import com.jhc.figleaf.Jobs3RestApi.models.JobNotes;
import com.jhc.figleaf.Jobs3RestApi.models.Jobs;
import com.jhc.figleaf.Jobs3RestApi.models.Person;
import com.wordnik.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * User: DicksonH
 * Date: 13/03/14
 * Time: 09:05
 */

@Path("/jobtest")
@Api( value = "/jobtest", description = "API to the jobs system using temp test data" )
public class JobsTestResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "List all the jobs in the jobs system ... actually no, that would be stupid - just return the last 100",
            notes = "Probably not that helpful ... but great for testing",
            response = Response.class,
            responseContainer = "JSON"
    )
    public Response getTestJobs() {
        System.out.println("Call to test system made");
        String output = "{\"personName\": \"Hamish Dickson\", \"jobs\":" + Jobs.getJobsList() + "}";
        return Response.ok().entity(output).build();
    }

    @GET
    @Path("/user/{user}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "List all the jobs in the jobs system ... actually no, that would be stupid - just return the last 100",
            notes = "Probably not that helpful ... but great for testing",
            response = Response.class,
            responseContainer = "JSON"
    )
    public Response getUserTestJobs(@ApiParam(value = "User", required = true) @PathParam("user") String user) {
        System.out.println("Call to test system made");
        String output = "{\"jobs\":" + Jobs.getJobsList() + "}";
        //String output = Jobs.getJobsList();
        return Response.ok().entity(output).build();
    }

    @GET
    @Path("/{jobNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Find details of a specific job",
            notes = "You know when things go further away from you they look smaller? " +
                    ".... well eventually they get big again (fact of the day)",
            response = Response.class,
            responseContainer = "JSON"
    )
    public Response getJob(@ApiParam(value = "Job number", required = true) @PathParam("jobNumber") int jobNumber) {
        if (Jobs.isInKnownJob(jobNumber)) {
            return Response.ok().entity(Jobs.getJobJson(jobNumber)).build();
        }

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/person")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson() {
        Person person = new Person("Hamish Dickson", "HD", "NB", "DICKSONH");

        return Response.ok().entity(new Gson().toJson(person)).build();

    }

    @GET
    @Path("/jobNotes/{jobNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJobNotesForJob(@ApiParam(value = "Job number", required = true) @PathParam("jobNumber") int jobNumber) {
        JobNotes jobNotes = new JobNotes(jobNumber, "This is a decent sized job note. With lots of blah blah to detail the blah blah that's already been passed. Oh actually thats wrong what I should have said was ... actually no, the first person was right ... yeah "
                + "This is a decent sized job note. With lots of blah blah to detail the blah blah that's already been passed. Oh actually thats wrong what I should have said was ... actually no, the first person was right ... yeah "
                + "This is a decent sized job note. With lots of blah blah to detail the blah blah that's already been passed. Oh actually thats wrong what I should have said was ... actually no, the first person was right ... yeah "
                , 3);

        return Response.ok().entity(new Gson().toJson(jobNotes)).build();

    }

    @GET
    @Path("/emptyperson")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmptyPerson() {
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    /*
    * Use POST to create new entities
    */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses(value = { @ApiResponse(code = 405, message = "Invalid input") })
    @ApiOperation(
            value = "Add a new job to the system",
            notes = "This should have a level of authorization added to it"
    )
    public Response addTestJob(@ApiParam(value = "Create a new job", required = true) Job job) {
        Jobs.addJob(job);

        // I'm going to return the whole lot just to be nice :)
        return Response.ok().entity(Jobs.toJsonString()).build();
    }

    /**
     * Update or create a record if it doesn't exist
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Update an existing job"
    )
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid job number supplied"),
            @ApiResponse(code = 404, message = "Job number not found"),
            @ApiResponse(code = 405, message = "Validation exception") })
    public Response updateTestJob(
            @ApiParam(value = "Update a job", required = true) Job job) {
        Jobs.setJob(job);
        return Response.ok().entity(Jobs.toJsonString()).build();
    }

    @DELETE
    @Path("/destroy/{jobNumber}")
    @ApiOperation(
            value = "Delete job from the system",
            notes = "I can't think of a good reason you would do this ... but maybe you can"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Invalid job number supplied"),
                    @ApiResponse(code = 404, message = "Job not found")
            }
    )
    public Response deleteTestJob(
            @ApiParam(value = "Job number to be deleted", required = true) @PathParam("jobNumber") int jobNumber) {
        if (Jobs.deleteJob(jobNumber)) {
            return Response.ok().entity(Jobs.toJsonString()).build();
        } else {
            return Response.status(400).build();
        }

    }
}
