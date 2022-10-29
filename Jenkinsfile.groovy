pipeline {
    agent none
    stages {
        stage('Run Downstream jobs parallel') {
            parallel {
                stage('job a') {
                    echo 'trigger downstream job a'
                    steps {
                      script {
                        echo 'trigger downtream-job-a'
                        sh ''' 
                           sleep 15
                        '''
                        // i.e build job: 'downtream-job-a'
                      }
                    }
                    post {
                        always {
                            echo 'always do something after the previous steps'
                        }
                    }
                }
                stage('job b') {
                    agent { // <= the agent can be set in the downstream job itself 
                        label "linux"
                    }
                    steps {
                        echo 'trigger downtream-job-b'
                        sleep 10   
                        // i.e build job: 'downtream-job-b'
                    }
                    post {
                        always {
                            echo currentBuild.durationString
                        }
                    }
                }
            }// Parallel
        }// Stage Downstream jobs
        stage('after parallel'){
            steps {
                echo 'after parallel procedure'
                echo currentBuild.durationString
            }         
        }// Stage after parallel
    }// Stages
}// Pipeline
