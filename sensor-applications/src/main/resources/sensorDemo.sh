#!/bin/bash
# MyApp
#
# description: bla bla
case $1 in
    start)
        /bin/bash /home/pi/javaSensorDemo/myapp-start.sh
    ;;
    stop)
        /bin/bash //home/pi/javaSensorDemo/myapp-stop.sh
    ;;
    restart)
        /bin/bash /home/pi/javaSensorDemo/myapp-stop.sh
        /bin/bash /home/pi/javaSensorDemo/myapp-start.sh
    ;;
esac
exit 0