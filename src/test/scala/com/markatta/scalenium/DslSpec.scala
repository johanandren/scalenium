package com.markatta.scalenium

import org.specs2.mutable.Specification
import org.openqa.selenium.firefox.FirefoxDriver

class DslSpec extends Specification {

  def mustCompile {
    // that it compiles is the testsuite for now
    // TODO figure out how to create tests for the library
    val b = new Browser(new FirefoxDriver())

    b.find(".someClass").isEmpty must beTrue
    b.first(".someClass").isEmpty must beTrue
    b.find(".someClass").exists(_.id == "someId") must beTrue
    b.find(".someClass").size must equalTo(3)

    b.find(".someClass").find(".someOtherClass")

    b.find("ul").find("li").size must equalTo(4)

    b.find("#cssId").allAreHidden must beTrue
    b.find(".someClass > .someOtherClass").anyIsSelected must beTrue
    b.find("li").texts must contain("Banana", "Pineapple")

    b.first("#it").isDefined must beTrue
    b.first("#it").map(_.visible).getOrElse(false) must beTrue

    b.write("newPassword").into("input[name='password']")
    b.write("happy").intoAll("input[type='text']")

  }

}
