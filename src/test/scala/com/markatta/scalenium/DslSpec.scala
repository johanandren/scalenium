package com.markatta.scalenium

import org.specs2.mutable.Specification
import org.openqa.selenium.firefox.FirefoxDriver

class DslSpec extends Specification {

  def mustCompile {
    // copy of the examples in the README, must compile
    // if anything is changed here, make sure you change the
    // readme as well

    // ex 1
    val b = new Browser(new FirefoxDriver())

    // ex 2
    {
      b.find(".cssClass") // returns a Seq[Element]
      b.first(".cssClass") // returns an Option[Element]

      // aliases to find, for an additional style, see the JQuery syntax example below
      b.all(".cssClass")
      b.select(".cssClass")

      b.find(".someClass").isEmpty must beTrue
      b.find(".someClass") must haveSize(0)
      b.first(".someClass").isEmpty must beTrue
      b.find(".someClass").exists(_.id == "someId") must beTrue
      b.find(".someClass").size must equalTo(3)

      // Element contains the same ops as browser for searching so
      // these two gives the same result
      b.first(".someClass").get.first(".someOtherClass")
      b.first(".someClass > .someOtherClass")

      // Seq[Element] is implicitly converted to an ElementSeq by
      // import com.markatta.scalenium.seqOfElements2ElementSeq
      // allowing us to:

      b.find(".cssClass").hidden must beTrue // all elements matching .cssClass hidden
      b.find(".someClass > .someOtherClass").anySelected must beTrue
      b.find("li").texts must contain("Banana", "Pineapple")
      b.find("ul").find("li").size must equalTo(4)

      b.first("#it").isDefined must beTrue
      b.first("#it").map(_.visible).getOrElse(false) must beTrue
    }

    // ex 3
    {
      implicit val brower = b
      import JqueryStyle._
      $("#someId").visible must beTrue
      $("ul > li").find(li => li.text == "Banana" && li.visible).isDefined must beTrue
    }

    // ex 4
    {
      b.write("newPassword").into("input[name='password']")
      b.write("happy").intoAll("input[type='text']")

      b.first("input[name='password']").get.write("newPassword")
      b.find("input[name='password']").write("newPassword")

      // fill entire form
      // identifying fields with name
      b.fillByName("user" -> "johndoe", "password" -> "newPasword")
      // id
      b.fillById("user" -> "johndoe", "password" -> "newPassword")
      // or css selector
      b.fill("input#user" -> "johndoe", "input[name='password']" -> "")
    }

    // ex 5
    {
      b.waitFor(".someClass").toBecomeVisible()
      b.waitAtMost(5).secondsFor(".someClass").toBecomeVisible()

      b.waitFor(1 == 2).toBecomeTrue()
      b.waitAtMost(3000000).secondsFor(1 == 2).toBecomeTrue

      b.waitAtMost(5).secondsFor(b.find("button").disabled).toBecomeTrue
    }

    // ex 6
    {
      implicit val timeout = Timeout(3).seconds
      implicit val interval = Interval(100).ms

      b.waitFor(".someClass").toBecomeVisible()
    }
  }

  // TODO figure out how to create tests for the library

}
