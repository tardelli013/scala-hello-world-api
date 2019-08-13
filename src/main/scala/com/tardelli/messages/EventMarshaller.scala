package com.tardelli.messages

import play.api.libs.json._
import com.tardelli.messages.EventMessage._
import de.heikoseeberger.akkahttpplayjson._

// message containing the initial number of tickets for the event
case class EventDescription(tickets: Int) {
  require(tickets > 0)
}

// message containing the required number of tickets
case class TicketRequests(tickets: Int) {
  require(tickets > 0)
}

// message containing an error
case class Error(message: String)

// convert our case classes from and to JSON
trait EventMarshaller extends PlayJsonSupport {

  implicit val eventDescriptionFormat: OFormat[EventDescription] = Json.format[EventDescription]
  implicit val ticketRequests: OFormat[TicketRequests] = Json.format[TicketRequests]
  implicit val errorFormat: OFormat[Error] = Json.format[Error]
  implicit val eventFormat: OFormat[Event] = Json.format[Event]
  implicit val eventsFormat: OFormat[Events] = Json.format[Events]
  implicit val ticketFormat: OFormat[TicketSellerMessage.Ticket] = Json.format[TicketSellerMessage.Ticket]
  implicit val ticketsFormat: OFormat[TicketSellerMessage.Tickets] = Json.format[TicketSellerMessage.Tickets]
}

object EventMarshaller extends EventMarshaller
