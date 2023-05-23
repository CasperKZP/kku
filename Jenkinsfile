import jenkins.model.*
import io.libs.Utils

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

def dumpBuildEnv(String agentName, String infoBaseName, String versionPlatform, String infobasePath, Boolean isFile) {
    node("${agentName}") {
        stage("1. Env in ${agentName}") {
            echo "running on agent, ${agentName}"
        }
        stage("2. second stage env in ${agentName}") {
            echo "running on agent, ${agentName}"
            dir("build_${agentName}") {
                writeFile file:"${infoBaseName}", text:''
            }
        }
    }
}

def processTask() {
    def data = readJSON file: 'app.properties'
    for (item in data.bases) {
        def agentName = item.hostName
        def infoBaseName = item.infobaseName
        def versionPlatform = item.version
        def infobasePath = item.infobasePath
        def isFile = item.isFile
        if (item.used != true) { continue }
        println 'Prearing task for ' + agentName
        collectBuildEnv['node_' + agentName] = {
            dumpBuildEnv(agentName, infoBaseName, versionPlatform, infobasePath, isFile)
        }
    }
}
