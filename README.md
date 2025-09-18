# Kernium
Kernium is a Bukkit/Spigot/Paper plugin that adds functionalities I like to have to the servers I create. Right now it only possesses `/warp`, `/setwarp`, `/delwarp`, and `/pubwarp`. Further explainations will be provided below for what these commands do and how to use them. 

If you have any bugs, or features you'd like to request, please feel free to open an issue regarding it. I will be monitoring daily.

## Note: 
All of the commands listed below have autocomplete enabled using the `BasicCommand` suggestion method. You do not need to remember the names of the locations you've set, the plugin will remember for you.

Configurations will be coming in the future. As of now, anyone can remove public warps.

## Warning:
I have fixed the `OutOFMemory` error being easy to reach by limiting warp creation but an extreme amount of players can still cause that to happen (though incredibly difficult to reach).

## Commands

### Warp Command
- Usage: `/warp <designation>`
- This command, along with the locations you set up, will allow you to travel between designated locations instantly.
- _**Warning**: **Do not use spaces for location names. Only the first word will be used at the moment!**_

### Set Warp Command
- Usage: `/setwarp <designation>`
- This command allows you to create a new private warp location for you. You simply choose a good name and the command will set that warp location to be exactly where you are in game, including which way you're turned and what way you're looking (Yaw and Pitch).
- A hard limit of 5 personal warps has been imposed, though this can be changed in the code.

### Delete Warp Command
- Usage: `/delwarp <designation>`
- This command allows you to delete one of the warp locations you have created when you don't need it anymore. 

### Public Warp Command
- Usage `/pubwarp [Optional add | remove] <designation>`
- This command deals with all publicly accessible warps. 
- Using the command with no operation argument will simply warp the user to the public warp location: e.g. `/pubwarp <designation>`.
- Using the command with the `add` operation will allow you to create a new public warp location: e.g. `/pubwarp add <designation>`.
- Using the command with the `remove` operation will allow you to renice a public warp location: e.g. `/pubwarp remove <designation>`.
- A hard limit of 3 personal warps has been imposed, though this can be changed in the code.
