Visual Testing in Katalon Studio
======

author: kazurayam

## Overview of this project

This is a [Katalon Studio](https://www.katalon.com/) project for demonstration purpose. You can clone this
out to your PC and run it with your Katalon Studio.

This project was initially developed using Katalon Studio 5.7.0, and has proved to work with the higher versions.

This project demonstrates how to perform screenshot-comparison testings in Katalon Studio.

## Motivation

- Often I have to look at the pages of a Web site. I may be a developer, a designer, a tester or just a fan of the site.
- I want to look at as many pages of the site as possible. Wide coverage matters.
- I want to compare 2 sets of screenshots of a pair of environments of my Application Under Test (AUT). I call this approach **Twins test**. E.g, comparing the Development environment to  the Production envirionement.
- Also I want to compare the current view of my AUT to the one chronologically previous. I call this approach  **Chronos test**. E.g, before and after any sort of changes of the Production system (application software upgrades, applying OS patches, changing of data in Database, network devices reconfiguration, etc).
- I want to take full-page screen shots of as many pages as possible.
- After taking screenshots, I want to check them and find out if there are any visual  differences between the two.
- If not significant difference found, OK, I am relieved and can go home. If any significant visual difference found, whatever the cause can be, I would fix it immediately.

If my AUT has 100 pages and if I do it manually, it would take me more than 1 hour to go through a Twins test. It's too tiring, too boring. I don't like doing the job everyday! Therefore I would add the final term:

- I want to automate it.

And let be add some more terms:

- I would not require the tool to be equipped with full fledged debugging functionalities. All I want is to be notified of indication of possible faults.
- Preferably the tool should be free of charge.

## Technical issues

Preliminary study revealed 2 technical problems.

1. I wanted to make tens or hundreds of screenshot files on local disk of my PC and reuse them. One scripts writes a screenshot in that path, another scripts reads the file form that path. Both scripts should share the common path format and respect it. So I had to define how the file paths format should be and develop a library in Groovy or Java.
2. Katalon Studio has a built-in Keyword to take screenshot:  [org.openqa.selenium.TakesScreenshot#getScreenshotAs(output)](https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/TakesScreenshot.html). But the image taken by this is not full-page size. I needed alternative.

## Solutions

This project demonstrates how to take multiple sets of web page screen shots and store them into a well-structured file system tree. This demo project uses another GitHub repository:
- [`Materials`](https://github.com/kazurayam/Materials)

As for the full-page screenshot problem, I found that the library:

- [`aShot`](https://github.com/yandex-qatools/ashot)

This library solves the problem like a charm. I wrote a report how I utilized aShot in Katalon Studio:[Entire page screenshot by aShot in Katalon Studio](https://github.com/kazurayam/EntirePageScreenshotByAShotInKatalonStudio)

I have integrated those external resources to build a tool set for *Visual Testing* in Katalon Studio.

## How to run the build-in examples

1. git clone [this project](https://github.com/kazurayam/VisualTestingInKatalonStudio).
1. start your Katalon Studio, open this project
1. in `Tests Explorer` pane, click `Test Suites/CURA`
1. The example assumes you have Firefox browser installed. In not, please install Firefox. Otherwise you can change the definition of Test Suite Collections to use Google Chrome browser.

### Execute_twins

Now you open and run a Test Suite Collection named "`Test Suites/CURA/Execute_twins`".

![Execute_twins](docs/images/Execute_twins.png)


#### Input

The Test Suite Collection `Executes_twins` takes screenshots of a pair of URLs:
1. http://demoaut.katalon.com/  --- can be regarded as *Production environment*
2. http://demoaut-mimic.kazurayam.com/ --- can be regarded as *Development environment*

For example, the test may capture screenshots as follows.

1. Production page: `<projectDir>/Materials/Main.TS1/20180920_165543/Main.Basic/CURA_Homepage.png`  ![Production](docs/images/Production_CURA_Homepage.png)
2. Development page: <projectDir>/Materials/Main.TS1/20180920_165544/Main.Basic/CURA_Homepage.png  ![Development](docs/images/Development_CURA_Homepage.png)

#### Output

This test generates a report at `<projectDir>/Materials/index.html` where you will find screenshot-comparison result.

Please find the html using Windows Explorer or Mac Finder. Unfortunately Katalon Studio GUI does not let us see the Materials/index.html. Open the html with your favorite web browser. You should bookmark it.

The Materials/index.html would show a list of source images plus the images as comparison result.

![index](docs/images/Materials_index.png)

If you click the line with purple background color, you will see a ImageDiff with a lot of red-portion. The red portion shows the differences between the two source images.

File path: `<projectDir>/Materials/ImageDiff/yyyyMMdd_hhmmss/ImageDiff/CURA_Homepage.yyyyMMdd_hhmmss_product-yyyyMMdd_hhmmss_develop.(6.30)FAILED.png`
![ImageDiff](docs/images/ImageDiff_CURA_Homepage.png)

Why red? --- You should investigate it. The tool just let you know of the redness.

### Execute_chronos

The example provides one more interesting script named `Test Suites/CURA/Execute_chronos`.

![Execute_chronos](docs/images/Execute_chronos.png)

#### Input

`Execute_chronos` takes screenshots of a single URL:
- http://demoaut-mimic.kazurayam.com/
and compares it to another set of screenshots taken beforehand to find out if there is any visual differences between the 2 : the current and the previous.

It can compare the current set of screenshot
1. to the last previous one (default)
2. to the one taken before 10 minutes ago
3. to the one taken before 30 minutes ago
4. to the one taken before 1 hour ago
5. to the one taken before 2 hours ago
6. to the one taken before 6AM today
7. to the one taken before 9AM today
8. to the one taken before 12AM today
9. to the one taken before 15PM today
10. to the one taken before 18PM today
11. to the one taken before 21PM today
12. to the one taken before 18PM last evening

you can see how these STRATEGIES are implemented in [Test Cases/VT/restorePreviousTSuiteResult](https://github.com/kazurayam/VisualTestingInKatalonStudio/blob/develop/Scripts/VT/restorePreviousTSuiteResult/Script1550220558541.groovy)

#### Output

The output of `Execute_chronos` is just similart to the one created by `Execute_twins`. Please find `<projectDir>/Materials/index.html`.


## How to make your Katalon Studio project capable of *Visual Testing*

Are you interested in this? Do you want to do similar screenshot-comparison testing against your own Application Under Test in your own Katalon Studio project?

The following page describes how to import `Test Suites/CURA/Execute_twins` and `Test Suites/CURA/Execute_chrones` into your own Katalon Studio project:

- [visualtestinginks --- a Gradle Plugin](https://github.com/kazurayam/visualtestinginks-gradle-plugin)

You can make a copy of "CURA" resources and rename them to your own AUT symbol. You want to modify the code as you want to accomplish your own Visual Testing. The following page describe how to getting started with customization.

- [customVisualTesting](docs/customVisualTesting.md)


# Revision history

see [Revision History](docs/revision_history.md)
