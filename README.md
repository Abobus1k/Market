# Market
Для запуска проекта нужно на сервер установить maven.
После установки нужно выполнить команду mvn clean package -DskipTests (Для более быстрой сборки)
После этого нужно выполнить команду sudo docker-compose up -d --build
После запуска можно открыть документацию по адресу http://localhost:8003/swagger-ui.html

Так же из-за того что сервис разврнут документацию можно посмотреть по адресу http://nedomarket.ru:8003/swagger-ui.html
