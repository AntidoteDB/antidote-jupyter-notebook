# jupyter-antidote-kernel

FROM jupyter/base-notebook
MAINTAINER Samitha Jayathilake, Thanjira Amornkosit

USER root

# install system-wide deps
RUN apt-get update && \
	apt-get install -y openjdk-8-jdk

# install docker
RUN apt-get install -y docker.io

# update jupyter notebook if any
#RUN pip install --upgrade pip
#RUN pip install --upgrade notebook
 
# add Antidote kernel to jupyter notebook
RUN mkdir -p /jupyter/antidote
COPY ./jupyter-antidote-kernel.jar /jupyter/
COPY ./kernel.json /jupyter/antidote

# add Antidote tutorial notebook
COPY ["./Welcome to Antidote.ipynb", "./Welcome to Antidote.ipynb"]

# COPY startup.sh /usr/bin/startup
# RUN chmod a+x /usr/bin/startup
RUN jupyter kernelspec install --name=antidote /jupyter/antidote

# WORKDIR /home/jovyan
# CMD ["/usr/bin/startup"]

# launch jupyter notebook
RUN jupyter notebook --allow-root
