package collectmacros

import scala.reflect.macros.blackbox

object xList {
  def apply[A](xs: A*): List[A] = macro xList.applyImpl[A]
}

final class xList(val c: blackbox.Context) {
  import c.universe._

  private val NilObject = q"_root_.scala.collection.immutable.Nil"
  private val ConsType = tq"_root_.scala.collection.immutable.::"

  def applyImpl[A](xs: Tree*)(implicit A: WeakTypeTag[A]) =
    xs.foldRight(NilObject: Tree)((next, acc) => q"new $ConsType[$A]($next, $acc)")
}

object Desugar {
  def desugar(a: Any): String = macro Desugar.desugarImpl
}

final class Desugar(val c: blackbox.Context) {
  import c.universe._
  def desugarImpl(a: Tree) = Literal(Constant(showRaw(a)))
}
