package com.example.zioinsight.presentation

import zio._

object C_TheZLayer {

  /*
  ZIO's answers to dependency management

   - ZLayer[RIn, E, ROut]
   */

  trait ServiceA {
    def methodA(): Task[Unit]
  }
  object ServiceA {
    private final class ServiceALive extends ServiceA {
      override def methodA(): Task[Unit] = ???
    }
   val layer = ZLayer.fromZIO {
     ZIO.succeed(new ServiceALive)
   }
  }

  trait ServiceB {
    def methodB(): Task[Unit]
  }
  object ServiceB {
    private final class ServiceBLive extends ServiceB {
      override def methodB(): Task[Unit] = ???
    }
    val layer = ZLayer.fromZIO {
      ZIO.succeed(new ServiceBLive)
    }
  }



  trait MyService {
    def myMethod():Task[Unit]
  }
  object MyService {
    private final class MyServiceLive(serviceA: ServiceA, serviceB: ServiceB) extends MyService {
      override def myMethod(): Task[Unit] = ???
    }

    val live = ZLayer {
      for {
        serviceA <- ZIO.service[ServiceA]
        serviceB <- ZIO.service[ServiceB]
      } yield new MyServiceLive(serviceA, serviceB)
    }
  }

  val combineLayers = ServiceA.layer ++ ServiceB.layer
  val buildLayer = combineLayers >>> MyService.live
  val passThrough = (ServiceA.layer ++ ServiceB.layer) >+> MyService.live

}
