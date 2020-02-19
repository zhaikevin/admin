#!/bin/bash


#SERVER_NAME='AdminServer'
#JAR_NAME='admin-server.jar'
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
TARGET_JAR=$(ls *.jar);
TJ=${TARGET_JAR[0]}
SERVER_NAME=${TARGET_JAR[0]}
JAR_NAME="$TJ"
DEPLOY_DIR=`pwd`
CONF_DIR=$DEPLOY_DIR/config

PROFILES="active="
SPRING_PROFILES_ACTIVE=""
APPLICATION_NAME="application.yml"
ACTIVE_ENV=""
if [[ "$1" == *$PROFILES* ]]; then
    substr=${1#*=}
    echo "active profiles $substr"
    APPLICATION_NAME="application-$substr.yml"
	ACTIVE_ENV="-$substr"
    echo "$APPLICATION_NAME"
    SPRING_PROFILES_ACTIVE="--spring.profiles.$1"
fi	
#`sed -n '1,5'p config/$APPLICATION_NAME > port_temp`
SERVER_PORT=`sed -nre '/server:/,/port: [0-9]+/ s/.*port: +([0-9]+).*/\1/p' config/$APPLICATION_NAME`
echo "starting port : $SERVER_PORT"
PIDS=`ps -f | grep java | grep "$CONF_DIR" |awk '{print $2}'`
if [ "$1" = "status" ]; then	  
    if [ -n "$PIDS" ]; then
        echo "The $SERVER_NAME is running...!"
        echo "PID: $PIDS"
        exit 0
    else
        echo "The $SERVER_NAME is stopped"
        exit 0
    fi
fi

if [ -n "$PIDS" ]; then
    echo "ERROR: The $SERVER_NAME already started!"
    echo "PID: $PIDS"
    exit 1
fi

if [ -n "$SERVER_PORT" ]; then
    SERVER_PORT_COUNT=`netstat -tln | grep $SERVER_PORT | wc -l`
    if [ $SERVER_PORT_COUNT -gt 0 ]; then
        echo "ERROR: The $SERVER_NAME port $SERVER_PORT already used!"
        exit 1
    fi
fi

LOGS_DIR=$DEPLOY_DIR/logs
#LOGS_DIR=/data/logs
if [ ! -d $LOGS_DIR ]; then
    mkdir $LOGS_DIR
fi
STDOUT_FILE=$LOGS_DIR/stdout.log

JAVA_OPTS=" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true "
JAVA_DEBUG_OPTS=""
if [ "$1" = "debug" ]; then
    JAVA_DEBUG_OPTS=" -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n "
fi

JAVA_JMX_OPTS=""
if [ "$1" = "jmx" ]; then
    JAVA_JMX_OPTS=" -Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false "
fi

JAVA_MEM_OPTS=""
BITS=`java -version 2>&1 | grep -i 64-bit`
if [ -n "$BITS" ]; then
    JAVA_MEM_OPTS=" -server -Xmx5120m -Xms5120m -Xmn512m -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
else
    JAVA_MEM_OPTS=" -server -Xms512m -Xmx2048m -XX:PermSize=128m -XX:SurvivorRatio=2 -XX:+UseParallelGC "
fi

CONFIG_FILES=" -Dlogging.path=$LOGS_DIR -Dlogging.config=$CONF_DIR/logback-spring.xml -Dspring.config.location=$CONF_DIR/application$ACTIVE_ENV.yml "
echo "CONFIG_FILES: $CONFIG_FILES"
echo -e "Starting the $SERVER_NAME ..."
echo "command: java $JAVA_OPTS $JAVA_MEM_OPTS $JAVA_DEBUG_OPTS $JAVA_JMX_OPTS $CONFIG_FILES -jar $DEPLOY_DIR/$JAR_NAME $SPRING_PROFILES_ACTIVE > $STDOUT_FILE 2>&1 &" 
nohup java $JAVA_OPTS $JAVA_MEM_OPTS $JAVA_DEBUG_OPTS $JAVA_JMX_OPTS $CONFIG_FILES -jar $DEPLOY_DIR/$JAR_NAME $SPRING_PROFILES_ACTIVE > $STDOUT_FILE 2>&1 &

COUNT=0
while [ $COUNT -lt 1 ]; do
    echo -e ".\c"
    sleep 1
    if [ -n "$SERVER_PORT" ]; then
        COUNT=`netstat -an | grep $SERVER_PORT | wc -l`
    else
    	COUNT=`ps -f | grep java | grep "$DEPLOY_DIR" | awk '{print $2}' | wc -l`
    fi
    if [ $COUNT -gt 0 ]; then
        break
    fi
done


echo "OK!"
PIDS=`ps -f | grep java | grep "$DEPLOY_DIR" | awk '{print $2}'`
echo "PID: $PIDS"
echo "STDOUT: $STDOUT_FILE"
