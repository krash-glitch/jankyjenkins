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
                    --bc-api-key cc67ac84-1d07-4643-95e6-7b24780fa0c1::WuRn23bdxr8YlICIfk6OQewEE40= \
                    --repo-id krash/terragoat \
                    --branch master \
                    --prisma-api-url https://app2.prismacloud.io 
                '''
            }
        }
    }
}
return this
