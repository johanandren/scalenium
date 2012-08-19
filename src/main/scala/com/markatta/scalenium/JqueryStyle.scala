package com.markatta.scalenium

object JqueryStyle {
  def $(cssSelector: String)(implicit browser: Browser): Seq[Element] = browser.find(cssSelector)
}
