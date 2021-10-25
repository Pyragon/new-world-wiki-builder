
# New World Wiki Builder

Attempts to build pages for WikiJS based around assets dumped from the New World Cache.

## How

This information may change as Amazon makes clear what information they are okay with, and what information they are not.

Currently this builder is used for:

* Decompiling lua scripts
* Decompiling datasheets
* Converting DDS images to PNG
* Building item definition pages

### Decompiling LUA

Decompiling the lua scripts is very easy.
There are two bytes at the beginning of the file (0x04 and 0x00) used for compatibility with Lumberyard's engine. Removing these two bytes allows the file to be decompiled using any mainstream lua decompilers. I personally use [unluac](https://github.com/HansWessels/unluac) in this project.

### Decompiling datasheets

The datasheets are decompiled using the method found [here](https://gist.github.com/Kattoor/50155a209fae4b19281f219def622b27). This method is not my own, all credits to the creator.

### Converting DDS

This project will convert all dds and dds.# files into PNGs. This is a very lengthy process. Will in the future add a way to skip these.

### Building Item Definition Pages

The project will atempt to build some wikipedia pages based on the information that can be obtained from the above dumped files.
Currently, we build only Item Definition pages, and are working on getting the styling a bit better.
These pages include relevent information on the item, including recipes that it can be used in, and stats for the item.

## TODO

* Extract and dump models
* Create way to update existing wiki pages if they have not been edited by users yet.
* Flag updated wiki pages for manual review.
