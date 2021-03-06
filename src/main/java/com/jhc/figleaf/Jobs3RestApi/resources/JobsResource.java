package com.jhc.figleaf.Jobs3RestApi.resources;

import com.google.gson.Gson;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.ErrorCompletingRequestException;
import com.ibm.as400.access.IllegalObjectTypeException;
import com.ibm.as400.access.ObjectDoesNotExistException;
import com.jhc.figleaf.Jobs3RestApi.database.RealTracey;
import com.jhc.figleaf.Jobs3RestApi.models.Job;
import com.wordnik.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by hamish dickson on 08/03/2014.
 *
 * Go forth and use at will
 */

@Path("/job")
@Api( value = "/job", description = "Open API to the jobs system" )
public class JobsResource {
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
    public Response getTestJob(@ApiParam(value = "Job number", required = true) @PathParam("jobNumber") int jobNumber) {
        try {
            Job job = RealTracey.getJob(jobNumber);

            return Response.ok().entity(new Gson().toJson(job)).build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

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

        try {
            List<Job> jobs = null;
            if (status != null) {
                jobs = RealTracey.getJobsForUserAndStatus(userId, status);
            } else {
                jobs = RealTracey.getJobsForUserAndStatus(userId);
            }
            String output = "{\"jobs\":" + new Gson().toJson(jobs) + "}";
            return Response.ok().entity(output).build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

/*    @GET
    @Path("/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Find details of a specific job",
            notes = "The americans once nearly destroyed the earth with a bomb called castle bravo",
            response = Response.class,
            responseContainer = "JSON"
    )
    public Response getJobsForUser(@ApiParam(value = "User Id", required = true) @PathParam("userId") String userId) {
        System.out.println("Request for: userId=" + userId);
        try {
            List<Job> jobs = RealTracey.getJobsForUserAndStatus(userId);
            String output = "{\"jobs\":" +  new Gson().toJson(jobs) + "}";
            return Response.ok().entity(output).build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }*/

    /**
     * Use POST to create new jobs
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses(value = { @ApiResponse(code = 405, message = "Invalid input") })
    @ApiOperation(
            value = "Add a new job to the system",
            notes = "This should have a level of authorization added to it"
    )
    public Response addJob(@ApiParam(value = "Create a new job", required = true) Job job) {
        Job outJob = null;
        try {
            outJob = RealTracey.addJob(job);
            return Response.ok().entity(new Gson().toJson(outJob)).build();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalObjectTypeException e) {
            e.printStackTrace();
        } catch (ObjectDoesNotExistException e) {
            e.printStackTrace();
        } catch (ErrorCompletingRequestException e) {
            e.printStackTrace();
        } catch (AS400SecurityException e) {
            e.printStackTrace();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    /**
     *
     * Add a new note to a job
     *
     * OK, so this is going to split the note in jobNotes up into 79 char long chunks ...
     * then add that to a List<List<String>>
     *
     */
/*    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNote(@ApiParam(value = "Add note", required = true) JobNotes jobNotes) {

        int job = jobNotes.getJobNumber();
        String note = jobNotes.getNotes();

        List<String> notesOnPage = new ArrayList<String>();

        List<List<String>> notesByPage = new ArrayList<List<String>>();

        int count = 0;
        int chunk = 78;
        int start = 0;
        int end = 78;

        while (note.length() > 0) {
            notesOnPage.add(count++, note.substring(start, end));
            start += chunk;
            end += chunk;

            if (count >= 15) {
                notesByPage.add(notesOnPage);
                notesOnPage.clear();
                count = 0;
            }
        }

        // if there is anything left, add it to the last page
        notesByPage.add(notesOnPage);
        notesOnPage.clear();

        // now call RealTracey and post it

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }*/
}