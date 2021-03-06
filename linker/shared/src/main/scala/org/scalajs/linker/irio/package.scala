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

package org.scalajs.linker

import scala.concurrent._

import scala.util.{Try, Success, Failure}

package object irio {
  private[irio] implicit class FutureOps[T](val __self: Future[T]) extends AnyVal {
    def transformWith[S](f: Try[T] => Future[S])(implicit ec: ExecutionContext): Future[S] =
      __self.map[Try[T]](Success(_)).recover { case t => Failure(t) }.flatMap(f)

    def finallyWith(f: => Future[Unit])(implicit ec: ExecutionContext): Future[T] = {
      __self.transformWith {
        case Success(x) =>
          f.map(_ => x)

        case Failure(vt) =>
          f.transform(_  => throw vt, ft => { ft.addSuppressed(vt); ft })
      }
    }
  }
}
