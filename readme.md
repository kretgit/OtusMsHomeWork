# OTUS micro-services course homework

build project
````
mvn clean package
````

build project module
````
mvn --projects 'common,user-service' clean package -DskipTests=true
````

## minikube on macbook M1/M2 chip

install minikube
````
curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-darwin-arm64
sudo install minikube-darwin-arm64 /usr/local/bin/minikube
````

run minukube via terminal using docker drivers (docker should be running)
````
minikube start
minikube start --driver=docker --alsologtostderr
minikube status
minikube delete --all --purge
````

## ingress

install ingress via helm
````
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm repo update
helm install ingress-nginx ingress-nginx/ingress-nginx --namespace ingress-nginx --create-namespace
````

config ingress via minikube
````
minikube addons list
minikube addons enable ingress
minikube addons enable ingress-dns
minikube tunnel
````

## kubectl useful commands

````
kubectl create ns app
kubectl config set-context --current --namespace=app

kubectl apply -f deployment.yaml
kubectl get deploy
kubectl rollout restart deployment order-service

kubectl get po
kubectl get po -o wide
kubectl exec -it user-serivce-deployment-5b589d6479-nsvsj -- bash
curl http://localhost:8000/user/health

kubectl rollout history deployment/user-serivce-deployment
kubectl logs (-f как и tail -f) user-serivce-deployment-5b589d6479-7r29v

kubectl get svc
kubectl get nodes -o wide

kubectl delete pods,services,deployments -l name=user-service
kubectl -n app2 delete pod,svc,deployments --all
kubectl delete all --all -n app
````

## minikube useful commands

````
minikube ssh

curl http://10.244.0.8:8000/user/health

curl --location 'http://<kubectl get svc CLUSTER-IP>:8300/order/create' \
--header 'Content-Type: application/json' \
--data '{
    "items": {
        "PIZZA": 1,
        "COLA": 1,
        "BURGER": 1
    }
}'

````