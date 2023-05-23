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
            stage("1. Env in ${agentName}") {
                echo "running on agent, ${agentName}"
            // sh 'printenv'
            }
        stage('2. second stage') {
            echo 'second stage'
        }
    }
}

def processTask() {
    def data = readJSON file: 'app.properties'
    for (item in data.bases) {
        def agentName = item.hostName
        if (item.used != true) { continue }
        println 'Prearing task for ' + agentName
        collectBuildEnv['node_' + agentName] = {
            dumpBuildEnv(agentName)
        }
    }
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
