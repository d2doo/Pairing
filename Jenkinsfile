pipeline {
    agent any

    tools {
        nodejs 'A408_FE_Build'
    }

    stages {
        stage('Build FE') {
            steps {
                script {
                    // FE 폴더로 이동
                    dir('FE') {
                
                        sh 'node -v'
                        sh 'npm -v'
                        sh 'rm -rf node_modules'
                        // sh 'rm package-lock.json'
                        sh 'npm install'

                        sh 'cat src/components/landingPage/joinModal1/EmailConfirmButton.jsx'

			            sh 'npm install --global yarn'
                        sh 'npm install --global vite'
                        sh 'yarn global add create-vite'
                        sh 'yarn add vite --dev'
                        sh 'yarn install'
                        sh 'yarn build'
                        

                        
                    }
                }
            }
        }
    

    stage('Send Artifact'){
            steps{
                sh 'ls -l'
                sh 'ls -l FE/'
                sh 'ls -l FE/dist'
                sh 'tar -cvf febuild.tar FE/**'
                sh 'ls -l'
                script{
                    sshPublisher(
                            publishers: [
                                sshPublisherDesc(
                                    configName: 'ssafycontrol',
                                    transfers: [
                                        sshTransfer(
                                            sourceFiles: 'febuild.tar',
                                            remoteDirectory: '/sendData',
                                        )
                                    ]
                                )
                            ]
                        )
                }
            }
            
        }
        stage('Auto CI By Git-lab CI-CD'){
            steps{
                script{
                    sh 'echo manual Auto CI Start'
                    sh 'curl "https://ssafycontrol.shop/control/dev/fe"'
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
