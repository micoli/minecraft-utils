#!/bin/sh
mvn -DaltDeploymentRepository=micoli-snapshots-repo::default::file:../maven-repo/snapshots clean deploy
