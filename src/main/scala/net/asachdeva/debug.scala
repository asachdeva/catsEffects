package net.asachdeva

import cats.effect._

object debug {

  implicit class DebugHelper[A](ioa: IO[A]) {

    def debug: IO[A] =
      for {
        a <- ioa
        tn = Thread.currentThread.getName
        _  = println(s"[${Colorize.reversed(tn)}] $a") // <1>
      } yield a
  }
}

object Colorize {
  def apply(a: Any): String = s"${colors(a.hashCode.abs % numColors)}$a${Console.RESET}"

  def reversed(a: Any): String = s"${Console.REVERSED}${apply(a)}"

  private val colors = List(
    Console.WHITE,
    Console.BLACK + Console.WHITE_B,
    Console.RED,
    Console.GREEN,
    Console.YELLOW,
    Console.BLUE,
    Console.MAGENTA,
    Console.CYAN
  )
  private val numColors = colors.size - 1
}
