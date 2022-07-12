# first web application
tomcat 9

---

### RUN .war
Start:
- `mvn clean package`
- download Tomcat 9: https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.64/bin/apache-tomcat-9.0.64.zip
- `unzip apache-tomcat-9.0.64.zip`
- `chmod +x ./apache-tomcat-9.0.64/bin/*`
- `cp -r ./target/cinema.war ./apache-tomcat-9.0.64/webapps/`
- `bash ./apache-tomcat-9.0.64/bin/startup.sh`

Stop: `bash ./apache-tomcat-9.0.64/bin/shutdown.sh`

---

### RUN docker compose (postgresql and tomcat containers)
Start containers: `docker compose up -d`  
Stop containers: `docker compose down`  
Delete volumes: `docker volume rm $(docker volume ls -q)`

---

- http://localhost:8080/cinema/
- http://localhost:8080/cinema/signin
- http://localhost:8080/cinema/signup
- http://localhost:8080/cinema/profile
