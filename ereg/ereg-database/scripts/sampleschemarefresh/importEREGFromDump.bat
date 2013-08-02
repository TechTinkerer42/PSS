@echo off
cls
echo.
echo Starting import
echo Dropping user EREG01DS
set BAT_FOLDER=%cd%\workfolder\bat
CALL %BAT_FOLDER%\DROPCRT_USER.bat sys admin EREG01MO ereg01mo EREG01DV_DA_01
echo *********Importing schema*********
CALL %BAT_FOLDER%\EREG_IMP.bat sys admin EREG01DS EREG01MO
echo *********Done importing schema*********
echo *********Post import *********
CALL %BAT_FOLDER%\exec_sql.bat EREG01MO ereg01mo post_import.sql EREG01MO
echo *********Post import done*********