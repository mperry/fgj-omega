#!/bin/sh

unset SCRIPT;
UNAME=`uname`;
SOURCE=$0;
while [ -h "$SOURCE" ]; do
    SCRIPT=`basename "$SOURCE"`;
    LOOKUP=`ls -ld "$SOURCE"`;
    TARGET=`expr "$LOOKUP" : '.*-> \(.*\)$'`;
    if expr "${TARGET:-.}/" : '/.*/$' > /dev/null; then
        SOURCE=${TARGET:-.};
    else
        SOURCE=`dirname "$SOURCE"`/${TARGET:-.};
    fi;
done;
PREFIX=`dirname "$SOURCE"`/..;
PREFIX=`cd "$PREFIX"; pwd`;

classpath="$PREFIX/lib/hofgj.jar";

case "$SCRIPT" in
    hofgj )
        exec java -ea -cp "$classpath" pkg.hofgj.Main "$@";;

    * )
        echo "unknown program '$SCRIPT'" 1>&2;
        exit 1;;
esac;
