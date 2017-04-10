@REM powerapi-cli launcher script
@REM
@REM Environment:
@REM JAVA_HOME - location of a JDK home dir (optional if java on path)
@REM CFG_OPTS  - JVM options (optional)
@REM Configuration:
@REM POWERAPI_CLI_config.txt found in the POWERAPI_CLI_HOME.
@setlocal enabledelayedexpansion

@echo off

if "%POWERAPI_CLI_HOME%"=="" set "POWERAPI_CLI_HOME=%~dp0\\.."

set "APP_LIB_DIR=%POWERAPI_CLI_HOME%\lib\"

rem Detect if we were double clicked, although theoretically A user could
rem manually run cmd /c
for %%x in (!cmdcmdline!) do if %%~x==/c set DOUBLECLICKED=1

rem FIRST we load the config file of extra options.
set "CFG_FILE=%POWERAPI_CLI_HOME%\POWERAPI_CLI_config.txt"
set CFG_OPTS=
if exist %CFG_FILE% (
  FOR /F "tokens=* eol=# usebackq delims=" %%i IN ("%CFG_FILE%") DO (
    set DO_NOT_REUSE_ME=%%i
    rem ZOMG (Part #2) WE use !! here to delay the expansion of
    rem CFG_OPTS, otherwise it remains "" for this loop.
    set CFG_OPTS=!CFG_OPTS! !DO_NOT_REUSE_ME!
  )
)

rem We use the value of the JAVACMD environment variable if defined
set _JAVACMD=%JAVACMD%

if "%_JAVACMD%"=="" (
  if not "%JAVA_HOME%"=="" (
    if exist "%JAVA_HOME%\bin\java.exe" set "_JAVACMD=%JAVA_HOME%\bin\java.exe"
  )
)

if "%_JAVACMD%"=="" set _JAVACMD=java

rem Detect if this java is ok to use.
for /F %%j in ('"%_JAVACMD%" -version  2^>^&1') do (
  if %%~j==java set JAVAINSTALLED=1
  if %%~j==openjdk set JAVAINSTALLED=1
)

rem BAT has no logical or, so we do it OLD SCHOOL! Oppan Redmond Style
set JAVAOK=true
if not defined JAVAINSTALLED set JAVAOK=false

if "%JAVAOK%"=="false" (
  echo.
  echo A Java JDK is not installed or can't be found.
  if not "%JAVA_HOME%"=="" (
    echo JAVA_HOME = "%JAVA_HOME%"
  )
  echo.
  echo Please go to
  echo   http://www.oracle.com/technetwork/java/javase/downloads/index.html
  echo and download a valid Java JDK and install before running powerapi-cli.
  echo.
  echo If you think this message is in error, please check
  echo your environment variables to see if "java.exe" and "javac.exe" are
  echo available via JAVA_HOME or PATH.
  echo.
  if defined DOUBLECLICKED pause
  exit /B 1
)


rem We use the value of the JAVA_OPTS environment variable if defined, rather than the config.
set _JAVA_OPTS=%JAVA_OPTS%
if "!_JAVA_OPTS!"=="" set _JAVA_OPTS=!CFG_OPTS!

rem We keep in _JAVA_PARAMS all -J-prefixed and -D-prefixed arguments
rem "-J" is stripped, "-D" is left as is, and everything is appended to JAVA_OPTS
set _JAVA_PARAMS=
set _APP_ARGS=

:param_loop
call set _PARAM1=%%1
set "_TEST_PARAM=%~1"

if ["!_PARAM1!"]==[""] goto param_afterloop


rem ignore arguments that do not start with '-'
if "%_TEST_PARAM:~0,1%"=="-" goto param_java_check
set _APP_ARGS=!_APP_ARGS! !_PARAM1!
shift
goto param_loop

:param_java_check
if "!_TEST_PARAM:~0,2!"=="-J" (
  rem strip -J prefix
  set _JAVA_PARAMS=!_JAVA_PARAMS! !_TEST_PARAM:~2!
  shift
  goto param_loop
)

if "!_TEST_PARAM:~0,2!"=="-D" (
  rem test if this was double-quoted property "-Dprop=42"
  for /F "delims== tokens=1,*" %%G in ("!_TEST_PARAM!") DO (
    if not ["%%H"] == [""] (
      set _JAVA_PARAMS=!_JAVA_PARAMS! !_PARAM1!
    ) else if [%2] neq [] (
      rem it was a normal property: -Dprop=42 or -Drop="42"
      call set _PARAM1=%%1=%%2
      set _JAVA_PARAMS=!_JAVA_PARAMS! !_PARAM1!
      shift
    )
  )
) else (
  if "!_TEST_PARAM!"=="-main" (
    call set CUSTOM_MAIN_CLASS=%%2
    shift
  ) else (
    set _APP_ARGS=!_APP_ARGS! !_PARAM1!
  )
)
shift
goto param_loop
:param_afterloop

set _JAVA_OPTS=!_JAVA_OPTS! !_JAVA_PARAMS!
:run
 
set "APP_CLASSPATH=%APP_LIB_DIR%\powerapi-cli.powerapi-cli-4.0.jar;%APP_LIB_DIR%\org.powerapi.powerapi-core-4.0.jar;%APP_LIB_DIR%\bluecove-2.1.0.jar;%APP_LIB_DIR%\libpfm.jar;%APP_LIB_DIR%\fusejna.jar;%APP_LIB_DIR%\bluecove-gpl-2.1.0.jar;%APP_LIB_DIR%\org.scala-lang.scala-library-2.11.7.jar;%APP_LIB_DIR%\org.apache.logging.log4j.log4j-api-2.5.jar;%APP_LIB_DIR%\org.apache.logging.log4j.log4j-core-2.5.jar;%APP_LIB_DIR%\com.typesafe.akka.akka-actor_2.11-2.3.14.jar;%APP_LIB_DIR%\com.typesafe.config-1.2.1.jar;%APP_LIB_DIR%\fr.inria.powerspy.powerspy-core_2.11-1.2.jar;%APP_LIB_DIR%\com.nativelibs4java.bridj-0.7.0.jar;%APP_LIB_DIR%\com.google.android.tools.dx-1.7.jar;%APP_LIB_DIR%\com.github.scala-incubator.io.scala-io-core_2.11-0.4.3.jar;%APP_LIB_DIR%\org.scala-lang.modules.scala-parser-combinators_2.11-1.0.1.jar;%APP_LIB_DIR%\com.madgag.scala-arm_2.11-1.3.3.jar;%APP_LIB_DIR%\org.scala-lang.plugins.scala-continuations-library_2.11-1.0.1.jar;%APP_LIB_DIR%\com.github.scala-incubator.io.scala-io-file_2.11-0.4.3.jar;%APP_LIB_DIR%\org.jfree.jfreechart-1.0.19.jar;%APP_LIB_DIR%\org.jfree.jcommon-1.0.23.jar;%APP_LIB_DIR%\org.scala-saddle.saddle-core_2.11-1.3.4.jar;%APP_LIB_DIR%\joda-time.joda-time-2.1.jar;%APP_LIB_DIR%\org.joda.joda-convert-1.2.jar;%APP_LIB_DIR%\org.scala-saddle.google-rfc-2445-20110304.jar;%APP_LIB_DIR%\com.googlecode.efficient-java-matrix-library.ejml-0.19.jar;%APP_LIB_DIR%\org.apache.commons.commons-math-2.2.jar;%APP_LIB_DIR%\it.unimi.dsi.fastutil-6.5.4.jar;%APP_LIB_DIR%\it.unimi.dsi.dsiutils-2.0.15.jar;%APP_LIB_DIR%\com.martiansoftware.jsap-2.1.jar;%APP_LIB_DIR%\ch.qos.logback.logback-classic-1.0.9.jar;%APP_LIB_DIR%\ch.qos.logback.logback-core-1.0.9.jar;%APP_LIB_DIR%\log4j.log4j-1.2.17.jar;%APP_LIB_DIR%\commons-configuration.commons-configuration-1.8.jar;%APP_LIB_DIR%\commons-lang.commons-lang-2.6.jar;%APP_LIB_DIR%\commons-io.commons-io-2.4.jar;%APP_LIB_DIR%\commons-collections.commons-collections-3.2.1.jar;%APP_LIB_DIR%\org.apache.commons.commons-math3-3.1.1.jar;%APP_LIB_DIR%\org.hyperic.sigar-1.6.5.132.jar;%APP_LIB_DIR%\net.java.dev.jna.jna-4.2.1.jar;%APP_LIB_DIR%\io.spray.spray-json_2.11-1.3.2.jar;%APP_LIB_DIR%\com.github.docker-java.docker-java-2.1.4.jar;%APP_LIB_DIR%\com.fasterxml.jackson.jaxrs.jackson-jaxrs-json-provider-2.1.2.jar;%APP_LIB_DIR%\com.fasterxml.jackson.core.jackson-core-2.1.2.jar;%APP_LIB_DIR%\com.fasterxml.jackson.core.jackson-databind-2.1.2.jar;%APP_LIB_DIR%\com.fasterxml.jackson.core.jackson-annotations-2.1.1.jar;%APP_LIB_DIR%\com.fasterxml.jackson.module.jackson-module-jaxb-annotations-2.1.2.jar;%APP_LIB_DIR%\org.glassfish.jersey.connectors.jersey-apache-connector-2.11.jar;%APP_LIB_DIR%\org.apache.httpcomponents.httpclient-4.3.1.jar;%APP_LIB_DIR%\org.apache.httpcomponents.httpcore-4.3.jar;%APP_LIB_DIR%\commons-logging.commons-logging-1.1.3.jar;%APP_LIB_DIR%\commons-codec.commons-codec-1.8.jar;%APP_LIB_DIR%\org.glassfish.jersey.core.jersey-common-2.11.jar;%APP_LIB_DIR%\javax.ws.rs.javax.ws.rs-api-2.0.jar;%APP_LIB_DIR%\javax.annotation.javax.annotation-api-1.2.jar;%APP_LIB_DIR%\org.glassfish.jersey.bundles.repackaged.jersey-guava-2.11.jar;%APP_LIB_DIR%\org.glassfish.hk2.hk2-api-2.3.0-b05.jar;%APP_LIB_DIR%\org.glassfish.hk2.hk2-utils-2.3.0-b05.jar;%APP_LIB_DIR%\org.glassfish.hk2.external.aopalliance-repackaged-2.3.0-b05.jar;%APP_LIB_DIR%\org.glassfish.hk2.external.javax.inject-2.3.0-b05.jar;%APP_LIB_DIR%\org.glassfish.hk2.hk2-locator-2.3.0-b05.jar;%APP_LIB_DIR%\org.javassist.javassist-3.18.1-GA.jar;%APP_LIB_DIR%\org.glassfish.hk2.osgi-resource-locator-1.0.1.jar;%APP_LIB_DIR%\org.glassfish.jersey.core.jersey-client-2.11.jar;%APP_LIB_DIR%\de.gesellix.unix-socket-factory-2015-01-27T15-02-14.jar;%APP_LIB_DIR%\org.apache.commons.commons-compress-1.5.jar;%APP_LIB_DIR%\org.tukaani.xz-1.2.jar;%APP_LIB_DIR%\org.slf4j.slf4j-api-1.7.5.jar;%APP_LIB_DIR%\org.slf4j.jcl-over-slf4j-1.7.12.jar;%APP_LIB_DIR%\com.google.guava.guava-18.0.jar;%APP_LIB_DIR%\org.bouncycastle.bcpkix-jdk15on-1.51.jar;%APP_LIB_DIR%\org.bouncycastle.bcprov-jdk15on-1.51.jar;%APP_LIB_DIR%\org.influxdb.influxdb-java-2.1.jar;%APP_LIB_DIR%\com.squareup.retrofit.retrofit-1.9.0.jar;%APP_LIB_DIR%\com.google.code.gson.gson-2.3.1.jar;%APP_LIB_DIR%\com.squareup.okhttp.okhttp-2.4.0.jar;%APP_LIB_DIR%\com.squareup.okio.okio-1.4.0.jar;%APP_LIB_DIR%\..\conf;%APP_LIB_DIR%\..\scripts"
set "APP_MAIN_CLASS=org.powerapi.app.PowerAPI"

if defined CUSTOM_MAIN_CLASS (
    set MAIN_CLASS=!CUSTOM_MAIN_CLASS!
) else (
    set MAIN_CLASS=!APP_MAIN_CLASS!
)

rem Call the application and pass all arguments unchanged.
"%_JAVACMD%" !_JAVA_OPTS! !POWERAPI_CLI_OPTS! -cp "%APP_CLASSPATH%" %MAIN_CLASS% !_APP_ARGS!

@endlocal


:end

exit /B %ERRORLEVEL%
