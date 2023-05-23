@Library("shared-libraries")
import jenkins.model.*
import io.libs.*
//import io.libs.ProjectHelpers

def projectHelpers = new ProjectHelpers()
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

def dumpBuildEnv(String agentName, String infoBaseName, String versionPlatform, String infobasePath, Boolean isFile, String ibLogin, String ibPass) {
    node("${agentName}") {
        stage("1. Block infobase (${agentName})") {
            def projectHelpers = new ProjectHelpers()
                try {
                projectHelpers.blockDb(infoBaseName, isFile);
                } catch (excp) {
                echo "Error happened when block base ${infoBaseName}."
                }
        }
        stage("2. Update infobase (${agentName})") {
            echo 'step 2'
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
        def ibLogin = item.ibLogin
        def ibPass = item.ibPass

        if (item.used != true) { continue }
        println 'Prearing task for ' + agentName
        collectBuildEnv['node_' + agentName] = {
            dumpBuildEnv(agentName, infoBaseName, versionPlatform, infobasePath, isFile, ibLogin, ibPass)
        }
    }
}
