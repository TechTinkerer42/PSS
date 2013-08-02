1)Make sure Oracle 11G XE is installed

2)Make sure the password for "sys" user is "admin"

If it is not, it can be changed by logging in as "sys" with "sysdba" role and executing the below statement :

alter user sys identified by admin;

3)Make sure there is an entry for "XE" in tnsnames.ora
Refer to [path_to_code]\ereg\ereg-database\scripts\sampleschemarefresh\sampletnsnames.ora
   
Note:- Make Sure that the host name reflects the correct value in tnsnames.ora.
Note:- To edit the above file open command prompt with admin rights and execute the below.
   
notepad <filename>  
      
4)Make sure there is no connection to the database(tomcat server,any other client) with
the username "EREG01DS"

5)Create tablespace EREG01DV_DA_01
cd [path_to_code]\ereg\ereg-database\scripts\sampleschemarefresh
create_ts.bat

Note: Ignore errors:
ORA-01543: tablespace 'EREG01DV_DA_01' already exists

6)To export, execute [path_to_code]\ereg\ereg-database\scripts\sampleschemarefresh\exportEREGSMPL.bat

7)To import, execute [path_to_code]\ereg\ereg-database\scripts\sampleschemarefresh\importEREGFromDump.bat