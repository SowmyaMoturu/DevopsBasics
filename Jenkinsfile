pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        bat 'mvn compile'
      }
    }

    stage('regression') {
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

  }
}