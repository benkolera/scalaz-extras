package com.benkolera.scalaz.extras

import scalaz._
import std.list._
import std.tuple._
import org.specs2.mutable._

class DisjunctionSpec extends Specification {
  "Disjunction.collectDisj" should {
    "Give two empty lists for an empty list" in {
      Disjunction.collectDisj[String,Int,List]( Nil ) must_==( Nil -> Nil )
    }
    "Give one empty list for no errors" in {
      Disjunction.collectDisj[String,Int,List](
        List(\/-(1))
      ) must_==( Nil -> List(1) )
    }
    "Give one empty list for no success" in {
      Disjunction.collectDisj[String,Int,List](
        List(-\/("Broken"))
      ) must_==( List("Broken") -> Nil )
    }
    "Give two lists of all collected errors and ints" in {
      Disjunction.collectDisj[String,Int,List](
        List(-\/("Broken"),\/-(1),\/-(2),-\/("Moar fail"),\/-(3))
      ) must_==( List("Broken","Moar fail") -> List(1,2,3) )
    }
  }
}
