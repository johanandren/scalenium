package com.markatta.scalenium

import org.openqa.selenium.SearchContext

trait HasSearchContext {

  def searchContext: SearchContext

}
