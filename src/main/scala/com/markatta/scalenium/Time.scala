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
