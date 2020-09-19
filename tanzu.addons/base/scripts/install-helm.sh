#!/bin/bash

HELM_VERSION=v3.3.1
curl -sSL "https://github.com/helm/helm/raw/master/scripts/get-helm-3" | bash -s -- --version "${HELM_VERSION}";
helm completion bash > /etc/bash_completion.d/helm;

HELM_DIFF_VERSION=v3.1.2
mkdir /usr/local/bin/helm-plugins
curl -sSL "https://github.com/databus23/helm-diff/releases/download/${HELM_DIFF_VERSION}/helm-diff-linux.tgz" \
  | tar -xzvf - -C /usr/local/bin/helm-plugins/;

HELMFILE_VERSION=v0.128.0
curl -sSL "https://github.com/roboll/helmfile/releases/download/${HELMFILE_VERSION}/helmfile_linux_amd64" \
    -o /usr/local/bin/helmfile; \
chmod +x /usr/local/bin/helmfile;

cat <<EOF > /etc/profile.d/helm.sh
export HELM_PLUGINS=/usr/local/bin/helm-plugins/
EOF
