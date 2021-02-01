package net.asachdeva

import cats.effect._
import cats.implicits._
import debug._

object Parallelism extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- IO(s"number of CPUs: $numCpus").debug
      _ <- tasks.debug
    } yield ExitCode.Success

  val numCpus               = Runtime.getRuntime().availableProcessors()
  val tasks                 = List.range(0, numCpus * 2).parTraverse(task)
  def task(i: Int): IO[Int] = IO(i).debug
}
