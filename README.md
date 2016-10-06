# Draconic-Launcher
The open-source Minecraft modpack launcher!

Currently early on in development, this project is a continuation of my over-done Python script that functioned as a custom Minecraft launcher. I created it so that modding the environment could be easier and more convenient. This program aims to support it's own modpacks, as well as those from other popular launchers. It will then create an manages multiple instances of the game for efficient creation of custom modpacks.

**TODO:**
_(Lots to put here, but let's get the basics down!)_
- Complete the main program
	- Login System
	- Game Launch
	- Launch Configuration
	- Instance-based management...stuff
- Create an interactive and visually-pleasing UI
	- Includes features such as the game output and launch configuration
	- Cool-looking logged-in visual (with the player model and everything)
- Create a wiki on here

(Feature additions, will be created on separate branches)
- Feature: Highly-Expandable modpack format and system
	- Version JSON appending/override
	- Jarfile override
	- Library override
	- Mod categories and launcher option to enable/disable a category of mods (launcher would recognize the created categories)
	- Special file generation to allow for light-weight, cross-user modpack sharing
	- Local modpack creation system (where other features apply; )
	- GUI-based modpack creator utility
	- Patch support
- Feature: Profiles
	- Improvement of default profiles*:
		- *Player profiles - modpack defaults for who is logged in
		- *Skin swap options
		- Launch config settings per profile (note: settings will save defaults, configured in menu)
		- Easy login
		- Other cool stuff? I dunno guys! Help me out here!
- Feature: FTB, Tekkit, ATLauncher modpack support
	- Make this launcher universal (those modpacks can be dragged somewhere, then automatically imported, where a _DraconicLauncher_ configuration file for it is generated)
	- Also potentially support directly downloading the other modpacks directly
- Feature: Server management portal
	- Allows for locally hosting a server (modpacks will also need a server version of the config)
	- Multicraft access portal (if possible)
		- Local client to manage multicraft servers
		- Built-in FTP support
- Feature: listing of mojang servers (using the mojang API)
