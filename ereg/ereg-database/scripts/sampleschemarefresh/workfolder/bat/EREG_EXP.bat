set WORKDIR=%cd%\workfolder
set DB_USERNAME=%1
set DB_PASSWORD=%2
set TNS_ALIAS=%3
set SCHEMAOWN=%4
set FILENAME=exp_%SCHEMAOWN%
set PARFILE=%WORKDIR%\par\%SCHEMAOWN%.par
set LOGFILE=%WORKDIR%\log\%FILENAME%.log
set DUMPFILE=%WORKDIR%\dmp\%FILENAME%.dmp

REM +--------------------------------------------------------------------------+
REM | REMOVE OLD LOG AND PARAMETER FILE(S). |
REM +--------------------------------------------------------------------------+

del /q %PARFILE%
del /q %LOGFILE%

REM +--------------------------------------------------------------------------+
REM | WRITE EXPORT PARAMETER FILE. |
REM +--------------------------------------------------------------------------+

echo.
echo userid=%DB_USERNAME%/%DB_PASSWORD%@%TNS_ALIAS% > %PARFILE%
echo buffer=50000000 >> %PARFILE%
echo file=%DUMPFILE% >> %PARFILE%
echo compress=n >> %PARFILE%
echo grants=N >> %PARFILE%
echo log=%LOGFILE% >> %PARFILE%
echo statistics=none >> %PARFILE%
echo owner=%SCHEMAOWN% >> %PARFILE%

REM +--------------------------------------------------------------------------+
REM | MOVE OLD EXPORT (DUMP) FILE. |
REM +--------------------------------------------------------------------------+

del /q %DUMPFILE%.backup
move %DUMPFILE% %DUMPFILE%.backup


REM +--------------------------------------------------------------------------+
REM | PERFORM EXPORT. |
REM +--------------------------------------------------------------------------+
echo %PARFILE%

exp parfile=%PARFILE%

REM +--------------------------------------------------------------------------+
REM | SCAN THE EXPORT LOGFILE FOR ERRORS. |
REM +--------------------------------------------------------------------------+

echo ...
echo Analyzing log file for EXP- errors...
findstr /I /C:"EXP-" %LOGFILE%
if errorlevel 0 if not errorlevel 1 echo EXP- Errors: %FILENAME% %TNS_ALIAS% %COMPUTERNAME% %DATE% %TIME% %LOGFILE%

echo ...
echo Analyzing log file for ORA- errors...
findstr /I /C:"ORA-" %LOGFILE%
if errorlevel 0 if not errorlevel 1 echo ORA- Errors: %FILENAME% %TNS_ALIAS% %COMPUTERNAME% %DATE% %TIME% %LOGFILE%

echo ...
echo Analyzing log file for warnings...
findstr /I /C:"Export terminated successfully with warnings" %LOGFILE%
if errorlevel 0 if not errorlevel 1 echo WARNING: %FILENAME% %TNS_ALIAS% %COMPUTERNAME% %DATE% %TIME% %LOGFILE%

echo ...
echo Analyzing log file for errors...
findstr /I /C:"Export terminated unsuccessfully" %LOGFILE%
if errorlevel 0 if not errorlevel 1 echo ERROR: %FILENAME% %TNS_ALIAS% %COMPUTERNAME% %DATE% %TIME% %LOGFILE%


echo ...
echo END OF FILE REPORT
echo Filename : %FILENAME%
echo Database : %TNS_ALIAS%
echo Hostname : %COMPUTERNAME%
echo Date : %DATE%
echo Time : %TIME%
echo EXP Log File : %LOGFILE%