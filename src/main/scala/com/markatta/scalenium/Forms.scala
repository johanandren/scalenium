package com.markatta.scalenium

trait Forms { this: MarkupSearch =>

  def write(text: String) = new {

    def into(cssSelector: String) {
      first(cssSelector).map(_.write(text)).getOrElse("Css selector '" + cssSelector + "' could not be found on page")
    }

    def intoAll(cssSelector: String) {
      find(cssSelector).foreach(_.write(text).fold(
        ex => throw ex,
        success => Unit
      ))
    }
  }
}
