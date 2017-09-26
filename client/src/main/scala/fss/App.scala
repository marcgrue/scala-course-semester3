package fss

import autowire._
import org.scalajs.dom.document.location
import org.scalajs.dom.html.{Button, Input}
import org.scalajs.dom.{Event, document, _}
import services.hotels.HotelsService

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("App")
class App extends JSApp {

  def hotelsTables() = document.querySelector("table")
  def destination() = document.getElementById("destination").asInstanceOf[Input]
  def distance() = document.getElementById("distance").asInstanceOf[Input]
  def searchButton() = document.getElementById("load-hotels").asInstanceOf[Button]

  val webSocket = new WebSocket(s"ws://${location.hostname}:${location.port}/WebSocket/user")

  @JSExport
  def main(): Unit = {

    new Autocomplete(
      document.getElementById("destination").asInstanceOf[Input],
      Seq("London", "Paris", "Bath"),
      _ => handleChange(null)
    )

    destination().onkeyup = handleChange _
    distance().onkeyup = handleChange _
    distance().onchange = handleChange _

    webSocket.onmessage = (e: MessageEvent) => console.log(e.data.toString)

    searchButton().style.display = "none"
  }

  def handleChange(e: Event) = {
    val dest = destination().value
    webSocket.send(dest)
    reload(dest, distance().value.toDouble)
  }

  def reload(destination: String, distance: Double) = {
    for {
      hotels <- Client[HotelsService].search(destination, distance).call()
      table = views.html.hotelsTable(hotels).body
    } hotelsTables().outerHTML = table
  }
}


