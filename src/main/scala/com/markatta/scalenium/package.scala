package com.markatta

package object scalenium {

  implicit def seqOfElements2ElementSeq(elements: Seq[Element]): ElementSeq = new ElementSeq(elements)

}
