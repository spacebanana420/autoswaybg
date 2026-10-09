# autoswaybg
`autoswaybg` is a command-line program for automatically changing and saving the wallpaper on Wayfire using swaybg.

The process of changing the current wallpaper is very manual on swaybg: it requires that you kill the current swaybg process, spawn a new one with the new file then disown the process on your shell and exit.
On window managers like Wayfire, Labwc, Hyprland, etc, you then also have to update their respective config file and change the swaybg command that runs on start to the new one.
This program aims to automate all of that.

**This project is brand new and a work-in-progress, it's not usable yet**
