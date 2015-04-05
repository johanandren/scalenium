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

import scala.concurrent.duration._

object Timeout {
  implicit val defaultTimeout = Timeout(5.seconds)
}
case class Timeout(inner: FiniteDuration)


object Interval {
  implicit val defaultInterval = Interval(5.millis)
}
case class Interval(inner: FiniteDuration)

private[scalenium] object Time {

  def humanText(d: FiniteDuration): String =
    if (d.toSeconds > 0) d.toSeconds + "s"
    else d.toMillis + "ms"

}
