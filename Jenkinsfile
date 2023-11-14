node {
    def WORKSPACE = "/var/lib/jenkins/workspace/visit-back-demo-deploy"
    def dockerImageTag = "visitback${env.BUILD_NUMBER}"
    def dockerHubUsername = "montassar95"
    def dockerHubPassword = "123456docker"

    try {
        notifyBuild('STARTED')

        stage('Clone Repo') {
            // Obtenir du code depuis un référentiel GitHub
            git url: 'https://github.com/montassar95/visitback.git',
                credentialsId: 'visitback-user',
                branch: 'master'
        }

        stage('Build Docker') {
            // Construire l'image Docker
            dockerImage = docker.build("visitback:${dockerImageTag}")
        }

        stage('Push vers Docker Hub') {
            // Connecter Docker à Docker Hub
            sh "docker login -u ${dockerHubUsername} -p ${dockerHubPassword}"

            // Pousser l'image vers Docker Hub
            sh "docker push ${dockerHubUsername}/visitback:${dockerImageTag}"
        }

        stage('Déployer Docker') {
            echo "Nom du tag de l'image Docker : ${dockerImageTag}"
          //  sh "docker stop visitback || true && docker rm visitback || true"
          //  sh "docker run --name visitback -d -p 8081:8081 ${dockerHubUsername}/visitback:${dockerImageTag}"
        }

    } catch (e) {
        currentBuild.result = "FAILED"
        throw e
    } finally {
        notifyBuild(currentBuild.result)
    }
}

def notifyBuild(String buildStatus = 'STARTED') {
    // build status of null means successful
    buildStatus = buildStatus ?: 'SUCCESSFUL'

    // Default values
    def colorName = 'RED'
    def colorCode = '#FF0000'
    def now = new Date()

    // Message
    def subject = "${buildStatus}, Job: ${env.JOB_NAME} FRONTEND - Sequence de déploiement : [${env.BUILD_NUMBER}] "
    def summary = "${subject} - Vérifiez sur : (${env.BUILD_URL}) - Heure : ${now}"
    def subject_email = "Déploiement Spring Boot"
    def details = """<p>${buildStatus} JOB </p>
    <p>Job: ${env.JOB_NAME} - Sequence de déploiement : [${env.BUILD_NUMBER}] - Heure : ${now}</p>
    <p>Vérifiez la sortie de la console à "<a href="${env.BUILD_URL}">${env.JOB_NAME}</a>"</p>"""
}
