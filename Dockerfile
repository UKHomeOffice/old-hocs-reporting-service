FROM jdk-alpine

ENV USER user_sls_data
ENV GROUP group_sls_data
ENV NAME sls-data
ENV JAR_PATH build/libs

RUN yum update -y glibc && \
    yum update -y nss && \
    yum update -y bind-license

WORKDIR /app

RUN groupadd -r ${GROUP} && \
    useradd -r -g ${GROUP} ${USER} -d /app && \
    mkdir -p /app && \
    chown -R ${USER}:${GROUP} /app

COPY ${JAR_PATH}/${NAME}*.jar /app
COPY run.sh /app

RUN chmod a+x /app/run.sh

EXPOSE 8081

USER ${USER}

ENTRYPOINT /app/run.sh
