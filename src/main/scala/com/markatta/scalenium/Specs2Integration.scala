package com.markatta.scalenium

import org.specs2.execute.{Failure, FailureException}

/** To avoid bringin in hard dependencies on specs2 across the entire library
  * all integration should be kept in this module (and possibly broken out into
  * a separate library)
  */
object Specs2Integration {

  /**
   *
   */
  implicit val specs2FailureHandler = new FailureHandler {
    def fail(timeout: Timeout, msg: String) {
      throw new FailureException(Failure("Waited " + timeout.humanText + " but " + msg))
    }
  }
}
