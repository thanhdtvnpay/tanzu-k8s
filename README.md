Tanzu Compute
==================
## 1. Requirement (install script on base/scripts on project)
- kubectl
- helm, helmfile, diff
- ...

## 2. Helm install Repos
- helm repo add bitnami https://charts.bitnami.com/bitnami #bitnami charts
- helm repo add stable https://kubernetes-charts.storage.googleapis.com #stable charts
- helm repo add incubator https://kubernetes-charts-incubator.storage.googleapis.com #incubator charts
- helm repo add loki https://grafana.github.io/loki/charts #loki charts
- helm repo list
- helm repo update

## 3. Kubectl Command CLI
- Get pod, service, ingress ...

```javascript
kubectl get node -A -o wide
kubectl -n namespace get pod
kubectl -n namespace get service
kubectl -n namespace get deployments.apps
kubectl -n namespace get ingress
kubectl -n namespace get replicasets.apps
....
```
- Describe pod, service, ingress ...
```javascript
kubectl -n namespace describe pods name-pod
kubectl -n namespace describe service name-service
kubectl -n namespace describe ingress name-ingress
....
```
- Logs pod
```javascript
kubectl -n namespace logs -f name-pod
```
- Exec to pod
```javascript
kubectl -n namespace exec -it client-pod -- bash
```

## 4. Addons
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
