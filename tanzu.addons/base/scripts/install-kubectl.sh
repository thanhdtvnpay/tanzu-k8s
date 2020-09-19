#!/bin/bash

KUBECTL_VERSION=v1.19.0
curl -sSL "https://storage.googleapis.com/kubernetes-release/release/${KUBECTL_VERSION}/bin/linux/amd64/kubectl" \
      -o /usr/local/bin/kubectl; \
chmod +x /usr/local/bin/kubectl; \
kubectl completion bash > /etc/bash_completion.d/kubectl;

KUSTOMIZE_VERSION=v3.8.2
curl -sSL "https://github.com/kubernetes-sigs/kustomize/releases/download/kustomize%2F${KUSTOMIZE_VERSION}/kustomize_${KUSTOMIZE_VERSION}_linux_amd64.tar.gz" \
        | tar -xzvf - -C /usr/local/bin/; \
echo yes | kustomize install-completion;

KUBECTL_OIDC_LOGIN_VERSION=v1.20.1
curl -LO "https://github.com/int128/kubelogin/releases/download/${KUBECTL_OIDC_LOGIN_VERSION}/kubelogin_linux_amd64.zip";
unzip kubelogin_linux_amd64.zip kubelogin;
mv ./kubelogin "/usr/local/bin/kubectl-oidc_login";
rm kubelogin_linux_amd64.zip

KUBECTL_KSD_VERSION=v3.0.0
curl -sSL "https://github.com/ashleyschuett/kubernetes-secret-decode/releases/download/${KUBECTL_KSD_VERSION}/kubernetes-secret-decode_${KUBECTL_KSD_VERSION#v}_Linux_x86_64.tar.gz" \
  | tar -xzvf - "kubectl-ksd" -C /usr/local/bin/;
