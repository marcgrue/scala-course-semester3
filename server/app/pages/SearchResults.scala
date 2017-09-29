package pages

import controllers.{WebJarAssets, routes}
import model.Hotel

import scalatags.Text.all._
import scalatags.Text.tags2.title


object SearchResults {

  def apply(destination: String, radius: String, searchResults: Seq[Hotel])(webJarAssets: WebJarAssets) =
    "<!DOCTYPE html>" +
      html(lang := "en",
        head(
          title(s"Hotels within $radius km of $destination"),
          link(rel := "stylesheet", href := s"${routes.WebJarAssets.at(webJarAssets.locate("css/bootstrap.css"))}"),
          script(src := s"${routes.WebJarAssets.at(webJarAssets.locate("jquery.js"))}"),
          script(src := s"${routes.WebJarAssets.at(webJarAssets.locate("js/bootstrap.js"))}"),
          script(tpe := "text/javascript", src := "/versionedAssets/client-fastopt.js")
        ),
        body(
          div(id := "container",
            p(
              form(cls := "form-inline", method := "get",
                div(cls := "form-group",
                  label(`for` := "destination", "Destination"),
                  input(tpe := "text", cls := "form-control", name := "destination", id := "destination",
                    placeholder := "e.g. London", value := destination)
                ),
                div(cls := "form-group",
                  label(`for` := "distance", "distance"),
                  input(tpe := "number", cls := "form-control", name := "distance", id := "distance",
                    placeholder := "e.g. London", min := "0.5", max := "20", step := "0.5", value := radius)
                ),
                button(id := "load-hotels", tpe := "submit", cls := "btn btn-default", "Search")
              )
            ),

            // From http://getbootstrap.com/javascript/#modals
            // Button to trigger modal
            p(
              button(id := "show-map", cls := "btn btn-primary btn-lg", data.toggle := "modal", data.target := "#mapModal",
                "Show hotels on a map"
              ),

              // Modal
              div(cls := "modal fade", id := "mapModal", tabindex := "-1", role := "dialog", aria.labelledby := "mapModalLabel",
                div(cls := "modal-dialog", role := "document",
                  div(cls := "modal-content",
                    div(cls := "modal-header",
                      button(cls := "close", data.dismiss := "modal", aria.label := "Close",
                        span(aria.hidden := "true", "x")
                      ),
                      // Custom title
                      h5(cls := "modal-title", id := "mapModalLabel")
                    ),
                    div(cls := "modal-body",
                      // This div is used in client.App.renderMap(...)
                      div(id := "map", style := "height: 500px")
                    )
                  )
                )
              )
            ),
            p(views.HotelListingTable(searchResults))
          ),
          script("App.main()")
        )
      )

}
