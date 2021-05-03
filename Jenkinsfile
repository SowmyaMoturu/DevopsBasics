pipeline {

  agent any
  
  tools {
        maven "LocalMaven"
  }
  
  stages {
  
    stage('build') {
      steps {
      		git credentialsId: 'Gitlab', url: 'https://git.epam.com/sowmya_moturu/devopsbasics.git'
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
    
    stage('results') {
      steps {
     		junit  '**/target/surefire-reports/junitreports/TEST-*.xml'
      }
    }
  }
}