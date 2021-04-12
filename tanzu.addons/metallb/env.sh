export METALLB_SPEAKER_SECRET=$(yq read "${STORAGE_CONFIG}" metallb.speaker.secret)
