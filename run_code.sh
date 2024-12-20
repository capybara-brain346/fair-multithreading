#!/usr/bin/bash

if [ $# -eq 0 ]; then
    echo "Usage: $0 <MainClassName> [JavaFile1.java JavaFile2.java ...]"
    echo "Example: $0 MainClassName MyClass1.java MyClass2.java"
    exit 1
fi

MAIN_CLASS=$1

if [ $# -eq 0 ]; then
    echo "Compiling all Java files in the current directory..."
    javac *.java
else
    echo "Compiling specified Java files..."
    javac "$@"
fi

if [ $? -ne 0 ]; then
    echo "Compilation failed. Please fix the errors and try again."
    exit 1
fi

echo "Running $MAIN_CLASS..."
java "$MAIN_CLASS"

