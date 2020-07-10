node {
    def DOCKERHUB_REPO = "karthikreddyvkr1794/tracker-api"
    def DOCKER_SERVICE_ID = "tracker-service"
    def DOCKER_IMAGE_VERSION = ""

    stage("clean workspace") {
        deleteDir()
    }

    stage("git checkout") {
        checkout scm

        def GIT_COMMIT = sh(returnStdout: true, script: "git rev-parse HEAD").trim().take(7)
        DOCKER_IMAGE_VERSION = "${BUILD_NUMBER}-${GIT_COMMIT}"
    }

    stage("mvn build") {
        sh "mvn clean install -DskipTests"
    }

    stage("docker build") {
        sh "docker build -t ${DOCKERHUB_REPO}:${DOCKER_IMAGE_VERSION} ."
    }

    stage("docker push") {

        withDockerRegistry(credentialsId: 'dockerhub') {
            sh "docker push ${DOCKERHUB_REPO}:${DOCKER_IMAGE_VERSION}"
        }
    }

    stage("docker service") {
        try {
            // Create the service if it doesn't exist otherwise just update the image
            sh """
                if [ \$(docker service ls --filter name=${DOCKER_SERVICE_ID} --quiet | wc -l) -eq 0 ]; then
                  docker service create --mode=global --publish mode=host,target=8080,published=8080 \
                    --name ${DOCKER_SERVICE_ID} \
                    ${DOCKERHUB_REPO}:${DOCKER_IMAGE_VERSION}
                fi
            """
        }
        catch (e) {
            sh "docker service update --rollback ${DOCKER_SERVICE_ID}"
            error "Service update failed. Rolling back ${DOCKER_SERVICE_ID}"
        }
        finally {
            sh "docker container prune -f"
            sh "docker image prune -af"
        }
    }
}