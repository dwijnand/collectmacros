package collectmacros

sealed trait A

sealed trait B extends A
case object B2 extends A

sealed trait C extends B
case object C2 extends B

case object D extends C

object Usage {
  def main(args: Array[String]): Unit = {
    assertEquals(xList(1, 2, 3), List(1, 2, 3))
    assertEquals(xList(1, 2, 3), 1 :: 2 :: 3 :: Nil)
    assertEquals(xList(B2, C2, D), List(B2, C2, D))
    assertEquals(xList(B2, C2, D), B2 :: C2 :: D :: Nil)
    assertEquals(xVector(1, 2, 3), Vector(1, 2, 3))
    assertEquals(xVector(B2, C2, D), Vector(B2, C2, D))
  }

  def assertEquals[T](l: T, r: T) = {
    if (l != r) {
      println(s"! $l != $r")
    }
  }
}
