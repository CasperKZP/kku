@Library('shared-libraries')
//import io.libs.SqlUtils
//import io.libs.ProjectHelpers
import io.libs.Utils
import groovy.json.JsonSlurper

//def sqlUtils = new SqlUtils()
def utils = new Utils()
//def projectHelpers = new ProjectHelpers()

pipeline {
  parameters {
        string(defaultValue: "${env.platform1c}", description: 'Версия платформы 1с, например 8.3.12.1685. По умолчанию будет использована последня версия среди установленных', name: 'platform1c')
        string(defaultValue: "${env.admin1cUser}", description: 'Имя администратора с правом открытия вншних обработок (!) для базы тестирования 1с Должен быть одинаковым для всех баз', name: 'admin1cUser')
        string(defaultValue: "${env.admin1cPwd}", description: 'Пароль администратора базы тестирования 1C. Должен быть одинаковым для всех баз', name: 'admin1cPwd')
  }

    agent {
      label 'expo-0068'
    //    label "${(env.jenkinsAgent == null || env.jenkinsAgent == 'null') ? "expo-0068" : env.jenkinsAgent}"
    }
    options {
        timeout(time: 8, unit: 'HOURS')
        buildDiscarder(logRotator(numToKeepStr:'10'))
    }

    environment {
        AGENT_NAME = ''
        AGENT_PLATFORM = ''
        AGENT_IBLOGIN = ''
        AGENT_IBPASS = ''
    }

  stages {
    //Шаг 1. Иниициализация
    stage('Подготовка') {
      steps {
        timestamps {
          script {
            def data = readJSON file: 'app.properties'
            def node_name = "${NODE_NAME}"
            echo "The Node Name is: ${node_name}"
            for (item in data.bases) {
              if (!item.used) { continue }
              if (item.hostName == node_name) {
                //Параметры текущего хоста
                echo "Host find: ${item.hostName}"
                AGENT_NAME = item.hostName
                AGENT_PLATFORM = item.version
                AGENT_IBLOGIN = item.ibLogin
                AGENT_IBPASS = item.ibPass
              }
            }

         
          }
        }
      }
    }
  

    stage('Init') {
      steps {
        script {   echoParams()
        }
      }
    }
}
}

def echoParams() {
  echo AGENT_NAME
  echo AGENT_PLATFORM
  echo AGENT_IBLOGIN
  echo AGENT_IBPASS
}
