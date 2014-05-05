obo-parser
==========

A java parser for obo file format as provided by unipathway


Installation
============

```bash
$ git clone https://github.com/institut-de-genomique/obo-parser.git
$ cd obo-parser
$ mvn install
$ mvn test
```

Usage
=====

To convert the "UPa:UPA00033" pathway to herbs format  with unix end line run this command.

````bash
$ java -jar oboToHerbsPathway.jar \
   --output result.clips          \
   --input unipathway.obo         \
   --term UPa:UPA00033            \
   --unix
````



LICENSE
=======

CeCILL-C