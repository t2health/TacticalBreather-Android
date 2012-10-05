The Tactical Breather Project is broken out into seperate sub-projects.

To get a build working for the Android environment in Eclipse (in Windows):
Checkout the "android" subproject as a top-level project in eclipse.
Checkout the "main" subproject as a top-level project in eclipse.
At a command line, navigate to the assets folder.  Create a directory symlink to the www subfolder in the main project: "mklink /D www ..\..\TB_main\www" (where TB_main is the folder for the the "main" project)
