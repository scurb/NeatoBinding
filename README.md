# Neato Binding

This binding is used to connect your openHAB 2 system with Neato web (where you log in and find Your Neato's). The binding supports discovery if configured with login and password. From the binding, you will get status of your vacuum cleaners and also a command channel where you can control them. Since the binding uses a polling mechanism, there may be some latency depending on your setting regarding refresh time. 

For log in transaction, the binding uses Neato Beehive API and for status and control, the binding uses Nucleao API. 


## Supported Things

Vacuum cleaner is the only support thing of this binding. As of todays date, it is only verified with Neato Connected vacuum cleaner.


## Discovery

Discovery is used _after_ the binding is configured with your log in information in Bindings Configuration (Paper UI). The step would be:

1. Add the binding
2. Configure the binding in Paper UI setting login information towards Neato Web.
3. Go to Inbox and start discovery of Vacuums using Neato Binding
4. Vacuums should appear in your inbox!


## Binding Configuration

In Paper UI under Bindings you may configure the binding with login information (e-mail and password) as well as refresh time. Default refresh time is set to 60 seconds.


## Thing Configuration

In order to manually create a thing file and not use the discovery routine you will need to know the vacuums serial number as well as the secret used in web service calls. This is a bit difficult to get. The easiest way of getting this information is to use the third party python library that is available at https://github.com/stianaske/pybotvac.

## Channels

| Channel     | Type    | Label | Description | Read Only |
| --------|---------|-------|-------|-------|
| battery-level | Number | Battery Level | Battery Level of the vacuum cleaner. | True |
| state | String | Current State | Current state of the vacuum cleaner. | True |
| available-services | String | Current available services | List of services that are currently available for the vacuum cleaner | True |
| version | String | Version | Version of the vacuum cleaner. | True |
| model-name | String | Model Name | Model Name of the vacuum cleaner. | True |
| firmware | String | Firmware | Firmware version of the vacuum cleaner. | True |
| action | String | Current Action | Current action of the vacuum cleaner. | True |
| dock-has-been-seen | Switch | Dock has been seen | True or False value if the dock has been seen | True |
| is-docked | Switch | Is docked | Is the vacuum cleaner in the docking station? | True |
| is-scheduled | Switch | Is scheduled enabled | True or False value if the vacuum cleaner is scheduled for cleaning. | True |    
| is-charging | Switch | Is Charging | Is the vacuum cleaner currently charging? | True |
| available-commands | String | Available Commands | List of available commands. | True |
| error | String | Error | Current error message in system. | True |
| command | String | Send Command | Send Commands to Vacuum Cleaner. (clean, pause, resume, stop, dock) | False |
| name | String | Vacuum Cleaner Name | Name of the vacuum cleaner represented by this Thing. | True |
| cleaning-category | String | Cleaning Category | Current or Last category of the cleaning. Manual, Normal House Cleaning or Spot Cleaning. | True |
| cleaning-mode | String | Cleaning Mode | Current or Last cleaning mode. Eco or Turbo. | True |
| cleaning-modifier | String | Cleaning Modifier | Modifier of current or last cleaning. Normal or Double. | True |
| cleaning-spotwidth | Number | Spot Width | Current or Last cleaning, width of spot. 100-400cm. | True |
| cleaning-spotheight | Number | Spot Height | Current or Last cleaning, height of spot. 100-400cm. | True |


## Full Example

Below you will find examples of the necessary files:

**neato.items**

    Group GNeato
    String FannDammName  "Name [%s]" (GNeato) 
    Number FannDammBattery  "Battery level [%.0f %%]" <battery> (GNeato) 
    String FannDammState  "Status [MAP(neato-sv.map):%s]" (GNeato) 
    String FannDammError  "Error [%s]" (GNeato) 
    String FannDammVersion  "Version [%s]" (GNeato) 
    String FannDammModel  "Model [%s]" (GNeato) 
    String FannDammFirmware  "Firmware [%s]" (GNeato) 
    String FannDammAction  "Action [MAP(neato-sv.map):%s]" (GNeato) 
    Switch FannDammDockHasBeenSeen  "Seen dock [%s]" <present> (GNeato) 
    Switch FannDammIsDocked  "In dock [MAP(neato-sv.map):%s]" <present> (GNeato) 
    Switch FannDammIsScheduled  "Scheduled [%s]" (GNeato) 
    Switch FannDammIsCharging  "Is Charging [%s]" <heating> (GNeato) 
    String FannDammCategory  "Cleaning Category [MAP(neato-sv.map):%s]" (GNeato) 
    String FannDammMode  "Cleaning Mode [MAP(neato-sv.map):%s]" (GNeato) 
    String FannDammModifier  "Cleaning Modifier [MAP(neato-sv.map):%s]" (GNeato) 
    Number FannDammSpotWidth  "SpotWidth [%.0f]" <niveau> (GNeato) 
    Number FannDammSpotHeight  "SpotHeight [%.0f]" <niveau> (GNeato) 
    String FannDammCommand  "Send Command"

**sitemap**

    Frame label="Neato BotVac Connected" {
	    Switch item=FannDammCommand mappings=[clean="Clean",stop="Stop",pause="Pause",resume="Resume", dock="Send to dock"]
	    Text item=FannDammBattery label="Battery level"
	    Text item=FannDammState
	    Text item=FannDammError label="Error Message" icon="siren"
	    Text item=FannDammAction label="Activity"
	    Text item=FannDammIsDocked label="In dock"
	    Group label="Mer information" item=GNeato
    }


**neato.things**

    neato:vacuumcleaner:fanndamm [ serial="vacuumcleaner-serial", secret="secret-string", name="Fann Damm"]



## Todo

The next thing to implement is the support of maps!
