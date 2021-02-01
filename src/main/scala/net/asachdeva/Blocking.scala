package net.asachdeva

import cats.effect._
import debug._

object Blocking extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    Blocker[IO].use { blocker => // Cannot cirectly instatiate a blocker
      withBlocker(blocker).as(ExitCode.Success)

    }

  def withBlocker(blocker: Blocker): IO[Unit] =
    for {
      _ <- IO("on default").debug
      _ <- blocker.blockOn(IO("on blocker").debug)
      _ <- IO("where am I?").debug
    } yield ()
}

object Shifting extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- IO("one").debug
      _ <- IO.shift
      _ <- IO("two").debug
      _ <- IO.shift
      _ <- IO("three").debug
      _ <- IO.shift
    } yield ExitCode.Success
}
