#!/usr/bin/groovy
def call(branch) {
    try {
     return branch.substring(branch.indexOf("/") + 1).split("-")[0] + "-" + branch.substring(branch.indexOf("/") + 1).split("-")[1]
    } catch (Exception e) {
       return e.getMessage()
    }
}
