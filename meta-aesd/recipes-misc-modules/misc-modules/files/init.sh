#!/bin/sh

case "$1" in
  start)
        # Load modules
        echo "Starting scull and loading modules"
        module_load faulty
        ;;
  stop)
        # Unload modules
        echo "Stopping scull and unloading modules"
        module_unload
        ;;
  *)
        echo "Usage: $0 {start|stop}"
        exit 1
esac

exit 0