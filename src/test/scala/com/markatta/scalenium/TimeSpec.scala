package com.markatta.scalenium

import org.specs2.mutable.Specification

class TimeSpec extends Specification {

  "Timeout" should {

    "be created correctly from ms" in {
      val result = Timeout(5).ms
      result.ms mustEqual (5)
    }

    "be created correctly from s" in {
      val result = Timeout(5).seconds
      result.ms mustEqual (5000)
    }

    "calculate value in seconds" in {
      val result = Timeout(5).seconds
      result.ms mustEqual (5000)
      result.seconds mustEqual (5)
    }

    "render as seconds when at least one second" in {
      val result = Timeout(1).seconds
      result.humanText mustEqual ("1s")
    }

    "render as ms when at less than one second" in {
      val result = Timeout(999).ms
      result.humanText mustEqual ("999ms")
    }

  }

}
