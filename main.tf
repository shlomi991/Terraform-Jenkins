module "cloud_run" {
  source  = "GoogleCloudPlatform/cloud-run/google"
  version = "~> 0.2.0"

 
  service_name           = var.service_name
  project_id             = var.project_id
  location               = var.location
  image                  = var.image
  service_annotations    = var.service_annotations
}