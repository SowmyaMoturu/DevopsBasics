pipeline {

   agent {
            node {
              label 'master'
            }

          }

  
  tools {
        maven "Maven"
  }
  
  stages {
  
    stage('build') {
      steps {
      	git credentialsId: 'GitlabCredentials', url: 'https://git.epam.com/sowmya_moturu/devopsbasics.git'
      }
    }

    stage('test') {
     
      parallel {

         agent {
            node {
              label 'JenkinsNode1'
            }

          }

      
        stage('regression') {
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
      steps {
    		sh 'mvn package'
      }
          post {
          	success	{
          		archiveArtifacts '**/*.war'
          	}
          }
    }

    stage('deploy') {
      steps {
       deploy adapters: [tomcat9(credentialsId: 'tomcatcredentials', path: '', url: 'http://10.245.128.167:8090/')], contextPath: '/', war: '**/*.war'
      }
    }
    
    stage('results') {
      steps {
     		junit  '**/*.xml'
      }
    }
  }
}