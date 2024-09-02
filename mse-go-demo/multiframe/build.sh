cd A
CGO_ENABLED=0 GOOS=linux GOARCH=amd64 ../aliyun-go-agent -mse
docker build -t gin-server-a:2.0 .
docker tag gin-server-a:2.0 registry.cn-hangzhou.aliyuncs.com/mse-governance-demo/gin-server-a:2.0
docker push registry.cn-hangzhou.aliyuncs.com/mse-governance-demo/gin-server-a:2.0
cd ..

cd B
CGO_ENABLED=0 GOOS=linux GOARCH=amd64 ../aliyun-go-agent -mse
docker build -t go-kratos-demo-b:2.0 .
docker tag go-kratos-demo-b:2.0 registry.cn-hangzhou.aliyuncs.com/mse-governance-demo/go-kratos-demo-b:2.0
docker push registry.cn-hangzhou.aliyuncs.com/mse-governance-demo/go-kratos-demo-b:2.0
cd ..

cd C
CGO_ENABLED=0 GOOS=linux GOARCH=amd64 ../aliyun-go-agent -mse
docker build -t go-zero-demo-c:2.0 .
docker tag go-zero-demo-c:2.0 registry.cn-hangzhou.aliyuncs.com/mse-governance-demo/go-zero-demo-c:2.0
docker push registry.cn-hangzhou.aliyuncs.com/mse-governance-demo/go-zero-demo-c:2.0
cd ..

kubectl apply -f deployment.yaml


