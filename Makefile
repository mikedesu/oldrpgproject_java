CC=javac
JAR=jar
RM=rm -rfv
ZIP=zip -r -9

all:
	$(CC) -d bin -sourcepath src src/GameApplet.java
	$(JAR) cvfm bin/GameApplet.jar manifest.mf -C bin .
	$(ZIP) rpgsandbox.zip bin/

clean:
	$(RM) bin/*.class bin/GameApplet.jar rpgsandbox.zip

