@Library("shared-libraries")
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
      label "expo-0068"
    //    label "${(env.jenkinsAgent == null || env.jenkinsAgent == 'null') ? "expo-0068" : env.jenkinsAgent}"
    }
    options {
        timeout(time: 8, unit: 'HOURS') 
        buildDiscarder(logRotator(numToKeepStr:'10'))
    }

  stages {

//Шаг 1. Иниициализация
  stage("Подготовка") {
            steps {
                timestamps {
                    script {


                        // создаем пустые каталоги
                        dir ('build') {
                            writeFile file:'dummy', text:''
                        }
                    }
                }
            }
        }

    stage('Init') {
      steps {
        script {
        
       // def props = readJSON file: 'app.properties', returnPojo: true
        //assert props['key'] == null
        //props.each { key, value ->
        //echo "Walked through key $key and value $value"
         //         }
  def props = readJSON file: 'app.properties'
  props.bases.each { key, value ->
        echo "Walked through key $key and value $value"
                  }
      }
      }
    }

  }
}