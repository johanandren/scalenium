scalenium
=========
[![Build Status](https://travis-ci.org/johanandren/scalenium.svg?branch=master)](https://travis-ci.org/johanandren/scalenium)

A Scala-ified fluent wrapper for selenium webdriver heavily inspired by FluentLenium but making use
of the scala collection API:s and all the fun DSL:y stuff possible with Scala.

Please note that this library is still a proof of concept and playground for various ideas of how
a selenium API in scala should look.


Using the library
-----------------

Scalenium is available from maven central, just add it Scalenium to your dependencies:

```scala
libraryDependencies += "com.markatta" %% "scalenium" % "1.0.2"
```

Setting up base object of library
---------------------------------

Everything in the API is available through the class com.markatta.scalenium.Browser

You can create a Browser instance like this (of course you may use any other selenium driver):
```scala
import com.markatta.scalenium._
val browser = new Browser(new FirefoxDriver())
```

Selecting elements in a page
----------------------------

Is done with a css selector, here are some samples of how it would look in specs2 tests

```scala
import com.markatta.scalenium._
val b: Browser = ...

b.find(".cssClass") // returns a Seq[Element]
b.first(".cssClass") // returns an Option[Element]

// aliases to find, for an additional style, see the JQuery syntax example below
b.all(".cssClass")
b.select(".cssClass")

// use regular scala collection API ops
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
// com.markatta.scalenium.seqOfElements2ElementSeq
// allowing us to:

b.find(".cssClass").hidden must beTrue // all elements matching .cssClass hidden
b.find(".someClass > .someOtherClass").anySelected must beTrue
b.find("li").texts must contain("Banana", "Pineapple")
b.find("ul").find("li").size must equalTo(4)

b.first("#it") must beSome
b.first("#it").map(_.visible) must beSome(true)
```

JQuery style selection
----------------------
It is also possible to do selects with a jquery flavored syntax, which will use an implicit browser for searching:
```scala
import com.markatta.scalenium._
import JqueryStyle._
implicit b: Browser = ...

$("#someId").visible must beTrue
$("ul > li").exists(li => li.text == "Banana" && li.visible) must beTrue
```

Entering data into forms
------------------------
```scala
import com.markatta.scalenium._
val b: Browser = ...

b.write("newPassword").into("input[name='password']")
b.write("happy").intoAll("input[type='text']")

b.first("input[name='password']").get.write("newPassword")
// if multiple elements are matched this writes into all of them
b.find("input[name='password']").write("newPassword")


// fill entire form
// identifying fields with name
b.fillByName("user" -> "johndoe", "password" -> "newPasword")
// id
b.fillById("user" -> "johndoe", "password" -> "newPassword")
// or css selector
b.fill("input#user" -> "johndoe", "input[name='password']" -> "newPassword")
```


Waiting for asynchronous javascript logic
-----------------------------------------
In many cases we want to wait for some asynchronous operation for a while before
failing a test, the default timeout is 5 seconds, polling every 250ms:

```scala
import com.markatta.scalenium._
val b: Browser = ...

// to get specs2 test failures instead of exceptions
import Specs2Integration.specs2FailureHandler

b.waitFor(".someClass").toBecomeVisible()
b.waitAtMost(5).secondsFor(".someClass").toBecomeVisible()

b.waitFor(1 == 2).toBecomeTrue()
b.waitAtMost(3000000).secondsFor(1 == 2).toBecomeTrue

b.waitAtMost(5).secondsFor(b.find("button").allAreDisabled).toBecomeTrue
```

The default timeout and polling interval can be provided with implicit values:
```scala
import com.markatta.scalenium._
import scala.concurrent.duration._

implicit val timeout = Timeout(3.seconds)
implicit val interval = Interval(100.millis)

b.waitFor(".someClass").toBecomeVisible()
```

Note that you need to mix in NoTimeConversions into your test spec or else the Specs2 time unit classes
will interfere with the scala.concurrent.duration time units.

Error handling
--------------
TODO not so sure about this, should the error handling be set on the browser object instead, it will probably not need to change in the same browser??

Error handling is done by implicit implementations of the various XxxFailureHandler traits,
the defaults throw exceptions defined in the library which might not suit any given test framework
or your use case so good. Therefore you can define your own or use another set of predefined
failure handlers by defining them as implicits closer to where you use the framework.

Example
```scala
// to get specs2 test failures instead of exceptions
import Specs2Integration.specs2FailureHandler // <- defined as an "implicit object"

... use API:s ...
```

or
```scala
implicit val customHandler = new MissingElementFailureHandler { ... }

... use API:s ...
```


License
-------
Copyright 2012 Johan Andrén

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
