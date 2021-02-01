package net.asachdeva

import debug._
import cats.effect._
import cats.implicits._
import java.io.RandomAccessFile
import scala.concurrent.duration.DurationInt

object BasicResource extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    stringResource.use { s =>
      IO(s"$s is so cool").debug
    } as (ExitCode.Success)

  val stringResource: Resource[IO, String] =
    Resource.make(
      IO("acquiring stringResource").debug *> IO("String")
    )(_ => IO("releasing stringResource").debug.void)
}

object BasicResourceFailure extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    stringResource
      .use(_ => IO.raiseError(new RuntimeException("oh no")))
      .attempt
      .debug
      .as(ExitCode.Success)

  val stringResource: Resource[IO, String] =
    Resource.make(
      IO("acquiring stringResource").debug *> IO("String")
    )(_ => IO("releasing stringResource").debug.void)
}

object ResourceBackgroundTask extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- backgroundTask.use { _ =>
             IO.sleep(200.millis) *> IO("$s is so cool!").debug
           }
      _ <- IO("done!").debug
    } yield ExitCode.Success

  val backgroundTask: Resource[IO, Unit] = {
    val loop = (IO("looping...").debug *> IO.sleep(100.millis)).foreverM

    Resource
      .make(IO("forking backGroundTask").debug *> loop.start)(
        IO("cancelling backGroundTask").debug.void *> _.cancel
      )
      .void
  }
}

class FileBufferReader private (in: RandomAccessFile) {

  def readBuffer(offset: Long): IO[(Array[Byte], Int)] =
    IO {
      in.seek(offset)

      val buf = new Array[Byte](FileBufferReader.bufferSize)
      val len = in.read(buf)

      (buf, len)
    }

  private def close: IO[Unit] = IO(in.close())
}

object FileBufferReader {
  val bufferSize = 4096

  def makeResource(fileName: String): Resource[IO, FileBufferReader] =
    Resource.make {
      IO(new FileBufferReader((new RandomAccessFile(fileName, "r"))))
    } { res =>
      res.close
    }
}

object BasicResourceComposed extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    (stringResource, intResource).tupled // just like mapN ...Resource[IO, String, Int]
      .use { case (s, i) =>
        IO(s"$s is so cool").debug *>
          IO(s"$i is also cool").debug
      }
      .as(ExitCode.Success)

  val stringResource: Resource[IO, String] =
    Resource.make(
      IO("acquiring StringResource").debug *> IO("String")
    )(_ => IO("releasing StringResource").debug.void)

  val intResource: Resource[IO, Int] =
    Resource.make(
      IO("acquiring IntResource").debug *> IO(99)
    )(_ => IO("releasing IntResource").debug.void)
}
