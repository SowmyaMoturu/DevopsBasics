pipeline {
  agent any
  stages {
    stage('checkout') {
      steps {
      		git credentialsId: 'Gitlab', url: 'https://git.epam.com/sowmya_moturu/devopsbasics'
      }
    }

     stage('test') {
      parallel {
        stage('regression') {
          steps {
          withMaven( maven: 'LocalMaven') {
    			bat     'mvn test -Pregression'
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
    			bat 'mvn test -Psmoke'
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
    			bat 'mvn package'
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