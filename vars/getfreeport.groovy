#!/usr/bin/groovy
def call() {
    // evaluate the body block, and collect configuration into the object
    sh (script: "ss -tln | awk 'NR > 1{gsub(/.*:/,\"\",\$4); print \$4}' | sort -un | awk -v n=9090 '\$0 < n {next}; \$0 == n {n++; next}; {exit}; END {print n}'", returnStdout:true)
}
