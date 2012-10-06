package com.markatta.scalenium

trait Forms { this: MarkupSearch =>

  def write(text: String) = new {

    def into(cssSelector: String)(implicit failureHandler: MissingElementFailureHandler) {
      first(cssSelector).map(_.write(text))
    }

    def intoAll(cssSelector: String)(implicit failureHandler: MissingElementFailureHandler) {
      find(cssSelector).foreach(_.write(text))
    }
  }

  /** Fill each field by name with the corresponding value
    * @param vals name -> value
    */
  def fillByName(vals: (String, String)*)(implicit failureHandler: MissingElementFailureHandler) {
    fill(vals, "name['" + _ + "']")(failureHandler)
  }

  /** Fill each field by id with the corresponding value
   * @param vals id -> value
   */
  def fillById(vals: (String, String)*)(implicit failureHandler: MissingElementFailureHandler) {
    fill(vals, "#" + _)(failureHandler)
  }

  /** Fill each field by css selector with the corresponding value
    * @param vals cssSelector -> value
    */
  def fill(vals: (String, String)*)(implicit failureHandler: MissingElementFailureHandler) {
    fill(vals, key => key)(failureHandler)
  }

  private def fill(vals: Seq[(String, String)], f: String => String)(failureHandler: MissingElementFailureHandler) {
    vals.foreach { case (key, value) => find(f(key)).write(value)(failureHandler)}
  }
}
