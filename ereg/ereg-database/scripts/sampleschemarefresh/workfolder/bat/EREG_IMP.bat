set WORKDIR=%cd%\workfolder
set DB_USERNAME=%1
set DB_PASSWORD=%2
set FROMUSER=%3
set TOUSER=%4
set FILENAME=exp_%FROMUSER%
set PARFILE=%WORKDIR%\par\%FROMUSER%_to_%TOUSER%_imp.par
set LOGFILE=%WORKDIR%\log\imp_%TOUSER%.log
set DUMPFILE=%WORKDIR%\dmp\%FILENAME%.dmp

REM +--------------------------------------------------------------------------+
REM | REMOVE OLD LOG AND PARAMETER FILE(S). |
REM +--------------------------------------------------------------------------+

del /q %PARFILE%
del /q %LOGFILE%

REM +--------------------------------------------------------------------------+
REM | WRITE IMPORT PARAMETER FILE. |
REM +--------------------------------------------------------------------------+

#echo userid=%DB_USERNAME%/%DB_PASSWORD% > %PARFILE%
echo userid="sys/admin as sysdba" > %PARFILE%
echo buffer=50000000 >> %PARFILE%
echo file=%DUMPFILE% >> %PARFILE%
echo grants=N >> %PARFILE%
echo log=%LOGFILE% >> %PARFILE%
echo commit=Y >> %PARFILE%
echo ignore=Y >> %PARFILE%
echo fromuser=%FROMUSER% >> %PARFILE%
echo touser=%TOUSER% >> %PARFILE%


REM +--------------------------------------------------------------------------+
REM | PERFORM IMPORT |
REM +--------------------------------------------------------------------------+

imp parfile=%PARFILE%

REM +--------------------------------------------------------------------------+
REM | SCAN THE IMPORT LOGFILE FOR ERRORS. |
REM +--------------------------------------------------------------------------+

echo ...
echo Analyzing log file for EXP- errors...
findstr /I /C:"IMP-" %LOGFILE%
if errorlevel 0 if not errorlevel 1 echo EXP- Errors: %FILENAME% %TNS_ALIAS% %COMPUTERNAME% %DATE% %TIME% %LOGFILE%

echo ...
echo Analyzing log file for ORA- errors...
findstr /I /C:"ORA-" %LOGFILE%
if errorlevel 0 if not errorlevel 1 echo ORA- Errors: %FILENAME% %TNS_ALIAS% %COMPUTERNAME% %DATE% %TIME% %LOGFILE%

echo ...
echo Analyzing log file for warnings...
findstr /I /C:"Import terminated successfully with warnings" %LOGFILE%
if errorlevel 0 if not errorlevel 1 echo WARNING: %FILENAME% %TNS_ALIAS% %COMPUTERNAME% %DATE% %TIME% %LOGFILE%

echo ...
echo Analyzing log file for errors...
findstr /I /C:"Import terminated unsuccessfully" %LOGFILE%
if errorlevel 0 if not errorlevel 1 echo ERROR: %FILENAME% %TNS_ALIAS% %COMPUTERNAME% %DATE% %TIME% %LOGFILE%


echo ...
echo END OF FILE REPORT
echo Filename : %FILENAME%
echo Database : %TNS_ALIAS%
echo Hostname : %COMPUTERNAME%
echo Date : %DATE%
echo Time : %TIME%
echo EXP Log File : %LOGFILE% 

