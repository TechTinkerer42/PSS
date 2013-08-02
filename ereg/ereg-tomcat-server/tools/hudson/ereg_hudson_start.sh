export CATALINA_OPTS="-DHUDSON_HOME=/export/Apps/hudson/ -Xmx1024m -Dorg.apache.jasper.runtime.BodyContentImpl.LIMIT_BUFFER=true -XX:MaxPermSize=256m" 
export JAVA_HOME="/export/Apps/jdk1.6.0_35"
export M2_HOME=/export/Apps/mvn/apache-maven-3.0.4/
PATH=$PATH:$M2_HOME/bin
./catalina.sh start

