/*
 * Scala.js (https://www.scala-js.org/)
 *
 * Copyright EPFL.
 *
 * Licensed under Apache License 2.0
 * (https://www.apache.org/licenses/LICENSE-2.0).
 *
 * See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.
 */

package org.scalajs.testsuite.javalib

package object util {

  implicit private[util] class CompareNullablesOps(private val self: Any)
      extends AnyVal {

    @inline
    def ===(that: Any): Boolean =
      if (self.asInstanceOf[AnyRef] eq null) that.asInstanceOf[AnyRef] eq null
      else self.equals(that)
  }

  private[util] final case class Box[+K](inner: K) {
    def apply(): K = inner

    override def equals(o: Any): Boolean = {
      o match {
        case o: Box[_] => inner === o.inner
        case _         => false
      }
    }

    override def hashCode(): Int =
      if (inner == null) 0
      else inner.hashCode
  }

}
