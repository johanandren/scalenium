package com.markatta.scalenium

trait Navigation { this: HasDriver =>

  def goTo(url: String): this.type = {
    require(url != "", "URL must not be empty")
    driver.get(url)
    this
  }

}
