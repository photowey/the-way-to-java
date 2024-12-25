@echo off
setlocal enableDelayedExpansion

@REM
@REM @author photowey
@REM

echo Current directory: !cd!

set CMD=mvn

IF DEFINED MVND_HOME (
    echo Using mvnd from: %MVND_HOME%

    set CMD="%MVND_HOME%\bin\mvnd"
)

call %CMD% clean -DskipTests=true source:jar deploy

IF %ERRORLEVEL% NEQ 0 (
    echo Maven command failed with error code %ERRORLEVEL%
) ELSE (
    echo Maven command completed successfully
)

endlocal
