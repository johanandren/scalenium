package com.markatta.scalenium

import scala.collection.breakOut
import scala.collection.JavaConversions._
import java.util.Date

trait Cookies { this: HasDriver =>

  def cookies: Map[String, Cookie] = driver.manage().getCookies.map(c => (c.getName, Cookie(c)))(breakOut)

}

object Cookie {
  def apply(c: org.openqa.selenium.Cookie): Cookie =
    new Cookie(c.getName, c.getDomain, c.getPath, c.getExpiry, c.getValue, c.isSecure)
}
case class Cookie(name: String, domain: String, path: String, expiry: Date, value: String, secure: Boolean)
