package com.markatta.scalenium

import org.specs2.execute.{Failure, FailureException}

/** To avoid bringin in hard dependencies on specs2 across the entire library
  * all integration should be kept in this module (and possibly broken out into
  * a separate library)
  */
object Specs2Integration {

  /**
   * Throw failures as FailureExceptions that specs2 will treat as failures instead
   * of errors.
   */
  implicit object specs2FailureHandler extends MissingElementFailureHandler with TimeoutFailureHandler {

    def noSuchElement(msg: String): Unit = {
      throw new FailureException(Failure(msg))
    }

    def fail(timeout: Timeout, msg: String): Unit = {
      throw new FailureException(Failure("Waited " + Time.humanText(timeout.inner) + " but " + msg))
    }

  }
}
