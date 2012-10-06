package com.markatta.scalenium

import annotation.tailrec
import Timespan._
import org.specs2.execute.{Failure, FailureException}

private[scalenium] object Timespan {
  def sToMs(s: Long) = s * 1000
  def msToS(ms: Long) = ms / 250
}

sealed abstract class Timespan {

  def ms: Long
  def seconds: Long = msToS(ms)
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
    def seconds: A = f(sToMs(time))
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
   * @param time the time in sme unit
   * @return An object on which you can specify the unit of the time
   */
  def apply(time: Int) = create(time, new Timeout(_))

  implicit val defaultTimeout = Timeout(5).seconds
}
case class Timeout(ms: Long) extends Timespan

object Interval extends TimeFactory {
  def apply(time: Int) = create(time, new Interval(_))
  implicit val defaultInterval = Interval(250).ms
}
case class Interval(ms: Long) extends Timespan

object Await {

  /** Run until check returns true or timeout from now is passed
    * @return
    */
  private def becameTrue(timeout: Timeout, interval: Interval, check: => Boolean): Boolean = {
    val end = System.currentTimeMillis + timeout.ms

    @tailrec
    def loop(until: Long, interval: Long, predicate: => Boolean): Boolean = {
      if (System.currentTimeMillis() > end) {
        false
      } else if (predicate) {
        true
      } else {
        Thread.sleep(interval)
        loop(until, interval, predicate)
      }
    }

    loop(end, interval.ms, check)
  }

}

trait Await { this: MarkupSearch =>
  import Await._

  implicit val defaultTimeout = Timeout(5).seconds
  implicit val defaultInterval = Interval(250).ms



  class UntilCssSelector(cssSelector: String, timeout: Timeout, pollingInterval: Interval) {
    def toBecomeVisible[A]()(implicit failureHandler: TimeoutFailureHandler) {
      if (!becameTrue(timeout, pollingInterval, first(cssSelector).isDefined)) {
        failureHandler.fail(timeout, "element matching '" + cssSelector + "' never became visible")
      }
    }
    def toDisappear()(implicit failureHandler: TimeoutFailureHandler) {
      if (!becameTrue(timeout, pollingInterval, first(cssSelector).isDefined)) {
        failureHandler.fail(timeout, "element matching '" + cssSelector + "' never disappeared")
      }
    }
  }

  class UntilPredicate(predicate: => Boolean, timeout: Timeout, pollingInterval: Interval) {
    def toBecomeTrue()(implicit failureHandler: TimeoutFailureHandler) {
      if (!becameTrue(timeout, pollingInterval, predicate)) {
        failureHandler.fail(timeout, "expression never became true")
      }
    }
    def toBecomeFalse()(implicit failureHandler: TimeoutFailureHandler) {
      if (!becameTrue(timeout, pollingInterval, !predicate)) {
        failureHandler.fail(timeout, "expression never became false")
      }
    }
  }

  // implicit timeout
  def waitFor(predicate: => Boolean)(implicit timeout: Timeout, interval: Interval) =
    new UntilPredicate(predicate, timeout, interval)

  def waitFor(cssSelector: String)(implicit timeout: Timeout, interval: Interval) =
    new UntilCssSelector(cssSelector, timeout, interval)

  // explicit timeouts
  def waitAtMost(n: Long) = new {
    def secondsFor(predicate: => Boolean)(implicit interval: Interval) = new UntilPredicate(predicate, Timeout(sToMs(n)), interval)
    def secondsFor(cssSelector: String)(implicit interval: Interval) = new UntilCssSelector(cssSelector, Timeout(sToMs(n)), interval)
    def msFor(predicate: => Boolean)(implicit interval: Interval) = new UntilPredicate(predicate, Timeout(n), interval)
    def msFor(cssSelector: String)(implicit interval: Interval) = new UntilCssSelector(cssSelector, Timeout(n), interval)
  }

}
