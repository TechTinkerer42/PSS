export CATALINA_OPTS="-Xms512m -Xmx1024m -XX:MaxPermSize=512m"
export JAVA_OPTS="-noverify -Druntime.environment=development  -javaagent:../lib/spring-instrument-3.1.1.RELEASE.jar ${JAVA_OPTS}"
export JAVA_HOME="/export/Apps/jdk1.6.0_35"
./catalina.sh start

