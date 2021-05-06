pipeline {
  agent {
    node {
      label 'master'
    }

  }
  stages {
    stage('build') {
      steps {
        git(credentialsId: 'GitlabCredentials', url: 'https://git.epam.com/sowmya_moturu/devopsbasics.git')
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