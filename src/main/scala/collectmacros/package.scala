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

object xVector {
  def apply[A](xs: A*): Vector[A] = macro xVector.applyImpl[A]
}

final class xVector(val c: blackbox.Context) {
  import c.universe._

  private val VectorObject = q"_root_.scala.collection.immutable.Vector"

  def applyImpl[A](xs: Tree*)(implicit A: WeakTypeTag[A]) = {
    if (xs.isEmpty) q"$VectorObject.empty[$A]"
    else q"{ val b = $VectorObject.newBuilder[$A]; ..${xs map (x => q"b += $x")}; b.result() }"
  }
}

object Desugar {
  def desugar(a: Any): String = macro Desugar.desugarImpl
}

final class Desugar(val c: blackbox.Context) {
  import c.universe._
  def desugarImpl(a: Tree) = Literal(Constant(showRaw(a)))
}
