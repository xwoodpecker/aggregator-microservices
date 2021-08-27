#!/bin/bash
# Grabs and kill a process from the pid list that has the word myapp
pid=$(ps aux | grep sensors | awk '{print $2}')
kill -9 "$pid"