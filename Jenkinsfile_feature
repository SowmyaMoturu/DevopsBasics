pipeline {
  agent {
    node {
      label 'master'
    }

  }
  stages {
    stage('build') {
      steps {
        git branch: 'develop', credentialsId: 'GithubCredentials', url: 'https://github.com/SowmyaMoturu/DevopsBasics.git'
      }
    }

    stage('unittets') {
      steps {
        sh 'mvn test'
      }
    }

    stage('results') {
      steps {
        junit '**/*.xml'
      }
    }

  }
  tools {
    maven 'Maven'
  }
}