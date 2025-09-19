import com.example.zioinsight.presentation.D_CreatingAZIOService.{DependencyA, DependencyB, MyService}
import zio._

import java.io.IOException

object MainApp extends ZIOAppDefault {
  val app =
    for {
      myService <- ZIO.service[MyService]
      result <- myService.aServiceMethod("hello")
      _ <- Console.printLine(s"my service method result: $result")
    } yield ()

  def run =
    app.provide(
      MyService.live,
      DependencyA.layer,
      DependencyB.layer
    )
}