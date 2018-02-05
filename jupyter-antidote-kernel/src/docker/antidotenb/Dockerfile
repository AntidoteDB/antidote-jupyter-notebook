FROM jupyter/base-notebook

USER root

RUN apt-get update && apt-get install -y openjdk-8-jdk
RUN apt-get install -y docker.io

RUN mkdir -p /jupyter/antidote
COPY jupyter-antidote-kernel.jar /jupyter/jupyter-antidote-kernel.jar
COPY kernel.json /jupyter/antidote/kernel.json
RUN jupyter kernelspec install --name Antidote /jupyter/antidote

COPY ./images ./images
COPY ["./Welcome to Antidote.ipynb", "./Welcome to Antidote.ipynb"]

#VOLUME ["/home/jovyan/work"]
#WORKDIR /home/jovyan/work
CMD ["jupyter", "notebook","--allow-root"]