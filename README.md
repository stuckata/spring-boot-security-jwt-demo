# Spring Boot JWT Demo

http://localhost:9090/swagger-ui/index.html

## How-to create a project

1). Go to ["Spring Initializr"](https://start.spring.io/)

2). Generate Project
![01_Spring_Initilizr.png](docs/01_Spring_Initilizr.png)

## How-to add project to empty GIT repo

```
echo "# spring-boot-security-jwt-demo" >> README.md
git init
git add README.md
git commit -m "first commit"
git branch -M main
git remote add origin https://github.com/stuckata/spring-boot-security-jwt-demo.git
git push -u origin main
```

## How-to generate *YOUR OWN* Public-Private Key Pair
https://app.id123.io/free-tools/key-generator/


## Kill process, listening on specific port

> netstat -ano | findstr :1521
> taskkill /PID 4376 /F