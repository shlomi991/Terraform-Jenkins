  service_name           = "hello"
  project_id             = "shlomi-interview"
  location               = "us-central1"
  image                  = "gcr.io/cloudrun/hello"
  service_annotations    = {
    "run.googleapis.com/ingress" = "internal"
  }
