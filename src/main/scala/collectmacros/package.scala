package collectmacros

import scala.reflect.macros.blackbox

final class xList(val c: blackbox.Context) {
  import c.universe._

  private val NilObject = q"_root_.scala.collection.immutable.Nil"
  private val ConsType = tq"_root_.scala.collection.immutable.::"

  def applyImpl[A](xs: Tree*)(implicit A: WeakTypeTag[A]) =
    xs.foldRight(NilObject: Tree)((next, acc) => q"new $ConsType[$A]($next, $acc)")
}

final class xVector(val c: blackbox.Context) {
  import c.universe._

  private val VectorObject = q"_root_.scala.collection.immutable.Vector"

  def applyImpl[A](xs: Tree*)(implicit A: WeakTypeTag[A]) = {
    if (xs.isEmpty) q"$VectorObject.empty[$A]"
    else q"{ val b = $VectorObject.newBuilder[$A]; ..${xs map (x => q"b += $x")}; b.result() }"
  }
}

final class xArray(val c: blackbox.Context) {
  import c.universe._

  private val ArrayType = tq"_root_.scala.Array"

  def applyImpl[A](xs: Tree*)(implicit A: WeakTypeTag[A]) = {
    if (xs.isEmpty) q"new $ArrayType[$A](0)"
    else {
      val assignments = xs.zipWithIndex map { case (x, idx) => q"a($idx) = $x" }
      q"{ val a = new $ArrayType[$A](${xs.size}); ..$assignments; a }"
    }
  }
}

object Desugar {
  def desugar(a: Any): String = macro Desugar.desugarImpl
}

final class Desugar(val c: blackbox.Context) {
  import c.universe._
  def desugarImpl(a: Tree) = Literal(Constant(showRaw(a)))
}

object `package` {

  implicit class ListExt(val companion: List.type) {
    def construct[A](xs: A*): List[A] = macro xList.applyImpl[A]
  }

  implicit class VectorExt(val companion: Vector.type) {
    def construct[A](xs: A*): Vector[A] = macro xVector.applyImpl[A]
  }

  implicit class ArrayExt(val companion: Array.type) {
    def construct[A](xs: A*): Array[A] = macro xArray.applyImpl[A]
  }

}
