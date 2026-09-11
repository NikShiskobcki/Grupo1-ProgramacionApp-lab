FROM eclipse-temurin:21-jdk

RUN apt-get update && apt-get install -y \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libfreetype6 \
    libgl1-mesa-dri \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY dist/ /app/
COPY lib/ /app/lib/


CMD ["java", "-cp", "Grupo1-DktpApp.jar:lib/*", "Presentacion.JFMain"]
