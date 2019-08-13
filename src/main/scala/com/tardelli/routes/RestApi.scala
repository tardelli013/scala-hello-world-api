package com.tardelli.routes

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import akka.pattern.ask
import akka.util.Timeout
import com.tardelli.messages.EventMessage._
import com.tardelli.messages._
import com.tardelli.swagger.SwaggerConfig

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}


class RestApi(system: ActorSystem, timeout: Timeout) extends RestRoutes {
  implicit val requestTimeout: Timeout = timeout
  implicit def executionContext: ExecutionContextExecutor = system.dispatcher

  def createEventActor(): ActorRef = system.actorOf(EventMessage.props)
}

trait RestRoutes extends EventApi with EventMarshaller {
  val service = "api"
  val version = "v1"

  //  endpoint for creating an event with tickets
  protected val createEventRoute: Route = {
    pathPrefix(service / version / "events" / Segment ) { event =>
      post {
        //    POST api/v1/events/event_name
        pathEndOrSingleSlash {
          entity(as[EventDescription]) { ed =>
            onSuccess(createEvent(event, ed.tickets)) {
              case EventMessage.EventCreated(event) => complete(Created, event)
              case EventMessage.EventExists =>
                val err = Error(s"$event event already exists!")
                complete(BadRequest, err)
            }
          }
        }
      }
    }
  }

  protected val getAllEventsRoute: Route = {
    pathPrefix(service / version / "events") {
      get {
        // GET api/v1/events
        pathEndOrSingleSlash {
          onSuccess(getEvents()) { events =>
            complete(OK, events)
          }
        }
      }
    }
  }

  protected val getEventRoute: Route = {
    pathPrefix(service / version / "events" / Segment) { event =>
      get {
        // GET api/v1/events/:event
        pathEndOrSingleSlash {
          onSuccess(getEvent(event)) {
            _.fold(complete(NotFound))(e => complete(OK, e))
          }
        }
      }
    }
  }

  protected val deleteEventRoute: Route = {
    pathPrefix(service / version / "events" / Segment) { event =>
      delete {
        // DELETE api/v1/events/:event
        pathEndOrSingleSlash {
          onSuccess(cancelEvent(event)) {
            _.fold(complete(NotFound))(e => complete(OK, e))
          }
        }
      }
    }
  }

  protected val purchaseEventTicketRoute: Route = {
    pathPrefix(service / version / "events" / Segment / "tickets") { event =>
      post {
        // POST api/v1/events/:event/tickets
        pathEndOrSingleSlash {
          entity(as[TicketRequests]) { request =>
            onSuccess(requestTickets(event, request.tickets)) { tickets =>
              if (tickets.entries.isEmpty) complete(NotFound)
              else complete(Created, tickets)
            }
          }
        }
      }
    }
  }

  val routes: Route = createEventRoute ~ getAllEventsRoute ~ getEventRoute ~ deleteEventRoute ~ purchaseEventTicketRoute ~
    SwaggerConfig().routes ~
    path("swagger") { getFromResource("swagger-ui/index.html") } ~ getFromResourceDirectory("swagger-ui")

}

trait EventApi {

  def createEventActor(): ActorRef

  implicit def executionContext: ExecutionContext
  implicit def requestTimeout: Timeout

  lazy val eventActor: ActorRef = createEventActor()

  def createEvent(event: String, numberOfTickets: Int): Future[EventResponse] = {
    eventActor.ask(CreateEvent(event, numberOfTickets))
      .mapTo[EventResponse]
  }

  def getEvents(): Future[Events] = eventActor.ask(GetEvents).mapTo[Events]

  def getEvent(event: String): Future[Option[Event]] = eventActor.ask(GetEvent(event)).mapTo[Option[Event]]

  def cancelEvent(event: String): Future[Option[Event]] = eventActor.ask(CancelEvent(event)).mapTo[Option[Event]]

  def requestTickets(event: String, tickets: Int): Future[TicketSellerMessage.Tickets] = {
    eventActor.ask(GetTickets(event, tickets)).mapTo[TicketSellerMessage.Tickets]
  }

}
