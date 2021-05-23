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
 
        environment {
            scannerHome = tool 'SonarScanner'
            PROJECT_NAME = "My Sample Sonar Project"
        }	
        steps {
 
            withSonarQubeEnv (credentialsId: 'sonartoken', installationName: 'SonarQube') {
                sh ''' \
                        ${scannerHome}/bin/sonar-scanner \
                            -Dsonar.projectName=MyProject \
                            -Dsonar.projectVersion=1.0 \
                            -Dsonar.sourceEncoding=UTF-8 \
                            -Dsonar.projectKey=my-java-maven-sonar \
                            -Dsonar.sources=server/src \
                            -Dsonar.java.binaries=server/target/classes \
                            '''
            }
      
        }
        
    }
    

    stage("Quality Gate") {
        steps {
            timeout(time: 1, unit: 'MINUTES') {
                 waitForQualityGate abortPipeline: true
            }
        }
    }
    
    stage('deploy') {
      steps {
        deploy(adapters: [tomcat9(credentialsId: 'tomcatcredentials', path: '', url: 'http://10.245.128.134:8090/')], contextPath: '/', war: '**/*.war')
      }
    }
    
    stage('deploy to tomcat on docker') {
    	steps {
    		sshPublisher(publishers: [sshPublisherDesc(configName: 'DockerHost', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: '''docker stop -f $(docker ps -qa)
			docker rm -f $(docker ps -qa)
			docker rmi mytomcat
			cd /opt/docker
			docker build -t mytomcat .
			docker run -d -p 8090:8080 mytomcat''', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '', remoteDirectorySDF: false, removePrefix: '/webapp/target', sourceFiles: '**/*.war')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
    	}
    
    }

    stage('results') {
      steps {
        junit '**/*.xml'
      }
    } 

  }
  
  post {
  always {
     emailext(subject: '$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS:', body: '$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS:  Check console output at $BUILD_URL to view the results.', attachLog: true, from: 'sowmya_moturu@epam.com', to: 'sowmya_moturu@epam.com')
  }
  
  }
  tools {
    maven 'Maven'
  }
}