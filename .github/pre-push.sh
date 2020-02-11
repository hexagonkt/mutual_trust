#!/bin/sh

#
# Refer to `.git/hooks/pre-push.sample` for more information about this hook script.
# To clean Docker artifacts execute: `sudo docker system prune -af`
#

set -e

#
# Package libraries and examples prior to Docker image generation
#
./gradlew --quiet clean installDist -x test

#
# Start containers required for tests and benchmarks
#
docker-compose --log-level warning rm -sf
docker-compose --log-level warning up -d

#
# Runs all tests
#
./gradlew --console=plain --quiet build installDist

#
# Clean up
#
docker --log-level warning volume prune -f
