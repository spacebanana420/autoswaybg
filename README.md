## autobg
autobg is a command-line program for automatically changing and saving the wallpaper on Wayfire using `swaybg`.

The process of changing the current wallpaper is very manual on swaybg: it requires that you kill the current swaybg process, spawn a new one with the new file then disown the process on your shell and exit.
On window managers like Wayfire, Labwc, Hyprland, etc, you then also have to update their respective config file and change the swaybg command that runs on start to the new one.
This program aims to automate all of that.

## Requirements
* Java 11 or newer
* swaybg
* wayfire

## Download

You can download autobg from the [releases page](https://github.com/spacebanana420/autobg/releases).

You can run `java -jar autobg.jar` and open the help screen to see what you can do.

### Install on your system (using [Yuuka](https://github.com/spacebanana420/yuuka))
```
yuuka install autobg.jar
```
