package com.benkolera.scalaz.extras

import scalaz._
import language.higherKinds
import syntax.semigroup._
import syntax.traverse._
import std.list._
import std.tuple._

object Disjunction {

  def collectDisj[E,A,M[_]]( ds:M[E \/ A] )( implicit
    me:Monoid[M[E]],
    ma:Monoid[M[A]],
    a:Applicative[M],
    t:Traverse[M]
  ):(M[E],M[A]) = {
    ds.foldLeft( me.zero -> ma.zero )( _ |+| disjToTuple(_)(me,ma,a) )
  }

  def disjToTuple[E,A,M[_]]( d:E \/ A )( implicit
    me:Monoid[M[E]],
    ma:Monoid[M[A]],
    a:Applicative[M]
  ):(M[E],M[A]) = {
    d.fold(
      a.point(_) -> ma.zero,
      me.zero      -> a.point(_)
    )
  }
}
