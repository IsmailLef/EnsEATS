EXCLUDED_FILES = src/deprecated/insertDB.java
FILES_TO_COMPILE := $(filter-out $(EXCLUDED_FILES), $(wildcard src/*/*.java))

all: compile

# Compilation
compile:
	javac -d bin -classpath bin:lib/ojdbc6.jar -sourcepath src $(FILES_TO_COMPILE)

# Exécutions
test: compile
	java -classpath bin:lib/ojdbc6.jar gui.Main

testDB: compile
	java -classpath bin:lib/ojdbc6.jar tests.testDB

testInit: compile
	java -classpath bin:lib/ojdbc6.jar tests.testInit

clean:
	rm -rf bin/*
	rm -rf src/*.class