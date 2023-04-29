#!/bin/sh
#source /Users/Roman_Kotov/IdeaProjects/aws-related/setEnv.sh
POSTGRES_ENDPOINT=db-server.chruufbjovlu.us-east-1.rds.amazonaws.com
POSTGRES_USER=changeit
POSTGRES_PASSWORD=changeit

export POSTGRES_USER POSTGRES_PASSWORD POSTGRES_ENDPOINT
echo setup finished
