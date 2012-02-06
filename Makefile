CC=javac
JAR=jar
RM=rm -rfv

all:
	$(CC) -d bin -sourcepath src src/GameApplet.java
	$(JAR) cvfm bin/GameApplet.jar manifest.mf -C bin .

clean:
	$(RM) bin/*.class GameApplet.jar

