fatih-parser
============

A Generic Syntactic Parser for Turkish and other Turkic Languages. 

A demo can be seen [here](http://fatihparser.herokuapp.com/).

###Maven Usage

Add the following repo to your pom.xml

    <repository>
       <id>hrzafer-releases-repo</id>
       <url>https://github.com/hrzafer/mvn-repo/raw/master/releases</url>
    </repository>

Add fatih-parser depencency:

	<dependency>
      <groupId>com.hrzafer</groupId>
      <artifactId>fatih-parser</artifactId>
      <version>1.0</version>
    </dependency>

Add other necessary depencencies:

	<dependency>
      <groupId>edu.osu.ling.pep</groupId>
      <artifactId>pep</artifactId>
      <version>0.4</version>
    </dependency>
    <dependency>
      <groupId>net.zemberek</groupId>
      <artifactId>zemberek-cekirdek</artifactId>
      <version>2.1.1</version>
    </dependency>
    <dependency>
      <groupId>net.zemberek</groupId>
      <artifactId>zemberek-tr</artifactId>
      <version>2.1.1</version>
    </dependency>
    <dependency>
      <groupId>net.zemberek</groupId>
      <artifactId>zemberek-tk</artifactId>
      <version>2.1.1</version>
    </dependency>
	<dependency>
      <groupId>org.apache.commons</groupId>
      <artifactId>commons-lang3</artifactId>
      <version>3.3.2</version>
    </dependency>