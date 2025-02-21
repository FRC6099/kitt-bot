# Kitt Bot
This is team 6099 Knight Riders robot. This will be updated each year.

## Tagging
We'll follow the following pattern for tagging: `yyyy.minor.patch`

For updates, please tag with command similar to:

```bash
git tag vYYYY.0.0 -a -m "YYYY changes"
git push --tags
```

Replace `YYYY` with the current year. Maintain the `YYYY` throughout the whole season.
When pushing changes to the **main** branch, please create a new tag with either the `patch`
or `minor` version number incremented. Increment the `minor` version when there are drastic
changes present and reset `patch` version number back to zero (0).


## Upgrades
For the new year, to upgrade, please follow the prompts in the WPI Lib Project Importer tool.
Note that the tool will make a new directory in the parent folder. Set the project name to 
**kitt-bot-tmp** and open project in new window.

After that, please run the bash command below in the *kitt-bot-tmp* folder.

```bash
cp -r . ../kitt-bot
```

This will copy the new year's files back into the original project. Feel free to compare the 
differences using below command in the *kitt-bot* folder.

```bash
git diff
```

Close or delete the tmp project. From here, execute and resolve any errors until the build passes.

```bash
./gradlew clean build
```

### Vendordeps errors
You may see an error like this:

```bash
An exception occurred applying plugin request [id: 'edu.wpi.first.GradleRIO', version: '2025.2.1']
> Failed to apply plugin class 'edu.wpi.first.gradlerio.wpi.WPIPlugin'.
   > Could not create an instance of type edu.wpi.first.gradlerio.wpi.WPIExtension.
      > Vendor Dependency REVLib has invalid year 2024. Expected to be 2025. Reach out to the vendor to get a new version of the dependency. Attempting to modify an existing dependency will break at runtime, and will result in loss of support from the WPILib team.
```

Please update to the latest year. To quickly get the new year's version, find the `jsonUrl` in the 
*vendordeps/MY_DEP.json* and copy it into the browser. Next change the year to match current year.
Lastly, copy the contents of the JSON file and overwrite the one in *vendordeps*.

### Compile Time Erros
You may see an error like `error: cannot find symbol`. This means that the class either no
longer exists or it has been moved. To resolve, try to find the correct package and class
name by using the IDE. Or visit the vendor's site to find what has changed.