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
        }

        stage('smoke') {
          steps {
            bat 'mvn test -Psmoke'
          }
        }

      }
    }

    stage('package') {
      steps {
        bat 'mvn package'
        archiveArtifacts '**/*.war'
      }
    }

     stage('deploy') {
      steps {
      deploy adapters: [tomcat9(credentialsId: 'd698c92f-b0e9-486b-ba09-ca8366f8c5ac', path: '', url: 'http://100.64.65.11:9090/')], contextPath: '/', war: '**/*.war'
      }
    }

  }
}