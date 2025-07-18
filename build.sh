# build react app
cd frontend
npm run build

# dist(html css js) 폴더의 파일을 static 폴더로 옮김
mv dist ../backend/src/main/resources/static

# build spring boot app
cd ../backend
./gradlew bootJar

# .jar 를 docker container 에 넣고 실행
docker stop prj3
docker rm prj3

docker build -t prj3 .
docker run -d -p 8080:8080 --restart always --name prj3 prj3

# 필요없는 파일 지우기
docker image prune -f