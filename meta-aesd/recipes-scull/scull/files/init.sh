#!/bin/sh

case "$1" in
  start)
        # Load modules
        echo "Starting scull and loading modules"
        scull_load
        ;;
  stop)
        # Unload modules
        echo "Stopping scull and unloading modules"
        scull_unload
        ;;
  *)
        echo "Usage: $0 {start|stop}"
        exit 1
esac

exit 0
