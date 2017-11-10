build:
	gcc -o Gigel Gigel.c
	javac GigelExpertul.java

run-p1:
	./Gigel

run-p2:
	java GigelExpertul

clean:
	rm Gigel
	rm *.class
