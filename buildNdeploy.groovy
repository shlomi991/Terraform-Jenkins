
import groovy.json.JsonSlurperClassic
@NonCPS
def parseJsonToMap(String json) {
    final slurper = new JsonSlurperClassic()
    return new HashMap<>(slurper.parseText(json))
}

pipeline {
    agent any
    parameters {
        string(name: 'branch', defaultValue: 'main', description: 'Branch to build code from?', trim: true)
    }
    
    environment 
    {
        USER_DOCKER = "shlomi99"
        RELEASE_NAME = "commit"
        PROJECT_ID="shlomi-interview"
        IMAGE_TAG = "${env.BUILD_ID}"
    }
    
    stages
    {

      stage('Checkout code') {
        steps {
          git branch: env.branch, credentialsId: '45146593-c75f-4562-9d18-8dcd58aaa713', url: "git@github.com:shlomi991/mysite.git"
        }
      }

      stage('Docker build')
      {
        steps
        {
            sh 'env'
            sh '''
                  export IMAGE_REPOSITORY=gcr.io/<${PROJECT_ID}/${USER_DOCKER}/${RELEASE_NAME}

                  echo "IMAGE_REPOSITORY:" $IMAGE_REPOSITORY
                  echo "IMAGE_TAG:" $IMAGE_TAG

                  docker pull $IMAGE_REPOSITORY:latest 
                  docker build -t $IMAGE_REPOSITORY:$IMAGE_TAG -t $IMAGE_REPOSITORY:latest .
                  docker push $IMAGE_REPOSITORY:$IMAGE_TAG
                  docker push $IMAGE_REPOSITORY:latest
                '''
        }
      }

      stage('TF') {
        steps {
          dir("terraform")  {
              sh  '''
                    gcloud config set project PROJECT_ID
                    terraform init
                    terraform apply -auto-approve -no-color -var="image=${IMAGE_REPOSITORY}:${IMAGE_TAG}"
                  '''
         }
       }
     }
    }

    post {
        failure {
            script
            {
                echo "Failed"
            }
        }
        success {
            script 
            {
                echo "The app is deployed"
            }
        }
        always { 
            cleanWs()
        }
    }
  }
