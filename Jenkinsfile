import jenkins.model.*

collectBuildEnv = [:]

@NonCPS
def getNodes(String label) {
    jenkins.model.Jenkins.instance.nodes.collect { thisAgent ->
        if (thisAgent.labelString.contains("${label}")) {
            // this works too
            // if (thisAagent.labelString == "${label}") {
            return thisAgent.name
            }
        }
    }

def dumpBuildEnv(String agentName) {
    node("${agentName}") {
        stage("Env in ${agentName}") {
            echo "running on agent, ${agentName}"
        // sh 'printenv'
        }
    }
}

def processTask() {
    def data = readJSON file: 'app.properties'
    for (item in data.bases) {
        println "is item use - ${item.used}"
        def agentName = item.hostName;
        if (item.used == true) {
            println 'Prearing task for ' + agentName
            collectBuildEnv['node_' + agentName] = {
                dumpBuildEnv(agentName)
            }
        }
    }
/*
    // Replace label-string with the label name that you may have
    def nodeList = getNodes('kku')

    for (i = 0; i < nodeList.size(); i++) {
        def agentName = nodeList[i]

        // skip the null entries in the nodeList
        if (agentName != null) {
            println 'Prearing task for ' + agentName
            collectBuildEnv['node_' + agentName] = {
                dumpBuildEnv(agentName)
            }
        }
    }*/
}

pipeline {
    // I prefer to have a dedicated node to execute admin tasks
    agent {
        label 'expo-0068'
    }

    options {
        timestamps()
    }

    stages {
        stage('agents-tasks') {
            steps {
                script {
                    processTask()
                    println 'Running parallel stages'
                    parallel collectBuildEnv
                }
            }
        }
    }
}
