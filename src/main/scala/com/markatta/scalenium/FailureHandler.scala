package com.markatta.scalenium


/* Allows for customization of how failures are handled to adapt
 * the library to a specific testing framework.
 */

class MissingElementException(message: String) extends RuntimeException(message)
class AwaitFailedException(msg: String) extends RuntimeException(msg)

/** default simple failure handler, will throw an [[com.markatta.scalenium.AwaitFailedException]],
  * see [[com.markatta.scalenium.Specs2Integration]] for an example of
  * testing framework integration */
object SimpleFailureHandler extends MissingElementFailureHandler with TimeoutFailureHandler {

  def fail(timeout: Timeout, msg: String) {
    throw new AwaitFailedException("Waited " + timeout.humanText + " but " + msg)
  }

  /** fail with an explanation */
  def noSuchElement(msg: String) {
    throw new MissingElementException(msg)
  }
}


object MissingElementFailureHandler {
  implicit val defaultMissingElementHandler = SimpleFailureHandler
}

trait MissingElementFailureHandler {
  /**
   * An element was asked for but not found inside the page
   *
   * @param elementSelector The css selector that was missing
   * @return
   */
  def noSuchElement(elementSelector: String)
}

object TimeoutFailureHandler {
  implicit val defaultTimeoutFailureHandler = SimpleFailureHandler
}

trait TimeoutFailureHandler {
  /**
   * Some kind of async query timed out, an element was not shown onscreen etc.
   * @param timeout
   * @param message
   * @return
   */
  def fail(timeout: Timeout, message: String)
}

