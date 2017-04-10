<img src="https://rawgit.com/Spirals-Team/powerapi/master/resources/logo/PowerAPI-logo.png" alt="Powerapi" width="300px">

[![Join the chat at https://gitter.im/Spirals-Team/powerapi](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/Spirals-Team/powerapi?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

[![OSS Licence](http://img.shields.io/badge/license-AGPLv3-532553.svg)](https://www.gnu.org/licenses/agpl-3.0.html)
[![Build Status](https://travis-ci.org/Spirals-Team/powerapi.svg)](https://travis-ci.org/Spirals-Team/powerapi)
[![Coverage Status](https://coveralls.io/repos/Spirals-Team/powerapi/badge.svg)](https://coveralls.io/r/Spirals-Team/powerapi)
[![Codacy Badge](https://www.codacy.com/project/badge/31f7762213c340fbb5fe1463a5b871d4)](https://www.codacy.com/app/mcolmant/powerapi)

PowerAPI is a middleware toolkit for building software-defined power meters.
Software-defined power meters are configurable software libraries that can estimate the power consumption of software in real-time.
PowerAPI supports the acquisition of raw metrics from a wide diversity of sensors (*eg.*, physical meters, processor interfaces, hardware counters, OS counters) and the delivery of power consumptions via different channels (including file system, network, web, graphical).
As a middleware toolkit, PowerAPI offers the capability of assembling power meters *«à la carte»* to accommodate user requirements.

# About

PowerAPI is an open-source project developed by the [Spirals research group](https://team.inria.fr/spirals) (University of Lille 1 and Inria) and fully managed with [sbt](http://www.scala-sbt.org).

The documentation is available [here](https://github.com/Spirals-Team/powerapi/wiki/Getting-started).

## Mailing list
You can follow the latest news and asks questions by subscribing to our <a href="mailto:sympa@inria.fr?subject=subscribe powerapi">mailing list</a>.

## Contributing
If you would like to contribute code you can do so through GitHub by forking the repository and sending a pull request.

When submitting code, please make every effort to follow existing conventions and style in order to keep the code as readable as possible.

## Publications
* **[Process-level Power Estimation in VM-based Systems](https://hal.inria.fr/hal-01130030)**: M. Colmant, M. Kurpicz, L. Huertas, R. Rouvoy, P. Felber, A. Sobe. *European Conference on Computer Systems* (EuroSys). April 2015, Bordeaux, France. pp.1-14.
* **[Monitoring Energy Hotspots in Software](https://hal.inria.fr/hal-01069142)**: A. Noureddine, R. Rouvoy, L. Seinturier. *Journal of Automated Software Engineering*, Springer, 2015, pp.1-42.
* **[Unit Testing of Energy Consumption of Software Libraries](https://hal.inria.fr/hal-00912613)**: A. Noureddine, R. Rouvoy, L. Seinturier. *International Symposium On Applied Computing* (SAC), March 2014, Gyeongju, South Korea. pp.1200-1205.
* **[Informatique : Des logiciels mis au vert](http://www.jinnove.com/Actualites/Informatique-des-logiciels-mis-au-vert)**: L. Seinturier, R. Rouvoy. *J'innove en Nord Pas de Calais*, [NFID](http://www.jinnove.com), 2013.
* **[PowerAPI: A Software Library to Monitor the Energy Consumed at the Process-Level](http://ercim-news.ercim.eu/en92/special/powerapi-a-software-library-to-monitor-the-energy-consumed-at-the-process-level)**: A. Bourdon, A. Noureddine, R. Rouvoy, L. Seinturier. *ERCIM News, Special Theme: Smart Energy Systems*, 92,  pp.43-44. [ERCIM](http://www.ercim.eu), 2013.
* **[Mesurer la consommation en énergie des logiciels avec précision](http://www.lifl.fr/digitalAssets/0/807_01info_130110_16_39.pdf)**: A. Bourdon, R. Rouvoy, L. Seinturier. *01 Business & Technologies*, 2013.
* **[A review of energy measurement approaches](https://hal.inria.fr/hal-00912996v2)**: A. Noureddine, R. Rouvoy, L. Seinturier. *ACM SIGOPS Operating Systems Review*, ACM, 2013, 47 (3), pp.42-49.
* **[Runtime Monitoring of Software Energy Hotspots](https://hal.inria.fr/hal-00715331)**: A. Noureddine, A. Bourdon, R. Rouvoy, L. Seinturier. *International Conference on Automated Software Engineering* (ASE), September 2012, Essen, Germany. pp.160-169.
* **[A Preliminary Study of the Impact of Software Engineering on GreenIT](https://hal.inria.fr/hal-00681560)**: A. Noureddine, A. Bourdon, R. Rouvoy, L. Seinturier. *International Workshop on Green and Sustainable Software* (GREENS), June 2012, Zurich, Switzerland. pp.21-27.

## Use Cases
PowerAPI is used in a variety of projects to address key challenges of GreenIT:
* [BitWatts](http://bitwatts.powerapi.org) provides process-level power estimation of applications running in virtual machines
* [Web Energy Archive](http://webenergyarchive.com) ranks popular websites based on the energy footpring they imposes to browsers
* [Greenspector](http://greenspector.com) optimises the power consumption of software by identifying potential energy leaks in the source code.

## Acknowledgments
We all stand on the shoulders of giants and get by with a little help from our friends. PowerAPI is written in [Scala](http://www.scala-lang.org) (version 2.11.7 under [3-clause BSD license](http://www.scala-lang.org/license.html)) and built on top of:
* [Akka](http://akka.io) (version 2.3.14 under [Apache 2 license](http://www.apache.org/licenses/LICENSE-2.0)), for asynchronous processing
* [Typesafe Config](https://github.com/typesafehub/config) (version 1.2.1 under [Apache 2 license](http://www.apache.org/licenses/LICENSE-2.0)), for reading configuration files.
* [Apache log4j2](http://logging.apache.org/log4j/2.x) (version 2.5 under [Apache 2 license](http://www.apache.org/licenses/LICENSE-2.0)), for logging outside actors.
* [powerspy.scala](https://github.com/Spirals-Team/powerspy.scala) (version 1.2 under [AGPL license](http://www.gnu.org/licenses/agpl-3.0.html)), for using the [PowerSpy powermeter](http://www.alciom.com/en/products/powerspy2-en-gb-2.html).
* [BridJ](https://code.google.com/p/bridj/) (version 0.7.0 under [3-clause BSD license](https://github.com/ochafik/nativelibs4java/blob/master/libraries/BridJ/LICENSE)), for system or C calls.
* [JNA](https://github.com/twall/jna) (version 4.2.1 under [LGPL 2.1 license](https://github.com/twall/jna/blob/master/LGPL2.1)), for system or C calls.
* [perfmon2](http://sourceforge.net/p/perfmon2/libpfm4/ci/master/tree) (version 4.6.0 under [MIT license](http://sourceforge.net/p/perfmon2/libpfm4/ci/master/tree/COPYING)), for accessing hardware performance counters.
* [JFreeChart](http://www.jfree.org/jfreechart/) (version 1.0.19 under [LGPL license](https://www.gnu.org/licenses/lgpl.html)), for creation of interactive and animated charts.
* [Scala IO](http://jesseeichar.github.io/scala-io-doc/0.4.3/index.html#!/overview) (version 0.4.3 under [3-clause BSD license](http://www.scala-lang.org/license.html)), for an extensions of IO.
* [Saddle](http://saddle.github.io/) (version 1.3.4 under [Apache 2 license](http://www.apache.org/licenses/LICENSE-2.0)), for data manipulation.
* [Sigar](https://support.hyperic.com/display/SIGAR/Home) (version 1.6.5 under [Apache 2 license](http://www.apache.org/licenses/LICENSE-2.0)), for providing a portable interface for gathering system information.
* [spray-json](http://spray.io/) (version 1.3.2 under [Apache 2 license](http://www.apache.org/licenses/LICENSE-2.0)), for (de)serializing JSON.
* [docker-java](https://github.com/docker-java/docker-java) (version 2.1.4 under [Apache 2 license](http://www.apache.org/licenses/LICENSE-2.0)), for using the JAVA Docker API.
* [influxdb-java](https://github.com/influxdata/influxdb-java) (version 2.1 under [MIT license](https://github.com/influxdata/influxdb-java/blob/master/LICENSE)), for using the JAVA InfluxDB API.

# License
This software is licensed under the *GNU Affero General Public License*, quoted below.

Copyright (C) 2011-2016 Inria, University of Lille 1.

PowerAPI is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

PowerAPI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with PowerAPI. If not, please consult http://www.gnu.org/licenses/agpl-3.0.html.
