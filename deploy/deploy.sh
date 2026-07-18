#!/usr/bin/env bash
#
# Redeploy de EduCore a la VM de GCP.
# Construye la imagen localmente (la e2-micro no tiene RAM para compilar),
# la transfiere y la levanta con docker compose + Caddy (HTTPS) en la VM.
#
# Uso:   ./deploy/deploy.sh
# Requisitos: docker (local), gcloud autenticado en la cuenta correcta.

set -euo pipefail

# ── Config ───────────────────────────────────────────────────
PROJECT="educore-502817"
ZONE="us-central1-f"
INSTANCE="instance-educore-backend"
IMAGE="educore-backend:latest"
REMOTE_DIR="/opt/educore"

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
TMP_TAR="$(mktemp -t educore-XXXX).tar.gz"
trap 'rm -f "$TMP_TAR"' EXIT

ssh_vm() { gcloud compute ssh "$INSTANCE" --project="$PROJECT" --zone="$ZONE" --command="$1"; }

echo "==> [1/5] Construyendo imagen $IMAGE ..."
docker build -t "$IMAGE" "$ROOT_DIR"

echo "==> [2/5] Exportando imagen comprimida ..."
docker save "$IMAGE" | gzip > "$TMP_TAR"
echo "    tamaño: $(du -h "$TMP_TAR" | cut -f1)"

echo "==> [3/5] Transfiriendo a la VM ..."
gcloud compute scp "$TMP_TAR"                       "$INSTANCE:/tmp/educore-backend.tar.gz" --project="$PROJECT" --zone="$ZONE"
gcloud compute scp "$ROOT_DIR/deploy/Caddyfile"     "$INSTANCE:/tmp/Caddyfile"              --project="$PROJECT" --zone="$ZONE"
gcloud compute scp "$ROOT_DIR/deploy/docker-compose.vm.yml" "$INSTANCE:/tmp/docker-compose.vm.yml" --project="$PROJECT" --zone="$ZONE"

echo "==> [4/5] Cargando imagen y desplegando en la VM ..."
ssh_vm "
  set -e
  sudo mkdir -p $REMOTE_DIR
  sudo mv /tmp/Caddyfile /tmp/docker-compose.vm.yml $REMOTE_DIR/
  sudo docker load -i /tmp/educore-backend.tar.gz
  rm -f /tmp/educore-backend.tar.gz
  cd $REMOTE_DIR
  sudo docker compose -f docker-compose.vm.yml up -d
  sudo docker image prune -f
"

echo "==> [5/5] Verificando ..."
sleep 5
ssh_vm "sudo docker compose -f $REMOTE_DIR/docker-compose.vm.yml ps"

echo ""
echo "Listo. API GraphQL: https://35-225-232-209.sslip.io/graphql"
