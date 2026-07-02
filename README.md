Cloud-Native Supply Chain System

A full-stack, cloud-native order management system orchestrated with Kubernetes.

🏗️ Architecture

Frontend: React + Vite + Nginx (Port 8082)

Backend: Kotlin + Spring Boot (Port 30080)

Database: PostgreSQL (Port 5432)

Infrastructure: Docker, Kubernetes (Docker Desktop)

🚀 Getting Started

1. Build Images

Ensure frontend/.env contains VITE_API_URL=http://localhost:30080.

# Build Backend
cd backend
docker build -t order-service:1.0.0 .

# Build Frontend
cd ../frontend
docker build -t frontend:1.0.8 .


2. Deploy

Apply all configurations:

cd ..
kubectl apply -f k8s/


3. Access

Frontend UI: http://localhost:8082

Backend API: http://localhost:30080/api/orders

🛠️ Developer Commands

Logs: kubectl logs deploy/<service-name>

DB Access (CLI): kubectl exec -it deploy/postgres-deployment -- psql -U user -d orders_db

DB Access (GUI): kubectl port-forward svc/postgres-service 5432:5432

⚠️ Notes

Environment Variables: Use the VITE_ prefix for frontend variables. Ensure .env is not ignored by .dockerignore.

Networking: Uses LoadBalancer services to map ports directly to your host network.

🗺️ Roadmap

[x] Establish synchronous 3-tier K8s architecture

[x] Dockerize React frontend

[ ] Implement Apache Kafka for async event broadcasting

[ ] Deploy MongoDB read-replica

[ ] Add Inventory and Shipping microservices
