package com.tardelli.swagger

import com.tardelli.messages.Event.Event
import com.tardelli.messages.{EventDescription, TicketRequests}
import io.swagger.annotations._
import javax.ws.rs.{Path, PathParam}

@Path("/api/v1")
@Api
trait EventRest {

  @ApiOperation(value = "Event",
    tags = Array("event"),
    httpMethod = "GET",
    produces = "application/json",
    notes = "This route will return all Events")
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "OK", response = classOf[Event]),
    new ApiResponse(code = 404, message = "Resource was not found"),
    new ApiResponse(code = 500, message = "There was an internal server error.")
  ))
  @Path("/events")
  def getEvents() = ???

  @ApiOperation(value = "Event",
    tags = Array("event"),
    httpMethod = "GET",
    produces = "application/json",
    notes = "This route will return a Events by name")
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "OK", response = classOf[Event]),
    new ApiResponse(code = 404, message = "Resource was not found"),
    new ApiResponse(code = 500, message = "There was an internal server error.")
  ))
  @Path("/events/{name}")
  def getEventsByName(@ApiParam(value = "Event identification", required = true) @PathParam("name")id: String) = ???

  @ApiOperation(value = "Event",
    tags = Array("event"),
    httpMethod = "POST",
    produces = "application/json",
    consumes = "application/json",
    notes = "This route create new Gym")
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Ok", response = classOf[EventDescription]),
    new ApiResponse(code = 201, message = "Created", response = classOf[EventDescription]),
    new ApiResponse(code = 500, message = "There was an internal server error.")
  ))
  @Path("/events/{name}")
  def postEvent(@ApiParam(value = "Event identification", required = true) @PathParam("name")id: String, ticket: EventDescription) = ???

  @ApiOperation(value = "Event",
    tags = Array("event"),
    httpMethod = "GET",
    produces = "application/json",
    notes = "This route will return a Events by name")
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "OK", response = classOf[Event]),
    new ApiResponse(code = 404, message = "Resource was not found"),
    new ApiResponse(code = 500, message = "There was an internal server error.")
  ))
  @Path("/events/{name}")
  def deleteEventsByName(@ApiParam(value = "Event identification", required = true) @PathParam("name")id: String) = ???

  @ApiOperation(value = "Event",
    tags = Array("event"),
    httpMethod = "POST",
    produces = "application/json",
    consumes = "application/json",
    notes = "This route create new Gym")
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Ok", response = classOf[EventDescription]),
    new ApiResponse(code = 201, message = "Created", response = classOf[EventDescription]),
    new ApiResponse(code = 500, message = "There was an internal server error.")
  ))
  @Path("events/{name}/tickets/")
  def postPurchaseEventTicket(@ApiParam(value = "Event identification", required = true) @PathParam("name")id: String, ticket: TicketRequests) = ???
}
