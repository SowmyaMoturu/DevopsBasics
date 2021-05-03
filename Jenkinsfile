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

    stage('test') {
      parallel {
        stage('regression') {
          agent {
            node {
              label 'JenkinsNode1'
            }

          }
          steps {
            sh 'mvn test -Pregression'
          }
        }

        stage('smoke') {
          steps {
            sh 'mvn test -Psmoke'
          }
        }

      }
    }

    stage('package') {
      post {
        success {
          archiveArtifacts '**/*.war'
        }

      }
      steps {
        sh 'mvn package'
      }
    }

    stage('deploy') {
      steps {
        deploy(adapters: [tomcat9(credentialsId: 'tomcatcredentials', path: '', url: 'http://10.245.128.167:8090/')], contextPath: '/', war: '**/*.war')
      }
    }

    stage('results') {
      steps {
        junit '**/*.xml'
      }
    }

    stage('emailResults') {
      steps {
        emailext(subject: 'Jenkins Job', body: 'Jenkins job ran successfully', attachLog: true, to: 'sowmya_moturu@epam.com')
      }
    }

  }
  tools {
    maven 'Maven'
  }
}