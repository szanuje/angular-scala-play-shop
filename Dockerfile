FROM ubuntu:18.04

RUN apt-get update && apt-get upgrade -y && \
    apt-get install -y curl zip unzip openjdk-8-jdk gnupg2

RUN echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | tee /etc/apt/sources.list.d/sbt.list && \
    echo "deb https://repo.scala-sbt.org/scalasbt/debian /" | tee /etc/apt/sources.list.d/sbt_old.list && \
    curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" | apt-key add && \
    apt-get update -y && \
    apt-get install -y sbt

RUN useradd -ms /bin/bash max && adduser max sudo

WORKDIR /home/max/project
COPY . /home/max/project
RUN sbt compile
EXPOSE 9000
CMD sbt run