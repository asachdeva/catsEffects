package net.asachdeva

import debug._
import cats.effect._
import cats.implicits._

object DebugExample extends IOApp {
  def run(args: List[String]): IO[ExitCode] = seq.as(ExitCode.Success)

  val hello = IO("hello").debug
  val world = IO("world").debug

  val seq =
    (hello, world)
      .mapN((h, w) => s"$h $w")
      .debug
}

object ParMapN extends IOApp {
  def run(args: List[String]): IO[ExitCode] = par.as(ExitCode.Success)

  val hello = IO("hello").debug
  val world = IO("world").debug

  val par =
    (hello, world)
      .parMapN((h, w) => s"$h $w")
      .debug
}

object ParMapNErrors extends IOApp {

  import scala.concurrent.duration._

  def run(args: List[String]): IO[ExitCode] =
    e1.attempt.debug *> // attempt transforms an IO[A] into an IO[Either[Throwable, A]]
      IO("---").debug *>
      e2.attempt.debug *>
      IO("---").debug *>
      e3.attempt.debug *>
      IO.pure(ExitCode.Success)

  val ok = IO("hi").debug

  val ko1 =
    IO.sleep(1.second).as("ko1").debug *>
      IO.raiseError[String](new RuntimeException("oh!")).debug
//  val ko1 = IO.raiseError[String](new RuntimeException("oh")).debug
  val ko2 = IO.raiseError[String](new RuntimeException("no")).debug

//  val e1 = (ok, ko1).parMapN((_, _) => ())
//  val e2 = (ko1, ok).parMapN((_, _) => ())
//  val e3 = (ko1, ko2).parMapN((_, _) => ())

  val e1 = (ok, ko1).parTupled.void
  val e2 = (ko1, ok).parTupled.void
  val e3 = (ko1, ko2).parTupled.void

}

object ParTraverse extends IOApp {

  def run(args: List[String]): IO[ExitCode] = tasks.parTraverse(task).debug.as(ExitCode.Success)

  val numTasks         = 100
  val tasks: List[Int] = List.range(0, numTasks)

  def task(id: Int): IO[Int] = IO(id).debug
}

object ParSequence extends IOApp {

  def run(args: List[String]): IO[ExitCode] = tasks.parSequence.debug.as(ExitCode.Success)

  val numTasks             = 100
  val tasks: List[IO[Int]] = List.tabulate(numTasks)(task)

  def task(id: Int): IO[Int] = IO(id).debug
}
