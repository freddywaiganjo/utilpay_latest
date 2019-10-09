name := "utilpay"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.18",
  "jasperreports" % "jasperreports" % "3.5.3"
)     

play.Project.playJavaSettings


