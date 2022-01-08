#!/usr/bin/env bash

ping -c 1 192.168.1.6 &>/dev/null && echo "UP" || echo "DOWN"