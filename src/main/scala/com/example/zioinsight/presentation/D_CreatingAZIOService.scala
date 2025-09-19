package com.example.zioinsight.presentation

import zio.{&, ZIO, ZLayer}

object D_CreatingAZIOService {


  trait DependencyA {
    def someMethodA(x: String): ZIO[Any, Throwable, Boolean]
  }
  object DependencyA {
  }


  trait DependencyB {
    def someMethodB(x: String): ZIO[Any, Throwable, Boolean]
  }
  object DependencyB {
  }




  trait MyService {
    def aServiceMethod(x: String): ZIO[Any, Throwable, String]
  }

  final class MyServiceLive(dependencyA: DependencyA, dependencyB: DependencyB) extends MyService {
    override def aServiceMethod(x: String): ZIO[Any, Throwable, String] = {
      for {
        b1 <- dependencyA.someMethodA(x)
        b2 <- dependencyB.someMethodB(x)
      } yield if(b1 && b2) "true" else "false"
    }
  }

  object MyService {
    val live: ZLayer[DependencyA with DependencyB, Nothing, MyService] =
      ZLayer {
        for {
          depA <- ZIO.service[DependencyA]
          depB <- ZIO.service[DependencyB]
        } yield new MyServiceLive(depA, depB)
      }
  }
}
