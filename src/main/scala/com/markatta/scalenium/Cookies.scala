/*
 * Copyright 2015 Johan Andrén
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
