pipeline {
  agent {
    node {
      label 'master'
    }

  }
  stages {
    stage('build') {
      steps {
        git(credentialsId: 'GithubCredentials', url: 'https://github.com/SowmyaMoturu/DevopsBasics.git')
      }
    }

    stage('compile') {
      steps {
        sh 'mvn compile'
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

    stage('SonarQube Analysis') {
      steps {
        withSonarQubeEnv(credentialsId: 'sonartoken', installationName: 'SonarQube') {
          sh '''-Dsonar.language=java
-Dsonar.projectName=My Sample Sonar Project 
-Dsonar.projectKey=my-java-maven-sonar
-Dsonar.sources=server/src 
-Dsonar.java.binaries=server/target/classes
-Dsonar.projectVersion=1.0'''
        }

        script {
          def qualitygate = waitForQualityGate()
          if (qualitygate.status != "OK") {
            error "Pipeline aborted due to quality gate coverage failure: ${qualitygate.status}"

          }
        }

      }
    }

    stage('deploy') {
      steps {
        deploy(adapters: [tomcat9(credentialsId: 'tomcatcredentials', path: '', url: 'http://10.245.128.134:8090/')], contextPath: '/', war: '**/*.war')
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
  post {
    always {
      emailext(subject: '$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS:', body: '$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS:  Check console output at $BUILD_URL to view the results.', attachLog: true, from: 'sowmya_moturu@epam.com', to: 'sowmya_moturu@epam.com')
    }

  }
}