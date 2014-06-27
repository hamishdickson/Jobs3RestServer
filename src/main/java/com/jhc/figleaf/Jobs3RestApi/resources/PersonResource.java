package com.jhc.figleaf.Jobs3RestApi.resources;

import com.google.gson.Gson;
import com.jhc.figleaf.Jobs3RestApi.database.RealTracey;
import com.jhc.figleaf.Jobs3RestApi.models.Person;
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
 * Created by hamishdickson on 26/06/2014.
 *
 */

@Path("/person")
@Api( value = "/person", description = "Open API to the jobs system" )
public class PersonResource {
    @GET
    @Path("/{whoDo}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Find details of a specific user from TEARNER",
            notes = "You know when things go further away from you they look smaller? " +
                    ".... well eventually they get big again (fact of the day)",
            response = Response.class,
            responseContainer = "JSON"
    )
    public Response getPersonInfo(@ApiParam(value = "Person ID", required = true) @PathParam("whoDo") String whoDo) {
        try {
            Person person = RealTracey.getPersonByWhoDo(whoDo);

            return Response.ok().entity(new Gson().toJson(person)).build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
