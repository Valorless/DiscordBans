# DiscordBans

DB catches the following commands and sends it to discord via webhook.
- /ban (Minecraft and Essentials)
- /tempban (Essentials)
- /banip (Essentials)
- /unban (Essentials)
- /unban-ip (Essentials)
- /pardon (Minecraft)
- /pardon-ip (Minecraft)

Once a command is caught, information regarding the target, sender, reason and more, is sent to the webhook.

*Currently DiscordBans does not catch commands cast by the console.*

## Commands

- /db reload
  - Reloads config.yml
  
## Permissions

- discordbans.*
  - Gives all DiscordBans permissions.
- discordbans.reload
  - Allows usage of /db reload.

## Messages

Within /plugins/DiscordBans/config.yml you'll find a 'message' section. You can customize the messages to anything you want, with or without provided placeholders:
- ***%target%*** 
  - *(Target of the command)*
- ***%sender%*** 
  - *(Sender of the command)*
- ***%reason%*** 
  - *(Reason for the ban)*
- ***%duration%*** 
  - *(Duration of the tempban)*
- ***%date%*** 
  - *(Server time when the ban occured)*
- ***%plugin%*** 
  - *(Will always return '&7[&4DiscordBans&9]&r')*

## Configuration

- webhook-url: ''
  - Webhook URL found in the integrations section of a channel's settings.
    - (If left blank, the plugin will disable itself until it has been set)
- bot-name: 'George'
  - Name of the Webhook sending the message.
    - (Leave blank to use the one specified in the integration section)
- bot-picture: 'https://i.pinimg.com/originals/bf/23/ca/bf23ca87c2a867e2b3b991e76d982abd.jpg'
  - Profile Picture of the Webhook. Must be a URL.
    - (Leave blank to use the one specified in the integration section)

- ban-color: '#ff2b2b' # Red
  - Color of the embed when a player is banned.
    - (Default: '#ff992b')
- tempban-color: '#ff992b' # Orange
  - Color of the embed when a player is temp banned.
    - (Default: '#ff992b')
- unban-color: '#2afa4d' # Green
  - Color of the embed when a player is unbanned.
    - (Default: '#2afa4d'))
- banip-color: '#5b09ad' # Purple
  - Color of the embed when a player is ip banned.
    - (Default: '#5b09ad'))
- unbanip-color: '#0ce6fa' # Light Blue
  - Color of the embed when a player is ip unbanned.
    - (Default: '#0ce6fa'))

- bans: true
  - Whether the plugin should send bans to the discord server or not.
    - (Default: true)
- tempbans: true
  - Whether the plugin should send bans to the discord server or not.
    - (Default: true)
- unbans: true
  - Whether the plugin should send bans to the discord server or not.
    - (Default: true)
- banips: true
  - Whether the plugin should send bans to the discord server or not.
    - (Default: true)
- unbanips: true
  - Whether the plugin should send bans to the discord server or not.
    - (Default: true)

- debug: false
  - Enabling 'debug' will make the plugin send additional messages in console.
    - (Default: false)
