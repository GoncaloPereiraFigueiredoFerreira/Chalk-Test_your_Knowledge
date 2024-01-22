#!/bin/bash

# Get the host machine's IP address
HOST_IP=$(hostname -I | awk '{print $1}')

# Set the IP address in the .env file
sed -i "s/LOCALHOST = .*/LOCALHOST = $HOST_IP/" ".env"