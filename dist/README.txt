A Compiler for FGJ-omega
------------------------

This archive contains the implementation of a prototype compiler for
the FGJ-omega programming language, an extension of Featherweight
Generic Java with variables representing type constructors. The
language is based on the calculus described in the paper "Adding Type
Constructor Parameterization to Java" by Philippe Altherr and Vincent
Cremet (submitted to FTfJP'07).

1) The archive is structured as follows:

- README.txt      : the present file
- SYNTAX-Java.txt : description of the Java-like syntax of FGJ-omega programs
- SYNTAX-Scala.txt: description of the Scala-like syntax of FGJ-omega programs
- bin/            : contains the script for launching the compiler
- examples/       : contains some code examples
- lib/            : contains the compiler as a jar file.

2) Launching the compiler

The compiler comes as a set of Java classfiles archived in a jar file.
Before launching the compiler, check that you have the version 1.5 (or
superior) of the Java runtime environment installed on your machine.

The compiler can be launched on the command line using the script
file, called hofgj, located in the directory bin/.

Type

  hofgj <file1> ...

for compiling a program composed of one or several input files. The
compiler accepts .java files in a Java-like syntax and .scala files in
a Scala-like syntax. A FGJ-omega program is composed of a list of
class definitions potentially split into several files that can
mutually depend on each other. Additionally, each file can be
optionally ended with a final statement (Java) or value expression
(Scala). See the SYNTAX files for a formal description of the syntax
understood by the compiler (in EBNF notation).

Once invoked with some input files, the compiler performs two tasks:
first, it analyzes the program and checks that it is well-typed, and
then it evaluates the final expressions of every file in order.

Example:

  ./bin/hofgj examples/Predef.java examples/OOMonads.java

3) Contact

If you encounter any problem, send a mail to Philippe Altherr at
philippe.altherr@gmail.com.

Lausanne, May 2007.
