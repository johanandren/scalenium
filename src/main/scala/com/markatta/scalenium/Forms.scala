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

  /** Fill each field by name with the corresponding value
    * @param vals name -> value
    */
  def fillByName(vals: (String, String)*) {
    fill(vals, "name['" + _ + "']")
  }

  /** Fill each field by id with the corresponding value
   * @param vals id -> value
   */
  def fillById(vals: (String, String)*) {
    fill(vals, "#" + _)
  }

  /** Fill each field by css selector with the corresponding value
    * @param vals cssSelector -> value
    */
  def fill(vals: (String, String)*) {
    fill(vals, key => key)
  }

  private def fill(vals: Seq[(String, String)], f: String => String) {
    vals.foreach { case (key, value) => find(f(key)).write(value)}
  }
}
