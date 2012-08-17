package com.markatta.scalenium

import annotation.tailrec
import Timespan._

private[scalenium] object Timespan {
  def sToMs(s: Long) = s * 1000
  def msToS(ms: Long) = ms / 1000
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
case class Timeout(ms: Long) extends Timespan
case class Interval(ms: Long) extends Timespan

// browser.await
class AwaitFailedException(msg: String) extends RuntimeException(msg)

object Await {


  private def fail(timeout: Timeout, msg: String) {
    throw new AwaitFailedException("Waited " + timeout.humanText + " but " + msg)
  }

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

  class UntilCssSelector(cssSelector: String, timeout: Timeout, pollingInterval: Interval) {
    def toBecomeVisible() {
      if (!becameTrue(timeout, pollingInterval, first(cssSelector).isDefined)) {
        fail(timeout, "element matching '" + cssSelector + "' never became visible")
      }
    }
    def toDisappear() {
      if (!becameTrue(timeout, pollingInterval, first(cssSelector).isDefined)) {
        fail(timeout, "element matching '" + cssSelector + "' never disappeared")
      }
    }
  }

  class UntilPredicate(predicate: => Boolean, timeout: Timeout, pollingInterval: Interval) {
    def toBecomeTrue() {
      if (!becameTrue(timeout, pollingInterval, predicate)) {
        fail(timeout, "expression never became true")
      }
    }
    def toBecomeFalse() {
      if (!becameTrue(timeout, pollingInterval, !predicate)) {
        fail(timeout, "expression never became false")
      }
    }
  }

  // implicit timeout
  def waitFor(predicate: => Boolean)(implicit timeout: Timeout, interval: Interval) = new UntilPredicate(predicate, timeout, interval)
  def waitFor(cssSelector: String)(implicit timeout: Timeout, interval: Interval) = new UntilCssSelector(cssSelector, timeout, interval)

  // explicit timeouts
  def waitAtMost(n: Long) = new {
    def secondsFor(predicate: => Boolean)(implicit interval: Interval) = new UntilPredicate(predicate, Timeout(sToMs(n)), interval)
    def secondsFor(cssSelector: String)(implicit interval: Interval) = new UntilCssSelector(cssSelector, Timeout(sToMs(n)), interval)
    def msFor(predicate: => Boolean)(implicit interval: Interval) = new UntilPredicate(predicate, Timeout(n), interval)
    def msFor(cssSelector: String)(implicit interval: Interval) = new UntilCssSelector(cssSelector, Timeout(n), interval)
  }

}
