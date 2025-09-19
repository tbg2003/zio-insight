package com.example.zioinsight.presentation

import zio._


object CreatingAZIOService {

  trait ServiceA {
    def serviceAMethod(): ZIO[Any, Throwable, Unit]
  }
  object ServiceA {
    final class ServiceALive extends ServiceA {
      override def serviceAMethod(): ZIO[Any, Throwable, Unit] = ???
    }
    // ZLayer
    val layer = ZLayer.succeed(new ServiceALive())
  }


  trait MyService {
    def myServiceMethod(): Task[Unit] // ZIO[Any, Throwable, Unit]
  }
  object MyService {
    final class MyServiceLive(serviceA: ServiceA) extends MyService {
      override def myServiceMethod(): Task[Unit] = ???
    }
    // creating the ZLayer
    val live: ZLayer[ServiceA, Nothing, MyServiceLive] = ZLayer {
      for {
        serviceA <- ZIO.service[ServiceA]
      } yield new MyServiceLive(serviceA)
    }
    // make implemented method(s) accessible
    def myServiceMethod: ZIO[MyService, Throwable, Unit] =
      ZIO.serviceWithZIO(_.myServiceMethod())
  }


















}
