package com.markatta.scalenium

private[scalenium] object Timespan {
  def sToMs(s: Long) = s * 1000
  def msToS(ms: Long) = ms / 1000
}

sealed abstract class Timespan {

  def ms: Long
  def seconds: Long = Timespan.msToS(ms)
  def humanText = {
    val asSeconds = seconds
    if (seconds > 0)
      asSeconds + "s"
    else
      ms + "ms"
  }
}

trait TimeFactory {

  def create[A](time: Long, f: Long => A) = new {
    def seconds: A = f(Timespan.sToMs(time))
    def ms: A = f(time)
  }
}

object Timeout extends TimeFactory {

  /**
   * Sample usage:
   * {{{
   *   val timeout = Timeout(5).seconds
   *   val shorter = Timeout(10).ms
   * }}}
   *
   * @param time the time in some unit
   * @return An object on which you can specify the unit of the time
   */
  def apply(time: Long) = create(time, new Timeout(_))

  implicit val defaultTimeout = Timeout(5).seconds
}
class Timeout(val ms: Long) extends Timespan




object Interval extends TimeFactory {
  def apply(time: Long) = create(time, new Interval(_))
  implicit val defaultInterval = Interval(250).ms
}

class Interval(val ms: Long) extends Timespan
