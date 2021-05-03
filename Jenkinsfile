pipeline {
  agent any
  stages {
    stage('checkout') {
      steps {
        git(url: 'https://git.epam.com/sowmya_moturu/devopsbasics.git', branch: 'master')
      }
    }

    stage('build') {
      steps {
        bat 'mvn compile'
      }
    }

    stage('test') {
      parallel {
        stage('regression') {
          steps {
            bat 'mvn test -Pregression'
          }
              post {
           
             success {
          
                junit '**/target/surefire-reports/TEST-*.xml'
                }
                }
       
        }

        stage('smoke') {
          steps {
            bat 'mvn test -Psmoke'
          }
              post {
           
             success {
          
                junit '**/target/surefire-reports/TEST-*.xml'
                }
                }
        }

      }
    }

    stage('package') {
      steps {
        bat 'mvn package'
        archiveArtifacts '**/*.war'
      }
      
       post {
            
                success {
     
                    archiveArtifacts '**/*.war'
                }
            }
    }

    stage('deploy') {
      steps {
       deploy adapters: [tomcat9(credentialsId: 'tomcatcredentials', path: '', url: 'http://localhost:9090/')], contextPath: '/', war: '**/*.war'
      }
    }

  }
}