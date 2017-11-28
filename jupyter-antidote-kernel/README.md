# Jupyter LSD Kernel, TODO Update Readme

The Lappsgrid Serivces DSL (LSD) is a Groovy based DSL (Domain Specific Language) that can be used to invoke LAPPS Grid web services from Groovy scripts. 

The [Jupyter](https://jupyter.org) LSD kernel is a Jupyter kernel based on the [Jupyter Groovy Kernel](https://github.com/lappsgrid-incubator/jupyter-groovy-kernel) that not only allows LSD scripts to be run in a Jupyter Notebook, but also allows scripts to interact with a Galaxy instance, for example the [LAPPS/Galaxy](https://galaxy.lappsgrid.org) instance.

## Table of contents
1. [Installation](#installation)
1. [Connecting to Galaxy](#connecting-to-galaxy)
1. [Functions](#notebook-functions)
1. [Docker](#docker)

## Installation

### From Source

Building the Jupyter LSD Kernel project requires Maven 3.x or higher.

```bash
$> git clone https://github.com/lappsgrid-incubator/jupyter-lsd-kernel.git 
$> cd jupyter-lsd-kernel
$> mvn package
$> ./install.sh <kernel directory>
```

Where *&lt;kernel directory&gt;* is a directory where the kernel jar file will be copied and can be any directory on your system.

### From Pre-compiled Binaries

Download and expand the [LSD Kernel archive](http://www.lappsgrid.org/downloads/jupyter-lsd-kernel-latest.tgz) and then run the *install.sh* script.

```bash
$> wget http://www.lappsgrid.org/downloads/jupyter-lsd-kernel-latest.tgz
$> tar xzf jupyter-lsd-kernel-latest.tgz
$> cd jupyter-lsd-kernel-x.y.z
$> ./install.sh <kernel directory>
```

Where *&lt;kernel directory&gt;* is a directory where the kernel jar file will be copied and can be any directory on your system. Replace *x.y.z* with the current version number.

### Notes

By default the *install.sh* script will install the Jupyter kernel to the system kernel directory. This is typically */usr/local/share/juptyer* on Linux/MacOS systems and %PROGRAMDATA%\jupyter\kernels on Windows systems.  To install the Jupyter kernel to the User's directory you must either:

* Edit the *install.script* and add the *--user* option to the `kernelspec` command, or
* Edit the kernel.json file to set the *argv* paramater to the location of the Jupyter Groovy kernel and then run the `jupyter kernelspec install` command manually.

You can view the default directories that Jupyter uses by running the command `jupyter --paths`.

## Connecting to Galaxy

The LSD kernel needs two pieces of information to be able to interact with Galaxy:

1. The URL of the Galaxy server.
1. Your Galaxy API key.  If you do not have a Galaxy API key you will need to log in to the Galaxy instance and generate one (User -> Preferences -> Manager your API keys.)

You will need to set the environment variables **GALAXY_HOST** and **GALAXY_KEY** before launching Jupyter:

```bash
$> export GALAXY_HOST=http://galaxy.example.com:8000
$> export GALAXY_KEY=1234567890DEADBEEF
$> jupyter notebook
```

## Notebook Functions

The Jupyter LSD kernel provides serveral functions to simplify interacting with a Galaxy instance.

**void selectHistory(String history_name)**<br/>
Select the Galaxy history to work with.  It is a good practice to always name histories in Galaxy so they may be easily selected in Jupyter.  Since the Galaxy API has no concept of the *current history* it is impossible to select a history if they are all named *"Unnamed history"*.

**File get(int history_id)**<br/>
Returns a *java.io.File* object with the contents of the dataset with the given history id. The history id is simply the integer to the left of the dataset name in the history panel.

**void put(Stirng path)**<br/>
**void put(File file)**<br/>
Uploads the file to the currently selected Galaxy history.  You may need to refresh the history in Galaxy to see the newly uploaded file.  The dataset name will be the name of the uploaded file.

**Object parse(String json)**<br/>
**Object parse(String json, Class theClass)**<br/>
Parses a JSON string into an instance of **theClass**.  Calling `parse(json)` is the same as calling `parse(json, org.lappsgrid.serialization.Data)`.

**String toJson(Object object)**<br/>
Returns the compact JSON string representation of the `object`.

**String toPrettyJSon(Object object)**<br/>
Returns a pretty-printed JSON string representation of the `object`.

**GalaxyInstance galaxy()**<br/>
**HistoriesClient histories()**<br/>
**ToolsClient tools()**<br/>
**History history()**<br/>
Returns handles to the various Galaxy clients. See the Blend4J API documentation (link needed) for more information on these clients.

## Docker

A Docker image containing the LSD kernel is available from the [Docker Hub](https://hub.docker.com/r/lappsgrid/jupyter-lsd-kernel/).  The container expects two environment variables to be defined that are used to connect to a Galaxy server:

1. **GALAXY_HOST** The URL to a Galaxy server.
1. **GALAXY_KEY** The API key for the user on the Galaxy server.

Inside the container Jupyter uses the directory */home/jovyan* to save and load notebooks.  To persists notebooks created inside the container mount a local directory as */home/jovyan*.

```bash
$> HOST=http://galaxy.lappsgrid.org
$> KEY=1234567890DEADBEEF
$> ENV="-e GALAXY_HOST=$HOST -e GALAXY_KEY=$KEY"
$> VOLUME="-v $HOME/notebooks:/home/jovyan"
$> PORTS="-p 8888:8888"
$> docker run -d $PORTS $ENV $VOLUME lappsgrid/jupyter-lsd-kernel 
```
