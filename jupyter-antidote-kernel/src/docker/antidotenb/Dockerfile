FROM jupyter/base-notebook

USER root

RUN apt-get update && apt-get install -y openjdk-8-jdk
RUN apt-get install -y docker.io
RUN apt-get install -y erlang-base

RUN mkdir -p /jupyter/antidote
COPY jupyter-antidote-kernel.jar /jupyter/jupyter-antidote-kernel.jar
COPY kernel.json /jupyter/antidote/kernel.json
COPY entrypoint.sh /
COPY link.sh /
COPY connect-dcs.erl /
RUN chmod a+x /entrypoint.sh && chmod a+x /link.sh
RUN jupyter kernelspec install --name Antidote /jupyter/antidote

COPY ./images ./images

#VOLUME ["/home/jovyan/work"]
#WORKDIR /home/jovyan/work
ENTRYPOINT [ "/entrypoint.sh" ]
CMD ["jupyter", "notebook","--allow-root"]
