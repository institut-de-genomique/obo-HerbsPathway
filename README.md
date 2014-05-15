obo-HerbsPathway
==========

A java converter unipathway to Herbs


Release
=======

- [obo-parser-0.1.0](https://github.com/institut-de-genomique/obo-parser/archive/obo-HerbsPathway-0.1.0.zip)

Download
========

- Users: get current [stable release](https://github.com/institut-de-genomique/obo-HerbsPathway/archive/v0.1.0.zip)
- Developers: get it from git


```bash
$ git clone https://github.com/institut-de-genomique/obo-HerbsPathway.git
````

Installation
============

```bash
$ cd obo-HerbsPathway
$ mvn install
```

Usage
=====

To convert the "UPa:UPA00033" pathway to herbs format  with unix end line run this command.

````bash
$ java -jar oboToHerbsPathway.jar    \
   --output lysine_biosynthesis.data \
   --input unipathway.obo            \
   --term UPa:UPA00033               \
   --unix
````


Build Information
=================

Travis Log: [here](https://travis-ci.org/institut-de-genomique/obo-HerbsPathway) (Not ready)

LICENSE
=======

CeCILL-C