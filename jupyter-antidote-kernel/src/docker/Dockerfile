# jupyter-antidote-kernel

FROM jupyter/notebook
MAINTAINER Samitha Jayathilake, Thanjira Amornkosit

USER root

RUN apt-get update && apt-get install -y openjdk-7-jdk
RUN mkdir -p /jupyter/antidote
COPY ./jupyter-antidote-kernel-1.1.0-SNAPSHOT.jar /jupyter
COPY kernel.json /jupyter/antidote
COPY startup.sh /usr/bin/startup
RUN chmod a+x /usr/bin/startup
RUN jupyter kernelspec install --name=antidote /jupyter/antidote

WORKDIR /home/jovyan
CMD ["/usr/bin/startup"]



