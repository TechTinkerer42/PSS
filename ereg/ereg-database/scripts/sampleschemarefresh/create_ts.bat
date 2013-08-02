set WORKDIR=%cd%\workfolder
set SQLFILE=%WORKDIR%\sql\create_ts.sql
sqlplus "sys/admin as sysdba" @%SQLFILE%