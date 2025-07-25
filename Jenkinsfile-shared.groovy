def execute() {
    echo '[shared] Starting shared Checkov execution'

    stage('Checkov') {
        echo '[shared] Inside Checkov stage'
        withCredentials([
            string(credentialsId: 'PC_USER', variable: 'pc_user'),
            string(credentialsId: 'PC_PASSWORD', variable: 'pc_password')
        ]) {
            docker.image('bridgecrew/checkov:latest').inside("--entrypoint=''") {
                unstash 'source'
                sh '''
                    checkov -d . \
                    --use-enforcement-rules \
                    -o cli -o junitxml \
                    --output-file-path console,results.xml \
                    --bc-api-key ${pc_user}::${pc_password} \
                    --repo-id krash/terragoat \
                    --branch master \
                    --prisma-api-url https://app2.prismacloud.io 
                '''
            }
        }
    }
}
return this
