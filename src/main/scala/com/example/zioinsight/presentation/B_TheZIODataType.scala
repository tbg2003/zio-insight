package com.example.zioinsight.presentation

import zio._

object B_TheZIODataType {

  trait R
  trait E
  trait A

  /*
  the zio data type takes 3 type parameters ZIO[R, E, A]
    - R is the environment
    - E is the error
    - A is the success result
   */

  val myZIO: ZIO[R, E, A] = ???

  /*
  This can be thought of as a function:
    R => Either[E, A]
   */


  /*
  Creating ZIOs
   */
  val sInt = ZIO.succeed(42)
  val fString = ZIO.fail("Something went so terribly wrong")

  val zOption = ZIO.fromOption(Some(2))

  val zOptionWithError = zOption.mapError(_ => "Option had no value")

}
