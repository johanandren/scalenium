package com.markatta

package object scalenium {

  implicit def seqOfElements2ElementSeq(elements: Seq[Element]): ElementSeq = new ElementSeq(elements)

  implicit val defaultTimeout = Timeout(Timespan.sToMs(5))
  implicit val defaultInterval = Interval(250)

}
