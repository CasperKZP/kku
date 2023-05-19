@Library('shared-libraries')
//import io.libs.SqlUtils
//import io.libs.ProjectHelpers
import io.libs.Utils
import groovy.json.JsonSlurper

//def sqlUtils = new SqlUtils()
def utils = new Utils()
//def projectHelpers = new ProjectHelpers()
def agents = [:]

pipeline {
  parameters {
        string(defaultValue: "${env.platform1c}", description: 'Версия платформы 1с, например 8.3.12.1685. По умолчанию будет использована последня версия среди установленных', name: 'platform1c')
        string(defaultValue: "${env.admin1cUser}", description: 'Имя администратора с правом открытия вншних обработок (!) для базы тестирования 1с Должен быть одинаковым для всех баз', name: 'admin1cUser')
        string(defaultValue: "${env.admin1cPwd}", description: 'Пароль администратора базы тестирования 1C. Должен быть одинаковым для всех баз', name: 'admin1cPwd')
  }

    agent any
    // {
    //  label any
    //    label "${(env.jenkinsAgent == null || env.jenkinsAgent == 'null') ? "expo-0068" : env.jenkinsAgent}"
    //}
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
    //Шаг 1. Иниициализация окружения
    stage('Подготовка') {
      steps {
        timestamps {
          script {
            def data = readJSON file: 'app.properties'
            def node_name = "${NODE_NAME}"
            echo "The Node Name is: ${node_name}"
            for (item in data.bases) {
              if (!item.used) { continue }

              agents["agents_${item.hostName}"] = echoParams(item.hostName) //заполняем словарь агентами

              if (item.hostName == node_name) {
                //Параметры текущего хоста
                echo "Host find: ${item.hostName}"
                AGENT_NAME = item.hostName
                AGENT_PLATFORM = item.version
                AGENT_IBLOGIN = item.ibLogin
                AGENT_IBPASS = item.ibPass
              }
            }

          // parallel agents
          }
        }
      }
    }
    //Шаг 2. Выполнение
    stage('Выполнение') {
    //  agent {
    //    label 'kku'
    //  }
      steps {
        script {
          echo 'stage Выполнение:'
          parallel agents
        }
      }
    }
  }
}

def echoParams(hostName) {
  return {
    node('expo-0068') {
      timestamps {
        stage("Этап ${hostName}") {
          //steps {
          script {
            if (hostName != "${NODE_NAME}") {
              //  unstable('Host skiped')
              return
            }

            echo "Host name in stage: ${hostName}"
            echo "Node name in stage: ${NODE_NAME}"
            dir("${hostName}") {
              writeFile file:'dummy', text:''
            }
        // }
        }
        }
      }
    }
  }
}
