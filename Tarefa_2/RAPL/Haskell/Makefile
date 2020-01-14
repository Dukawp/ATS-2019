fib-rapl: F.hs fib-rapl.c
	ghc -c -O F.hs
	ghc --make -no-hs-main -optc-O fib-rapl.c F -o fib-rapl 
	chmod 777 fib-rapl

clean:
	rm *.o *.hi prog
