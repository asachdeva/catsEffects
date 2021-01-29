package net
package asachdeva

import scala.concurrent.duration.FiniteDuration

import java.util.concurrent.TimeUnit

case class MyIO[A](unsafeRun: () => A) {
  def map[B](f: A => B): MyIO[B]           = MyIO(() => f(unsafeRun()))
  def flatMap[B](f: A => MyIO[B]): MyIO[B] = MyIO(() => f(unsafeRun()).unsafeRun())
}

object MyIO {
  def putStr(s: => String): MyIO[Unit] = MyIO(() => println(s))
}

object Timing extends App { self =>

  val clock: MyIO[Long] = MyIO(() => System.currentTimeMillis())

  def time[A](action: MyIO[A]): MyIO[(FiniteDuration, A)] =
    for {
      start <- clock
      a     <- action
      stop  <- clock
    } yield (FiniteDuration(stop - start, TimeUnit.MILLISECONDS), a)

  val timedHello = Timing.time(MyIO.putStr("hello"))
  timedHello.unsafeRun() match {
    case (duration, _) => println(s"'hello' took $duration")
  }
}
