
import com.example.zioinsight.presentation.CreatingAZIOService.{MyService, ServiceA}
import zio._

import java.io.IOException

object MainApp extends ZIOAppDefault {
  val app =
    for {
      myService <- ZIO.service[MyService]
      result <- myService.myServiceMethod()
      _ <- Console.printLine(s"my service method result: $result")
    } yield ()


  val combiningLayers: ZLayer[Any with ServiceA, Nothing, ServiceA with MyService] = ServiceA.layer ++ MyService.live
  val passingInLayers: ZLayer[Any, Any, MyService] = ServiceA.layer >>> MyService.live


  // end of world approach - provide all dependencies at the end
  def run =
    app.provide (
      MyService.live,
      ServiceA.layer
    )
}