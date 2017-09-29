package views
import model.Hotel

import scalatags.Text.all._

object HotelListingTable{

  def gmapsLink(hotel: Hotel) ={
    import hotel._, coordinates._
    s"http://maps.google.com/?q=$name near $lat,$long"
  }

  def apply(searchResults: Seq[Hotel]) = {
    table(id := "hotels", `class` := "table",
      thead(
        tr(
          th("Name"),
          th("Location"),
          th("Images")
        )
      ),
      tbody(
        for(hotel <- searchResults) yield {
          tr(
            td(hotel.name),
            td(a(href := gmapsLink(hotel), "Map")),
            td(
              for(image <- hotel.images) yield {
                img(height := 200, src := image)
              }
            )
          )
        }
      )
    )
  }

}
