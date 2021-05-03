pipeline {
  agent any
  stages {
    stage('checkout') {
      steps {
       git branch: 'master', credentialsId: 'Gitlab', url: 'https://git.epam.com/sowmya_moturu/devopsbasics.git'
      }
    }

     stage('test') {
      parallel {
        stage('regression') {
          steps {
          withMaven( maven: 'LocalMaven') {
    			     mvn test -Pregression
			}
        
          }
          post {
          	success	{
          		junit '**/target/surefire-reports/TEST-*.xml'
          	}
          }
        }

        stage('smoke') {
          steps {
            withMaven( maven: 'LocalMaven') {
    			 mvn test -Psmoke
			}
          }
          post {
          	success	{
          		junit '**/target/surefire-reports/TEST-*.xml'
          	}
          }
        }

      }
    }

    stage('package') {
      steps {
       withMaven( maven: 'LocalMaven') {
    			 mvn package
			}
      }
          post {
          	success	{
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