set DB_USERNAME=%1
set DB_PASSWORD=%2
set SQLFILE=%WORKDIR%\sql\%3
set SQL_PARM1=%4
echo Calling script %SQLFILE%
sqlplus %DB_USERNAME%/%DB_PASSWORD%@localhost @%SQLFILE% %SQL_PARM1%