# Amazing Company project

## Terraform
I used terraform to deploy my apllication on Cloud Run, And I chose to use environment variables to make my files more dynamic.


## CI/CD (Jenkins)

Runnig build and deploy in the same file.

First, we need to configure Jenkins with GCP, [Do it by this guide](https://breakdance.github.io/breakdance/).

The build procces: Build our image and tag with Jenkins BUILD_ID (Great way to get random tag) and we push it to our repository.
By the way, we tagging latest and push as latest as well (help us to follow after our versions).

The deploy procces: We going to terrafom folder, And we using terraform commands by gcloud cli to deploy our app.


