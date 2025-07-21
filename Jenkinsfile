pipeline {
    agent any
   
    environment {
        PRISMA_API_URL = "https://api2.prismacloud.io" // This will need to be change
    }
   
    stages {
        stage('Checkout') {
            steps {
              git branch: 'master', url: 'https://github.com/krash-glitch/terragoat.git'
                stash includes: '**/*', name: 'source'
            }
        }
        stage('Checkov') {
            steps {
                withCredentials([string(credentialsId: 'PC_USER', variable: 'pc_user'), string(credentialsId: 'PC_PASSWORD', variable: 'pc_password')]) {
                    script {
                        docker.image('bridgecrew/checkov:latest').inside("--entrypoint=''") {
                            unstash 'source'
                            try {
                                sh 'checkov -d . --use-enforcement-rules -o cli -o junitxml --output-file-path console,results.xml --bc-api-key ${pc_user}::${pc_password} --repo-id krash/terragoat --branch master'
                            } catch (err) {
                                throw err
                            }
                        }
                    }
                }
            }
        }
    }
    options {
        preserveStashes()
        timestamps()
    }
}
