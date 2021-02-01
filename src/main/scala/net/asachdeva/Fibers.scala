package net.asachdeva

import debug._
import cats.effect._

import scala.concurrent.duration._

object Fibers extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- task.start //start an effect -- current execution is forked
      _ <- IO("task was started").debug
    } yield ExitCode.Success

  val task: IO[String] =
    IO("task").debug
}

object Join extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    for {
      fiber <- task.start //start an effect -- current execution is forked
      _     <- IO("pre-join").debug
      _     <- fiber.join.debug // Prints task twice ..2 debugs
      _     <- IO("post-join").debug
    } yield ExitCode.Success

  val task: IO[String] =
    IO.sleep(2.seconds) *> IO("task").debug
}

object Cancel extends IOApp {
  import cats.effect.implicits._

  def run(args: List[String]): IO[ExitCode] =
    for {
      fiber <- task.onCancel(IO("i was cancelled").debug.void).start // call back
      _     <- IO("pre-cancel").debug
      _     <- fiber.cancel // Prints task twice ..2 debugs
      _     <- IO("cancelled").debug
    } yield ExitCode.Success

  val task: IO[String] =
    IO("task").debug *> IO.never // Non terminating effect
}

object Timeout extends IOApp {
  import cats.effect.implicits._

  def run(args: List[String]): IO[ExitCode] =
    for {
//      _ <- task.timeout(timeout)
      done <- IO.race(task, timeout)
      _ <- done match {
             case Left(_)  => IO("    task: won").debug
             case Right(_) => IO("timeout: won").debug
           }
    } yield ExitCode.Success

  val task: IO[Unit]    = annotatedSleep("    task", 100.millis)
  val timeout: IO[Unit] = annotatedSleep("timeout", 500.millis)

  def annotatedSleep(name: String, duration: FiniteDuration): IO[Unit] =
    (
      IO(s"$name starting").debug *>
        IO.sleep(duration) *>
        IO(s"$name done").debug
    ).onCancel(IO(s"$name cancelled").debug.void).void

}
