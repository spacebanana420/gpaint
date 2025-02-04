# GPaint

GPaint is a theme editor for [Geany themes](https://www.geany.org/download/themes/). It allows you to adjust and manipulate the colors of a theme with contrast, saturation, temperature and more. GPaint also supports Xfce Terminal themes and all theme files that are similar to Geany themes and Xfce Terminal themes.

## Using GPaint and Requirements

### Requirements
* Java 11 or newer

### Download and run

Grab the latest release of Geany from the [releases page](https://github.com/spacebanana420/gpaint/releases) and run `java -jar gpaint.jar -h` to see what you can do.

You do not require to pass the absolute or local path of Geany files, since GPaint knows the filesystem location for Geany colorschemes.

## Build from source

GPaint uses the [Yuuka build tool](https://github.com/spacebanana420/yuuka).

You can build an executable JAR by running `yuuka package`.

### Install GPaint (Unix-like systems)

Run **as root**: `yuuka install`.

If you are on FreeBSD or you have `~/.local/bin` in your $PATH, you do not have to run Yuuka as root to install GPaint.
