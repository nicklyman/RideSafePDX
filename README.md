# RIDE SAFE PDX

#### RIDE SAFE PDX, July 29, 2016

#### By **Daniel Wulff, Kevin Deganos, Ming Wen, Inthrayuth Mahaphol, and Nick Lyman**

## Description

Ride Safe PDX is an Android application that stores and displays bicycle accidents and near-misses. The goal of this project is to provide an app for users to view recent incidents to hopefully be able to avoid dangerous areas. If a bicycle accident or near-miss occurs, a user is able to enter information about the accident after logging in. Details include the type of accident, severity, description, date and time, address, and police report number (if available). Over time, this application will be able to help visualize if there are areas with multiple incidents and be made safer for cyclists.

## Setup/Installation Requirements

* In a terminal navigate to a directory you'd like to place the project folder and type $ git clone https://github.com/nicklyman/RideSafePDX
* Start Android Studio
* Select 'Open an existing Android Studio project'
* Browse to the directory containing the cloned the project 
* For viewing maps, you will need a Google Maps API key - https://developers.google.com/maps/documentation/android-api/start
    * Start at step 4 in link above
        * Select "Project" in dropdown menu
            * Navigate to /app/src/debug/res/values/google_maps_api.xml file
                * Your API key will replace "Your API Key Here" 
    * It is also necessary to add your SHA-1 certificate fingerprint in the Google Developer Console 
        * Select "Credentials", then select "Add package name and fingerprint"
        * Copy and paste into terminal: keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
        * Add package name from the AndroidManifest.xml file
        * Add SHA-1 fingerprint certificate from terminal response and click "Save"
* To run the app select the menu Run > Run 'app'

## Known Bugs

No known issues at this time.

## Support and contact details

If you find any errors or have trouble viewing the app feel free to submit an issue "bug" report via Github.

## Technologies Used

* Git
* Github
* Android Studio 2 - built with SDK V. 23.0.3 with minimum support for SDK V. 15
* Google Maps API
* Google Geocoder API
* Firebase

### License

Copyright (c) 2016 Daniel Wulff, Kevin Deganos, Ming Wen, Inthrayuth Mahaphol, and Nick Lyman

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. Copyright (c) 2016 