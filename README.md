# MasaAdditions
An add-on mod for Masa's mods, Tweakeroo, MiniHUD, and Litematica.

## Features
 - [TweakerooAdditions](https://github.com/hp3721/masaadditions/wiki/TweakerooAdditions)
 - [MiniHUDAdditions](https://github.com/hp3721/masaadditions/wiki/MiniHUDAdditions)
 - [LitematicaAdditions](https://github.com/hp3721/masaadditions/wiki/LitematicaAdditions)

## Installation
1. Download and run the [Fabric installer](https://fabricmc.net/use).
   - Note: this step may vary if you aren't using the vanilla launcher.
1. Download the [Fabric API](https://minecraft.curseforge.com/projects/fabric) and move it to the mods folder (`.minecraft/mods`).
1. Download MasaAdditions from the [releases page](https://github.com/hp3721/masaadditions/releases) and move it to the mods folder (`.minecraft/mods`).

## Contributing
1. Clone the repository
   ```
   git clone https://github.com/hp3721/masaadditions.git
   cd masaadditions
   ```
1. Generate the Minecraft source code
   ```
   ./gradlew genSources
   ```
   - Note: on Windows, use `gradlew` rather than `./gradlew`.
1. Import the project into your preferred IDE.
   1. If you use IntelliJ (the preferred option), you can simply import the project as a Gradle project.
   1. If you use Eclipse, you need to `./gradlew eclipse` before importing the project as an Eclipse project.
1. Edit the code
1. After testing in the IDE, build a JAR to test whether it works outside the IDE too
   ```
   ./gradlew build
   ```
   The mod JAR may be found in the `build/libs` directory
1. [Create a pull request](https://help.github.com/en/articles/creating-a-pull-request) so that your changes can be integrated into MasaAdditions
   - Note: for large contributions, create an issue before doing all that work, to ask whether your pull request is likely to be accepted

## License
This project is licensed under [GPL-3.0](https://github.com/hp3721/masaadditions/LICENSE). Some portions of code are liberally adapted from [UsefulMod](https://github.com/Nessiesson/UsefulMod) and [CutelessMod](https://github.com/Nessiesson/CutelessMod) by [nessie](https://github.com/Nessiesson) which is licensed under the [MIT License](https://github.com/hp3721/masaadditions/blob/master/LICENSE_MIT).
