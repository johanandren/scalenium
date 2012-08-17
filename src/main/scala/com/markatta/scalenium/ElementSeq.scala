package com.markatta.scalenium

class ElementSeq(elements: Seq[Element]) {

  /** Click on all elements on the list
    * Only the visible elements are filled
    */
  def click()  {
    elements.foreach(_.click())
    this
  }

  /** Clear all visible elements in the list
   */
  def clearAll() {
    elements.filter(_.isEnabled).foreach(_.clear())
  }

  def ids: Seq[String] = elements.map(_.id)

  def texts: Seq[String] = elements.map(_.text)
  def text: String = elements.map(_.text).mkString
  def values: Seq[String] = elements.map(_.value)
  def html: String = elements.map(_.html).mkString

  def anyIsEnabled = elements.exists(_.isEnabled)
  def anyIsDisabled = elements.exists(!_.isEnabled)
  def allAreEnabled = elements.forall(_.isEnabled)
  def allAreDisabled = elements.forall(!_.isEnabled)

  def anyIsVisible = elements.exists(_.visible)
  def anyIsHidden = elements.exists(!_.visible)
  def allAreDisplayed = elements.forall(_.visible)
  def noneIsHidden = allAreDisabled
  def noneisVisible = elements.forall(!_.visible)
  def allAreHidden = noneisVisible

  def anyIsSelected = elements.exists(_.selected)

  def find(cssSelector: String): Seq[Element] = elements.flatMap(_.find(cssSelector))

}
