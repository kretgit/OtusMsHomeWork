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

run minukube via terminal using docker drivers
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

## Prometheus & Grafana

install via helm
````
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo add grafana https://grafana.github.io/helm-charts
helm repo update
helm install prometheus prometheus-community/kube-prometheus-stack --namespace monitoring --create-namespace

helm install prometheus prometheus-community/kube-prometheus-stack \
--namespace monitoring \
--create-namespace \
--set grafana.enabled=false

если еще нет пароля для графаны (default = prom-operator):
kubectl get secret --namespace monitoring prometheus-grafana -o jsonpath="{.data.admin-password}" | base64 --decode ; echo

если не нужна/понадобилась графана:
kubectl delete deployment prometheus-grafana -n monitoring && kubectl delete service prometheus-grafana -n monitoring
helm upgrade prometheus prometheus-community/kube-prometheus-stack --namespace monitoring --set grafana.enabled=true
````

## kubectl useful commands

````
kubectl create ns app && kubectl config set-context --current --namespace=app

kubectl apply -f deployment.yaml
kubectl get deploy
kubectl rollout restart deployment order-service

kubectl get po
kubectl get po -o wide
kubectl exec -it user-serivce-deployment-5b589d6479-nsvsj -- bash
curl http://localhost:8000/user/health

можно создать временный под, выполнить команду, затем при выходе он самоуничтожится:
kubectl run -it --rm debug --image=busybox --restart=Never --namespace=app -- sh
wget http://user-service.app.svc.cluster.local:8080/actuator/prometheus

kubectl rollout history deployment/user-serivce-deployment
kubectl logs (-f как и tail -f) user-serivce-deployment-5b589d6479-7r29v

kubectl get svc
kubectl get nodes -o wide
kubectl get all -n monitoring | grep grafana

kubectl delete -n app deployment kitchen-service && kubectl delete -n app service kitchen-service && kubectl delete -n app deployment notification-service && kubectl delete -n app service notification-service
kubectl delete -n app deployment order-service && kubectl delete -n app service order-service
kubectl delete pods --all -n monitoring
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

# План показа демо

 - Docker Desktop запущен, внутри него все запущено
 - minikube tunnel запущен
 - нет лишних неймспейсов
 - логи в терминале бегут, поды на месте

## без авторизации с 3-мя сервисами
````
kubectl create ns app && \
kubectl config set-context --current --namespace=app

kubectl apply -f kitchen-service/kubernetes && \
kubectl apply -f notification-service/kubernetes && \
kubectl apply -f order-service/kubernetes && \
kubectl apply -f ingress/demo-ingress.yaml
````
 - показать `ActiveMq`
 - показать `/order/recent`

## авторизация с 2-мя сервисами и обновленными ингрессами
````
kubectl delete -n app deployment kitchen-service && \
kubectl delete -n app service kitchen-service && \
kubectl delete -n app deployment notification-service && \
kubectl delete -n app service notification-service && \
kubectl delete -n app ingress demo-ingress

kubectl apply -f user-service/kubernetes && \
kubectl apply -f ingress/auth-ingress.yaml
````
 - показать `headers` в логах

## мониторинг 1-го сервиса
````
kubectl delete -n app deployment order-service && \
kubectl delete -n app service order-service

minikube dashboard

helm install prometheus prometheus-community/kube-prometheus-stack \
--namespace monitoring \
--create-namespace \
--set grafana.enabled=false

kubectl apply -f ingress/monitoring-ingress.yaml
````
 - показать Prometheus
 - показать Postgre (Liquibase)