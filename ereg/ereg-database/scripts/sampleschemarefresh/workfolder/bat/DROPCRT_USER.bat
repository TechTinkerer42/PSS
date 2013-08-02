set WORKDIR=%cd%\workfolder
set DB_USERNAME=%1
set DB_PASSWORD=%2
set LOCALDBUSER=%3
set LOCALDBUSERPASS=%4
set TABLESPACE=%5
set SQLFILE=%WORKDIR%\sql\drop_crt_yourownschema.sql
echo.
echo "Calling script to drop and recreate user"
sqlplus "sys/admin as sysdba" @%SQLFILE% %LOCALDBUSER% %LOCALDBUSERPASS% %TABLESPACE%