README for Java Watchdog Project
================================

=== 1. Introduction

This project contains the source code of the Java Watchdog Project.
The source code is hosted at: http://code.google.com/p/jwatchdog/


=== 2. Requirements

The following is required for compiling the project:
* Oracle Java 1.6.0+
* Apache Maven 3.1.1
* Android SDK with API version 4


=== 3. Build

You need to set the ANDROID_HOME environment variable to point to the Android
SDK directory. Example configuration:
	export ANDROID_HOME=$HOME/adt-bundle-linux-x86_64/sdk

Build the project via:
	mvn clean install


=== 4. Eclipse IDE

We use Eclipse Juno with the following plugins from the Eclipse Marketplace:
* Maven Integration for Eclipse (m2e) by Eclipse.org
* MercurialEclipse by MercurialEclipse Project
* Android Development Tools for Eclipse by Google, Inc.
* Android Configurator for M2E by Richardo Gladwell and Hugo Josefson


=== 5. License
The source code of the Java Watchdog Project is licensed under GNU LGPL v3.0.
The license conditions can be found in the file: LICENSE.txt
