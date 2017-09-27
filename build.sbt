val collectmacros = project in file(".")

organization in ThisBuild := "com.dwijnand"
     version in ThisBuild := "0.1.0-SNAPSHOT"
    licenses in ThisBuild := Seq("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0"))
 description in ThisBuild := "Scala collection creating macros"
  developers in ThisBuild := List(Developer("dwijnand", "Dale Wijnand", "dale wijnand gmail com", url("https://dwijnand.com")))
   startYear in ThisBuild := Some(2017)
    homepage in ThisBuild := (scmInfo in ThisBuild).value map (_.browseUrl)
     scmInfo in ThisBuild := Some(ScmInfo(url("https://github.com/dwijnand/collectmacros"), "scm:git:git@github.com:dwijnand/collectmacros.git"))

scalaVersion in ThisBuild := "2.12.3"

scalacOptions in ThisBuild ++= Seq("-encoding", "utf8")
scalacOptions in ThisBuild ++= Seq(
  "-deprecation",
  "-feature",
  "-language:experimental.macros",
  "-unchecked",
  "-Xfuture",
  "-Xlint:-unused,_",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
)

libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value

cancelable in Global := true
