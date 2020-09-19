Tanzu Compute
==================
## 1. Helm install Repo

- helm repo add bitnami https://charts.bitnami.com/bitnami #bitnami charts
- helm repo add stable https://kubernetes-charts.storage.googleapis.com #stable charts
- helm repo add incubator https://kubernetes-charts-incubator.storage.googleapis.com #incubator charts
- helm repo add loki https://grafana.github.io/loki/charts #bitnami charts
- helm repo list
- helm repo update

## 2. Addons
* [x] base
* [x] cert-manager
* [x] contour
* [x] metallb
* [x] metrics-server
* [x] nginx-ingress
* [x] node-problem-detector
* [x] persistent-volume
* [x] test-apps
* Observability stacks
  * [x] prometheus
  * [x] loki
  * [x] grafana
