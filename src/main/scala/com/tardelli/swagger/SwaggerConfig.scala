package com.tardelli.swagger

import com.github.swagger.akka.SwaggerHttpService
import com.github.swagger.akka.model.Info
import com.tardelli.routes.RestApi
import com.typesafe.config.ConfigFactory
import io.swagger.models.Scheme
import io.swagger.models.auth.ApiKeyAuthDefinition
import io.swagger.models.auth.In.QUERY

case class SwaggerConfig() extends SwaggerHttpService {
  val config = ConfigFactory.load()
  val API_URL = config.getString("swagger.api.url")
  val BASE_PATH = config.getString("swagger.api.base.path")
  val PROTOCOL = config.getString("swagger.api.protocol")

  override val host = API_URL
  override val basePath = BASE_PATH

  override def apiClasses: Set[Class[_]] = Set(classOf[EventRest])

  override def schemes = List(Scheme.forValue(PROTOCOL))

  override def apiDocsPath = "api-docs"

  val apiKey = new ApiKeyAuthDefinition("api_key", QUERY)
  override val securitySchemeDefinitions: Map[String, ApiKeyAuthDefinition] = Map("apiKey" -> apiKey)

  override def info =
    new Info(
      "Hello World API - End User",
      "1.0",
      "Swagger",
      "",
      None,
      None,
      Map.empty)
}
