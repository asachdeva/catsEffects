package net.asachdeva

import cats.effect.IO
import cats.implicits._

import scala.concurrent._
import scala.concurrent.duration._

object Future1 extends App {
  implicit val ec: ExecutionContext = ExecutionContext.global

  val hello = Future(println(s"${Thread.currentThread.getName} hello"))
  val world = Future(println(s"${Thread.currentThread.getName} world"))

  val hw1: Future[Unit] =
    for {
      _ <- hello
      _ <- world
    } yield ()

  Await.ready(hw1, 1.seconds)

  val hw2: Future[Unit] =
    (hello, world).mapN((_, _) => ())

  Await.ready(hw2, 1.seconds)
}

object Future2 extends App {
  implicit val ec: ExecutionContext = ExecutionContext.global

  def hello = Future(println(s"${Thread.currentThread.getName} hello"))
  def world = Future(println(s"${Thread.currentThread.getName} world"))

  val hw1: Future[Unit] =
    for {
      _ <- hello
      _ <- world
    } yield ()

  Await.ready(hw1, 1.seconds)

  val hw2: Future[Unit] =
    (hello, world).mapN((_, _) => ())

  Await.ready(hw2, 1.seconds)
}

object IOComposition extends App {

  val hello = IO(println(s"${Thread.currentThread.getName} hello"))
  val world = IO(println(s"${Thread.currentThread.getName} world"))

  val hw1: IO[Unit] =
    for {
      _ <- hello
      _ <- world
    } yield ()

  val hw2: IO[Unit] =
    (hello, world).mapN((_, _) => ())

  hw1.unsafeRunSync()
  hw2.unsafeRunSync()
}
