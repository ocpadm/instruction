@Library('nocom') _
pipeline {
    agent any
    tools {
        maven 'maven-3.3.9'
        jdk 'jdk1.8.0_65'
    }
    environment {
    	ARTIFACT = readMavenPom().getArtifactId()
        VERSION = readMavenPom().getVersion().replaceAll("SNAPSHOT","").replaceAll("-","")
        BRANCHNAME = "$env.BRANCH_NAME".replaceAll("-","").toLowerCase().substring("$env.BRANCH_NAME".indexOf("/") + 1)
        PORT = getfreeport().trim()

    }
    stages {
        stage('Initialize') {
            steps {
                echo "M2_HOME = ${M2_HOME}"
            }
        }
        stage('Build') {
           
            steps {
                echo "Building"
                sh 'mvn -Dmaven.test.skip=true install'
            }
        }
        stage('User') {
            steps {
            	script {
            		// Wrap the input step in a timeout so that Jenkins won't
            		// be left waiting for input forever...
            		timeout(time: 5, unit: 'MINUTES') {
                		env.RELEASE_SCOPE = input message: 'User input required', ok: 'Release!',
                            parameters: [choice(name: 'RELEASE_SCOPE', choices: 'SIT\nUAT\nPROD', description: 'What is the release scope?')]
                	}
                }
				echo "${env.RELEASE_SCOPE}"
            }

        }
        stage('Docker Master') {
        	when {
					branch 'master'
					environment name: 'RELEASE_SCOPE', value: 'PROD'
            }
            steps {
            	script {
            		 try {
            	     	echo "Building Docker Image for master branch"
                     	sh "docker rmi -f $BRANCHNAME:$VERSION || exit 0"
			         	sh "docker build --rm -t $BRANCHNAME:$VERSION ."
			         	sh "docker rm -f $BRANCHNAME || exit 0"
			         	sh "docker run -d  -p $PORT:8080 --name=$BRANCHNAME $BRANCHNAME:$VERSION"
			         } catch (Exception e) {
			         	currentBuild.result = "FAILURE"
			         }
			      		
            	}

            }

        }
        stage('Docker Developer') {
            when {
					not {
					     branch 'master'
					}
					environment name: 'RELEASE_SCOPE', value: 'SIT'
            }
            steps {
            	script {
            		 try {
            	     	echo "Building Docker Image $BRANCHNAME:$VERSION"
                     	sh "docker rmi -f $BRANCHNAME:$VERSION || exit 0"
			         	sh "docker build --rm -t $BRANCHNAME:$VERSION ."
			         	sh "docker rm -f $BRANCHNAME || exit 0"
			         	sh "docker run -d  -p $PORT:8080 --name=$BRANCHNAME $BRANCHNAME:$VERSION"
			         } catch (Exception e) {
			         	currentBuild.result = "FAILURE"
			         }
            	}

            }

        }
        stage('Jira') {
            when {
                expression {
                   return env.RELEASE_SCOPE == 'SIT'
                }
            }
        	steps {
        		script {
        			withEnv(['JIRA_SITE=nocom']) {
        		    	comments = jiraGetComments idOrKey: calculateIssueKey(BRANCH_NAME)
                    	if (!comments.data.toString().contains("Test your application")) {
            			    jiraAddComment idOrKey: calculateIssueKey(BRANCH_NAME) , comment: 'Test your application at http://testhost:' + getfreeport().trim()
            			}
            		}
            	}
            }
        }

    }
    post {
        always {
            script {
              	currentBuild.result = 'SUCCESS'
			}
			step([$class: 'StashNotifier'])
        	echo "RESULT: ${currentBuild.result}"
        }
    } 
}