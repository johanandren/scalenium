package com.markatta.scalenium

import scala.concurrent.duration._
import scala.annotation.tailrec

object Await {

  /** Run until check returns true or timeout from now is passed
    * @return
    */
  private def becameTrue(timeout: Timeout, interval: Interval, check: => Boolean): Boolean = {
    val end = System.currentTimeMillis + timeout.inner.toMillis

    @tailrec
    def loop(until: Long, interval: FiniteDuration, predicate: => Boolean): Boolean = {
      if (System.currentTimeMillis() > end) {
        false
      } else if (predicate) {
        true
      } else {
        Thread.sleep(interval.toMillis)
        loop(until, interval, predicate)
      }
    }

    loop(end, interval.inner, check)
  }

}

trait Await { this: MarkupSearch =>
  import Await._

  implicit val defaultTimeout = Timeout(5.seconds)
  implicit val defaultInterval = Interval(250.millis)


  class UntilCssSelector(cssSelector: String, timeout: Timeout, pollingInterval: Interval) {
    def toBecomeVisible[A]()(implicit failureHandler: TimeoutFailureHandler): Unit =  {
      if (!becameTrue(timeout, pollingInterval, first(cssSelector).isDefined)) {
        failureHandler.fail(timeout, "element matching '" + cssSelector + "' never became visible")
      }
    }
    def toDisappear()(implicit failureHandler: TimeoutFailureHandler): Unit =  {
      if (!becameTrue(timeout, pollingInterval, first(cssSelector).isEmpty)) {
        failureHandler.fail(timeout, "element matching '" + cssSelector + "' never disappeared")
      }
    }
  }

  class UntilPredicate(predicate: => Boolean, timeout: Timeout, pollingInterval: Interval) {
    def toBecomeTrue()(implicit failureHandler: TimeoutFailureHandler): Unit = {
      if (!becameTrue(timeout, pollingInterval, predicate)) {
        failureHandler.fail(timeout, "expression never became true")
      }
    }
    def toBecomeFalse()(implicit failureHandler: TimeoutFailureHandler): Unit =  {
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
  class WaitAtMostBuilder private[Await](n: Long) {
    def secondsFor(predicate: => Boolean)(implicit interval: Interval) = new UntilPredicate(predicate, new Timeout(n.seconds), interval)
    def secondsFor(cssSelector: String)(implicit interval: Interval) = new UntilCssSelector(cssSelector, new Timeout(n.seconds), interval)
    def msFor(predicate: => Boolean)(implicit interval: Interval) = new UntilPredicate(predicate, new Timeout(n.millis), interval)
    def msFor(cssSelector: String)(implicit interval: Interval) = new UntilCssSelector(cssSelector, new Timeout(n.millis), interval)

  }
  def waitAtMost(n: Long) = new WaitAtMostBuilder(n)

}
