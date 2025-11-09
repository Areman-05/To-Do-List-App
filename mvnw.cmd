@ECHO OFF
SETLOCAL

SET WRAPPER_DIR=%~dp0.mvn\wrapper
SET WRAPPER_JAR=%WRAPPER_DIR%\maven-wrapper.jar
SET WRAPPER_PROPERTIES=%WRAPPER_DIR%\maven-wrapper.properties

IF NOT EXIST "%WRAPPER_JAR%" (
  ECHO Error: no se encuentra "%WRAPPER_JAR%"
  EXIT /B 1
)

IF DEFINED JAVA_HOME (
  SET JAVA_EXE=%JAVA_HOME%\bin\java.exe
  IF EXIST "%JAVA_EXE%" GOTO execute
)

SET JAVA_EXE=java.exe
WHERE "%JAVA_EXE%" >NUL 2>&1
IF ERRORLEVEL 1 (
  ECHO Error: Java no esta instalado o no esta en el PATH.
  EXIT /B 1
)

:execute
"%JAVA_EXE%" -classpath "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*
ENDLOCAL

