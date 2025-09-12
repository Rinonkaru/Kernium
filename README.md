# Kernium
Kernium is a Bukkit/Spigot/Paper plugin that adds functionalities I like to have to the servers I create. Right now it only possesses `/warp`, `/setwarp`, `/delwarp`, and `/pubwarp`. Further explainations will be provided below for what these commands do and how to use them.

## Note: 
All of the commands listed below have autocomplete enabled using the `BasicCommand` suggestion method. You do not need to remember the names of the locations you've set, the plugin will remember for you.

Configurations will be coming in the future. As of now, anyone can remove public warps.

## Warning:
This plugin is in its early stages of development, keep in mind that as of right now, there is no limit on how many private or public warp locations can be set, I will be adding a limit in the future but please be careful not to cause an `OutOfMemory` error due to excessive warp creations. **I am not responsible for any world corruption or server failures due to this issue.**

## Commands

### Warp Command
- Usage: `/warp <designation>`
- This command, along with the locations you set up, will allow you to travel between designated locations instantly.
- _**Warning**: **Do not use spaces for location names. Only the first word will be used at the moment!**_

### Set Warp Command
- Usage: `/setwarp <designation>`
- This command allows you to create a new private warp location for you. You simply choose a good name and the command will set that warp location to be exactly where you are in game, including which way you're turned and what way you're looking (Yaw and Pitch).

### Delete Warp Command
- Usage: `/delwarp <designation>`
- This command allows you to delete one of the warp locations you have created when you don't need it anymore. 

### Public Warp Command
- Usage `/pubwarp [Optional add | remove] <designation>`
- This command deals with all publicly accessible warps. 
- Using the command with no operation argument will simply warp the user to the public warp location: e.g. `/pubwarp <designation>`.
- Using the command with the `add` operation will allow you to create a new public warp location: e.g. `/pubwarp add <designation>`.
- Using the command with the `remove` operation will allow you to renice a public warp location: e.g. `/pubwarp remove <designation>`.
