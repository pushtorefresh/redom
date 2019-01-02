#!/bin/bash
# Modified version of file from Domic project (see NOTICE).
set -eu

# You can run it from any directory.
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PROJECT_DIR="$DIR/.."

pushd "$PROJECT_DIR" > /dev/null

./gradlew build --stacktrace
