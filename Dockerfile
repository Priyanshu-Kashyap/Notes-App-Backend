FROM gradle:7.0-jdk11 as build

WORKDIR /app

COPY build.gradle .
COPY src src

RUN gradle clean bootJar -x test --no-daemon && mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)

FROM azul/zulu-openjdk-alpine:11-jre

ARG DEPENDENCY=/app/build/dependency

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","/app:/app/lib/*","me.projects.notesappbackend.NotesAppBackendApplication"]
