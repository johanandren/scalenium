scalenium
=========

A Scala-ified fluent wrapper for selenium inspired but FluentLenium but making use of the scala collection API:s and all the fun DSL:y stuff possible with Scala.


Using the library
-----------------

TODO publish artifact to repo on github so it can be used through sbt etc.

For now, you'll have to clone, build and use the built jar


Setting up base object of library
---------------------------------

Everything in the API is available through the class com.markatta.scalenium.Browser

You can create a Browser instance like this (of course you may use any other selenium driver):
```
val browser = new Browser(new FirefoxDriver())
```

Selecting elements in a page
----------------------------

Is done with a css selector, here are some samples of how it would look in specs2 tests

```
val b: Browser = ...

b.find(".cssClass") // returns a Seq[Element]
b.first(".cssClass") // returns an Option[Element]

b.find(".someClass").isEmpty must beTrue
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

b.find("#cssId").allAreHidden must beTrue
b.find(".someClass > .someOtherClass").anyIsSelected must beTrue
b.find("li").texts must contain("Banana", "Pineapple")
b.find("ul").find("li").size must equalTo(4)

b.first("#it").isDefined must beTrue
b.first("#it").map(_.isVisible).getOrElse(false) mustBeTrue
```


Waiting for asynchronous javascript logic
-----------------------------------------
In many cases we want to wait for some asynchronous operation for a while before
failing a test:

```
import com.markatta.scalenium._
val b: Browser = ...

b.waitFor(".someClass").toBecomeVisible()
b.waitAtMost(5).secondsFor(".someClass").toBecomeVisible()

b.waitFor(1 == 2).toBecomeTrue()
b.waitAtMost(3000000).secondsFor(1 == 2).toBecomeTrue

b.waitAtMost(5).secondsFor(b.find("button").allAreDisabled).toBecomeTrue
```
