pipeline {
  agent any
  stages {
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

  }
}