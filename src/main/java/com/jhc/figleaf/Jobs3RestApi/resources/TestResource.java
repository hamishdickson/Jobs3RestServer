package com.jhc.figleaf.Jobs3RestApi.resources;

import com.google.gson.Gson;
import com.jhc.figleaf.Jobs3RestApi.models.*;
import com.wordnik.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * User: DicksonH
 * Date: 13/03/14
 * Time: 09:05
 */

@Path("/jobtest")
@Api( value = "/jobtest", description = "API to the jobs system using temp test data" )
public class TestResource {
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
/*
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
    }*/

    @GET
    @Path("/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Find details of a specific job",
            notes = "The americans once nearly destroyed the earth with a bomb called castle bravo",
            response = Response.class,
            responseContainer = "JSON"
    )
    public Response getJobsForUser(@PathParam("userId")  String userId, @QueryParam("status") String status) {
        System.out.println("Request for: userId=" + userId + " and status=" + status);
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
    @Path("/person/{personId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@ApiParam(value = "Person", required = true) @PathParam("personId") String personId) {
        Person person = new Person("Hamish Dickson", "HD", "NB", "DICKSONH");

        return Response.ok().entity(new Gson().toJson(person)).build();

    }

    @GET
    @Path("/jobNotes/{jobNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJobNotesForJob(@ApiParam(value = "Job number", required = true) @PathParam("jobNumber") int jobNumber) {
        JobNotes jobNotes = new JobNotes(jobNumber, "This is a decent sized job note. With lots of blah blah to detail the blah blah that's already been passed. Oh actually thats wrong what I should have said was ...                                         actually no, the first person was right ... yeah "
                + "This is a decent sized job note. With lots of blah blah to detail the blah blah that's already been passed. Oh actually thats wrong what I should have said was ... actually no, the first person                                             was right ... yeah "
                + "This is a decent sized job note. With lots of blah blah to detail the blah blah that's already been passed. Oh actually thats wrong what I should have said was ... actually no, the first person                     123456               was right ... yeah "
                , 3);

        System.out.println("Call to test system made: job notes for job " + jobNumber);

        return Response.ok().entity(new Gson().toJson(jobNotes)).build();

    }

    /*
    * Use POST to create new entities
    */
    @POST
    @Path("/jobNotes")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses(value = { @ApiResponse(code = 405, message = "Invalid input") })
    @ApiOperation(
            value = "Add a new note",
            notes = "The field for this is only 79 characters long"
    )
    public Response addTestNote(@ApiParam(value = "Create a new job note", required = true) JobNotes jobNotes) {
        Jobs.updateNote(jobNotes);

        // I'm going to return the whole lot just to be nice :)
        return Response.ok().entity(Jobs.toJsonString()).build();
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

    @GET
    @Path("/deliverables/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get all deliverables for a user",
            notes = "Not a lot to say about this really ",
            response = Response.class,
            responseContainer = "JSON"
    )
    public Response getUsersDeliverables(@ApiParam(value = "User id", required = true) @PathParam("userId") String userId) {
        List<DeliverableKey> deliverableKeyList = new ArrayList<DeliverableKey>();
        deliverableKeyList.add(new DeliverableKey(223456, 1, 20140902));
        deliverableKeyList.add(new DeliverableKey(223456, 2, 20140602));
        deliverableKeyList.add(new DeliverableKey(223456, 4, 20140802));
        deliverableKeyList.add(new DeliverableKey(323456, 4, 20140803));
        deliverableKeyList.add(new DeliverableKey(423456, 4, 20140804));
        deliverableKeyList.add(new DeliverableKey(523456, 4, 20140805));
        deliverableKeyList.add(new DeliverableKey(623456, 4, 20140806));
        deliverableKeyList.add(new DeliverableKey(723456, 4, 20140807));
        deliverableKeyList.add(new DeliverableKey(823456, 4, 20140808));

        return Response.ok().entity(new Gson().toJson(deliverableKeyList)).build();

    }

    @GET
    @Path("/deliverables/job/{jobNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get all deliverables for a user",
            notes = "Not a lot to say about this really ",
            response = Response.class,
            responseContainer = "JSON"
    )
    public Response getJobsDeliverables(@ApiParam(value = "Job number", required = true) @PathParam("jobNumber") int jobNumber) {
        List<DeliverableKey> deliverableKeyList = new ArrayList<DeliverableKey>();
        deliverableKeyList.add(new DeliverableKey(223456, 1, 20140702));
        deliverableKeyList.add(new DeliverableKey(223456, 2, 20140602));
        deliverableKeyList.add(new DeliverableKey(223456, 4, 20140802));

        return Response.ok().entity(new Gson().toJson(deliverableKeyList)).build();

    }

    @GET
    @Path("/deliverables/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDeliverable(@QueryParam("jobNumber") int jobNumber, @QueryParam("id") int id) {

        Deliverable deliverable = new Deliverable(jobNumber, 1, 20140801, "Y", "Do dat thang", 0, "", "HD", "F63", "N");

        return Response.ok().entity(new Gson().toJson(deliverable)).build();

    }
}
