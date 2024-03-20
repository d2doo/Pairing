pipeline {
    agent any

    tools {
        jdk 'A408_BE_Build'
    }
    environment {
        CONTAINER_NAME = "auto-dev-server"
        SSH_CREDENTIALS = 'DevOps'
        REMOTE_HOST = '172.31.41.136'
        SCRIPT_PATH = '/temp/AutoDevServer.sh'
        SSH_REMOTE_CONFIG = 'ubuntu'
    }

    stages {

        stage('Build BE') {
            when {
                expression { env.GIT_BRANCH == 'be' }
            }
            steps {
                script {
                    dir('BE') {
                        sh 'chmod +x gradlew'
                        sh 'ls -l'

                        sh './gradlew clean build'
                        sh 'jq --version'
                        sh 'cd build/libs && ls -al'

                        sh 'echo manual Auto CI Start'
                        sh 'curl "https://ssafycontrol.shop/control/dev/be"'

                    }
                }
            }
        }

        stage('Build FE') {
        	    when {
                        expression { env.GIT_BRANCH == 'fe' }
                    }
                    steps {
                        script {
                            // FE 폴더로 이동
                            dir('FE') {

                                sh 'node -v'
                                sh 'npm -v'
                                sh 'rm -rf node_modules'
                                // sh 'rm package-lock.json'
        			            sh 'npm install --global pnpm'
                                sh 'pnpm i'
                                sh 'pnpm run build'


                                sh 'echo manual Auto CI Start'
                                sh 'curl "https://ssafycontrol.shop/control/dev/fe"'




                            }
                        }
                    }
                }


        stage('Send Artifact to Control') {
            when {
                expression { env.GIT_BRANCH == 'dev' }
            }
            steps {
                script {
                    sh 'ls -al'
                    sh 'ls -al BE/build'
                    sh 'cd BE/build/libs && ls -al'
                    sshPublisher(
                        publishers: [
                            sshPublisherDesc(
                                configName: 'ssafycontrol',
                                transfers: [
                                    sshTransfer(
                                        sourceFiles: 'BE/build/libs/I10A709BE-0.0.1-SNAPSHOT.jar',
                                        removePrefix: 'BE/build/libs',
                                        remoteDirectory: '/sendData',
                                    )
                                ]
                            )
                        ]
                    )
                }
            }
        }

        stage('Send Artifact to Dev') {
            when {
                expression { env.GIT_BRANCH == 'dev' }
            }
            steps {
                script {
                    sh 'ls -al'
                    sh 'ls -al BE/build'
                    sh 'cd BE/build/libs && ls -al'
                    sshPublisher(
                        publishers: [
                            sshPublisherDesc(
                                configName: 'ssafyhelper',
                                transfers: [
                                    sshTransfer(
                                        sourceFiles: 'BE/build/libs/I10A709BE-0.0.1-SNAPSHOT.jar',
                                        removePrefix: 'BE/build/libs',
                                        remoteDirectory: '/sendData',
                                        execCommand: 'sh temp/AutoDevServer.sh'
                                    )
                                ]
                            )
                        ]
                    )
                }
            }
        }

        stage('Continuous Delivery') {
            when {
                expression { env.GIT_BRANCH == 'main' }
            }
            steps {
                script {
                    sh '''curl -X POST -H "Content-Type: application/json" -d '{"isBe": true}' "https://ssafycontrol.shop/control/dev/deploy"'''
                }
            }
        }
    }

    post {
        success {
            echo 'Build successful!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}
