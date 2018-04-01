<<<<<<< HEAD
# Solar Measure
---
<p>
Solar Measure is a centralized system to aid the redirection of the city power grid to those most in need.
</p>

## Abstract
---
<p>
The main issue with solar power is that it is never a consistent source of power.
It is dependent on multiple factors, the major player being the local weather.
Solar Measure aims to assist the city buildings which have solar cells powering
them by redirecting the power produced by the city to the houses which are not
able to maintain a steady stream of power generation to remain self sufficient.
This encourages more people to switch over to Solar Power, while maintaining
Solar Power as a steady source of power with some assistance from the city's
power grid.
</p>

## System Architecture
---
<p>
The system as a whole revolves around a centralized server which intelligently
decides which user to route the excess power generated to. There are 3 main
components to the system- a centralized server, an application for the user and
a sensor.</br>
[System Architecture](architectureImg.jpg)
</p>

### Sensor-
---
<p>
The Sensor is the main hardware which collects the raw data. The sensor collects
data which indicates the rate at which the reserve battery is being charged,
discharged, the amount of power reserved in the backup battery. This data is then
sent to the server upon which the server performs various functions which then
govern the redirection of the power to the specific node. This sensor also sends
the same data to the user application so the user can keep a track of the
information sent to the server. As of now, since the sensor cannot be implemented,
the sensor is emulated by the user application.
</p>

### Server-
---
<p>
The server receives the data from the sensor and performs various operations
to indicate which building to redirect power to, and how much. More can be found
in the README in the server folder.
</p>

### Application-
---
<p>
The application is a user friendly android application which provides an interface
to monitor the power connections and charging and discharging of the reserve battery.
This enables the user to verify the data being sent to the server, thus maintaining
a level of transparency, and also provides the user with data the user can use to
maximize his/her power usage.
</p>

## Future plans
---

<p>
1. Implement the sensor functionalities through a microcontroller.</br>
2. Making the graphs easier to use, by adding filters and scaling functionalities.</br>
3. Adding more detailed information to provide the users with more comprehensive
and categorized information regarding socket-wise power consumption.</br>
4. Integrating the system and building on an existing power routing system to
increase efficiency of power supply and generation.</br>
</p>
=======
# Hackncode
T01
>>>>>>> 80a555e2a10d3880a86a8b60002523e9618e7d58
