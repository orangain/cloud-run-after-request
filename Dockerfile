# Use the official gradle image to create a build artifact.
# https://hub.docker.com/_/gradle
FROM gradle:6.4.0-jdk11 as builder

# Copy local code to the container image.
COPY *.gradle.kts gradle.properties ./
COPY src ./src/
COPY resources ./resources/

# Build a release artifact.
RUN gradle clean build --no-daemon

# Use the Official OpenJDK image for a lean production stage of our multi-stage build.
# https://hub.docker.com/_/adoptopenjdk
FROM adoptopenjdk:11-jre-hotspot

ADD https://storage.googleapis.com/berglas/0.5.1/linux_amd64/berglas /usr/local/bin/

RUN chmod +rx /usr/local/bin/berglas

# Copy the jar to the production image from the builder stage.
COPY --from=builder /home/gradle/build/libs/*.jar /app.jar

# Run the web service on container startup.
CMD [ "berglas", "exec", "--", "java", "-jar", "-Djava.security.egd=file:/dev/./urandom", "/app.jar" ]
