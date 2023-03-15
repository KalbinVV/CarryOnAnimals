# CarryOnAnimals
Spigot plugin, that allow player to move entity on head.

Link: https://www.spigotmc.org/resources/carryonanimals-1-16-api-towny-worldguard-support.107165/

Adds the ability to move animals (or another entities if you enabled it) on the head.

# Screenshot:
https://imgur.com/a/NUJsSdJ

# How it works?

To pick up an animal, you must crouch and right-click.

To remove an animal from its head, you must crouch and stand.

# Considers the protection of the following plugins:

Towny (0.98.4.0 or new)

WorldGuard (7.0.0 or new) (Experimental)

I don't know how regions work in WorldGuard to the end and therefore I couldn't fully check the functionality of the protection, if you find an error - let me know.

# How does Towny protection work?

Only players who are in the city where these animals are located can move animals. Other options may appear in the future.

# How does WorldGuard protection work?

You can only move animals in a region where the "carry-on-animals" flag is set.

# Permissions:

The plugin has three permissions:

carryonanimals.default - default permissions allow animals to be moved.

carryonanimals.bypass - ignore all protections (Towny, WorldGuard)

carryonanimals.admin - to use protections and reload command.

# Command

This plugin have command /carryonanimals (or just /coa):

help - show help message.

protections - show list of enabled protections.

reload - reload plugin.

#Particles and sounds

Plugin has particles and sounds support, you can configure this in config.yml file.

# API usage:


The API currently has two events that can be tracked:

## EntityPickUpEvent

This event is called when player try to pickup entity.

Methods:

getPlayer() -> Player

getEntity() -> Entity

setCancelled() -> void

isCancelled() -> boolean

## EntityDropEvent

This event is called when player try to drop entity.

Methods:

getPlayer() -> Player

setCancelled() -> void

isCancelled() -> boolean

## API manager:

Methods:

registerProtection(Protection protection) -> void

Allow to register your own protection, every protection should be implemented by Protection interface and override methods:

check(Player player, Entity entity) -> bool

This methods is called when event check all protections before allow player to pickup entity, if you return true - event was cancelled.

getMessage() -> String

Message that was returned if check method return false.

getName() -> String

Name of protections, it will show up in the list when admin use command "coa protections".

This documentations will updated in future along with the API.
