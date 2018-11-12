#!/bin/bash
# https://github.com/vishnubob/wait-for-it/blob/master/wait-for-it.sh
# wait-for-it.sh

# https://www.gnu.org/software/bash/manual/html_node/The-Set-Builtin.html
# It will immediately stop your script if a simple command fails.
# I think this should have been the default behavior. Since such errors almost
# always signify something unexpected, it is not really 'sane' to keep executing the following commands.
# set -e

hostport=(${1//:/ })
host=${hostport[0]}
port=${hostport[1]}
shift
path="$@"

wait_for () {
    local -i max_attempts=25
    local -i attempt_num=0

    start_ts=$(date +%s)

    while :
    do
        echo "Trying..."
        respone=$(curl -s http://${host}:${port}/${path} | grep UP)
        result=$?

        if [[ result -eq 0 ]]; then
            end_ts=$(date +%s)
            echo "$host is available after $((end_ts - start_ts)) seconds"
            break
        fi

        if (( attempt_num == max_attempts )); then
            end_ts=$(date +%s)
            echo "$host is NOT available after $((end_ts - start_ts)) seconds"
            return 1
        fi

        $(( attempt_num++ )) 2>/dev/null
        sleep 5
    done

    return result
}

wait_for
