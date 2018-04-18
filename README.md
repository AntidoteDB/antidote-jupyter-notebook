# Antidote-kernel Jupyter Notebook

[AntidoteDB](http://syncfree.github.io/antidote) is a non-SQL style database which is fast and available even under network partition. It provides a low-level key-value interface and handles data inconsistencies due to asynchronous communication among the servers.

The [Jupyter](https://jupyter.org) Antidote kernel is a Jupyter kernel based on the [Jupyter Groovy Kernel](https://github.com/lappsgrid-incubator/jupyter-groovy-kernel) that connects to Antidote services. It allows users to interact with Antidote databases and observe how Antidote resolves inconsistencies in case of network failure.

This notebook is based on [Antidote Java API](https://www.javadoc.io/doc/eu.antidotedb/antidote-java-client/0.1.0), but it is tailored for tutorial purpose. As a result, not all Antidote functionalities are realized in the notebook. 

## Installation

### From Source

Building the Antidote-kernel Jupyter Notebook project requires Maven 3.x or higher.

```bash
$> cd jupyter-antidote-kernel
$> make
```

## Docker Compose

To start up the Antidote-kernel notebook, you need to execute the following commands which builds and starts all service containers.

```bash
$> cd jupyter-antidote-kernel/src/docker
$> docker-compose up
```

For Docker Toolbox and Docker Machine Users, you can find out the ip address which is mapped to localhost by using the following command.

```bash
$> docker-machine ip
```
