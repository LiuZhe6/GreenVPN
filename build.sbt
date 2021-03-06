scalaVersion := "2.11.8"

enablePlugins(AndroidApp)
android.useSupportVectors

versionCode := Some(1)
version := "0.1-SNAPSHOT"

instrumentTestRunner :=
  "android.support.test.runner.AndroidJUnitRunner"

platformTarget := "android-26"

javacOptions in Compile ++= "-source" :: "1.7" :: "-target" :: "1.7" :: Nil

libraryDependencies ++=
  "com.android.support" % "appcompat-v7" % "25.3.1" ::
    "com.android.support" % "design" % "25.3.1" ::
//    "com.github.dmytrodanylyk.circular-progress-button" % "library" % "1.1.3" ::
    "com.android.support.test" % "runner" % "0.5" % "androidTest" ::
    "com.android.support.test.espresso" % "espresso-core" % "2.2.2" % "androidTest" ::
    "com.google.code.gson" % "gson" % "2.8.1"::
    Nil

