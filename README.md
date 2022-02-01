# Custom Floating Action Button

|This view is for replacement of standard Floating Action Button from Google Support Library. It is easy to use, customizable and you can also add text to button|<img src="https://github.com/robertlevonyan/custom-floating-action-button/blob/master/Images/fab.png"  width="900" />|
|----------------------------------------------------------------------------------------------|-----------|

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Custom%20Floating%20Action%20Button-yellow.svg?style=flat-square)](https://android-arsenal.com/details/1/6570) [![API](https://img.shields.io/badge/API-17%2B-yellow.svg?style=flat-square)](https://android-arsenal.com/api?level=14) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.robertlevonyan.view/CustomFloatingActionButton/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.robertlevonyan.view/CustomFloatingActionButton)
## Setup

Add following line of code to your project level gradle file

```kotlin
  repositories {
    mavenCentral()
  }
```

#### Gradle:

Add following line of code to your module(app) level gradle file

```groovy
    implementation 'com.robertlevonyan.view:CustomFloatingActionButton:<LATEST_VERSION>'
```

#### Kotlin:

```kotlin
    implementation("com.robertlevonyan.view:CustomFloatingActionButton:$LATEST_VERSION")
```

#### Maven:

```xml
  <dependency>
    <groupId>com.robertlevonyan.view</groupId>
    <artifactId>CustomFloatingActionButton</artifactId>
    <version>LATEST_VERSION</version>
    <type>pom</type>
  </dependency>
```

## Usage

```xml
  <com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton
    android:id="@+id/custom_fab"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="bottom|end" />
```

|![](https://github.com/robertlevonyan/custom-floating-action-button/blob/master/Images/fab_circle_2.jpg)|![](https://github.com/robertlevonyan/custom-floating-action-button/blob/master/Images/fab_circle_text_2.jpg)|
|----------------------|-----------------------------------------------------------------------------------------|
|![](https://github.com/robertlevonyan/custom-floating-action-button/blob/master/Images/fab_rounded_square_2.jpg)|![](https://github.com/robertlevonyan/custom-floating-action-button/blob/master/Images/fab_rounded_square_text_2.jpg)|
|![](https://github.com/robertlevonyan/custom-floating-action-button/blob/master/Images/fab_square_2.jpg)|![](https://github.com/robertlevonyan/custom-floating-action-button/blob/master/Images/fab_square_text_2.jpg)|

```xml
<com.robertlevonyan.examples.customfloatingactionbutton.view.FloatingActionLayout
        android:id="@+id/customFABL"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal">
        
        <!--Add any layout here-->
        
</com.robertlevonyan.examples.customfloatingactionbutton.view.FloatingActionLayout>        
```

|Circle layout                          |Rounded square layout                   |Square layout                         |
|---------------------------------------|----------------------------------------|--------------------------------------|
|<img src="https://github.com/robertlevonyan/custom-floating-action-button/blob/master/Images/fal_circle_2.jpg" width="270">|<img src="https://github.com/robertlevonyan/custom-floating-action-button/blob/master/Images/fal_rounded_square_2.jpg" width="270">|<img src="https://github.com/robertlevonyan/custom-floating-action-button/blob/master/Images/fal_square_2.jpg" width="270">|

```xml
<com.robertlevonyan.examples.customfloatingactionbutton.view.FloatingLayout
        android:id="@+id/floating_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        
        <!--
        Add here only 
        com.robertlevonyan.examples.customfloatingactionbutton.view.FloatingActionButton 
        or android.support.design.widget.FloatingActionButton,
        other views will be neglected by the layout
        -->
        
        <!--Top view will be selected as a toggle for menu-->
        <com.robertlevonyan.examples.customfloatingactionbutton.view.FloatingActionButton 
        ... />
        
        <com.robertlevonyan.examples.customfloatingactionbutton.view.FloatingActionButton 
        ... />
        
        <com.robertlevonyan.examples.customfloatingactionbutton.view.FloatingActionButton 
        ... />
        
        <com.robertlevonyan.examples.customfloatingactionbutton.view.FloatingActionButton 
        ... />
        
        <android.support.design.widget.FloatingActionButton
        ... />
        
</com.robertlevonyan.examples.customfloatingactionbutton.view.FloatingLayout>
```

|Up                          |Down                   |Right                         |Left                         |
|----------------------------|-----------------------|------------------------------|-----------------------------|
|<img src="https://github.com/robertlevonyan/custom-floating-action-button/blob/master/Images/fl_up_2.jpg" width="200">|<img src="https://github.com/robertlevonyan/custom-floating-action-button/blob/master/Images/fl_down_2.jpg" width="200">|<img src="https://github.com/robertlevonyan/custom-floating-action-button/blob/master/Images/fl_right_2.jpg" width="200">|<img src="https://github.com/robertlevonyan/custom-floating-action-button/blob/master/Images/fl_left_2.jpg" width="200">|

![](https://github.com/robertlevonyan/custom-floating-action-button/blob/master/Images/fl_expand_collapse_2.gif)

### Attributes

|Custom Atributes        |Description                                                                              |
|------------------------|-----------------------------------------------------------------------------------------|
|`app:fabType`           |Visual style of Floating Action Button (values: circle (default), square, roundedSquare) |
|`app:fabSizes`          |Sizes of Floating Action Button (values: normal (default), mini)                         |
|`app:fabElevation`      |Change the elevation of view                                                             |
|`app:fabColor`          |Custom color for Floating Action Button (default value is accent color)                  |
|`app:fabIcon`           |Custom icon for Floating Action Button                                                   |
|`app:fabIconColor`      |Custom color for icon                                                                    |
|`app:fabRippleColor`    |Custom ripple color for button                                                           |
|`app:fabIconPosition`   |Icon position for icon (values: start (default), top, end, bottom)                       |
|`app:fabMenuStyle`      |Animation style for items appearing (values: popUp (default), popDown, popRight, popLeft)|
|`android:gravity`       |Gravity of menu and items                                                                |
|`app:fabAnimateMenu`    |Flag to animate menu toggle                                                              |
|`app:fabAnimateDuration`|Flag to animate menu toggle                                                              |

You can set a text and customize it using 'android' namespace like on a normal Button or TextView.

### Customizing from code

```kotlin
    floatingActionButton.fabType = FabType.FAB_TYPE_CIRCLE  /* Set button type FabType.FAB_TYPE_CIRCLE, 
                                                                               FabType.FAB_TYPE_SQUARE, 
                                                                               FabType.FAB_TYPE_ROUNDED_SQUARE */                           
    floatingActionButton.fabSize = FabSize.FAB_SIZE_MINI    /* Set button size FabSize.FAB_SIZE_NORMAL, 
                                                                               FabSize.FAB_SIZE_MINI */
    
    floatingActionButton.fabElevation = 7f                  // Change elevation
    floatingActionButton.fabColor = myFabColor              // Change background color
    floatingActionButton.fabIcon = myFabIconDrawable        // Change icon
    floatingActionButton.fabIconColor = myFabIconColor      // Change icon color
    floatingActionButton.fabRippleColor = myFabRippleColor  // Change ripple color
    floatingActionButton.fabIconPosition = FabIconPosition.FAB_ICON_START /* Change icon position                   
                                                                               FabIconPosition.FAB_ICON_START, 
                                                                               FabIconPosition.FAB_ICON_TOP,
                                                                               FabIconPosition.FAB_ICON_END, 
                                                                               FabIconPosition.FAB_ICON_BOTTOM */
```

```kotlin
    floatingActionLayout.fabType = FabType.FAB_TYPE_CIRCLE  /* Set button type FabType.FAB_TYPE_CIRCLE, 
                                                                               FabType.FAB_TYPE_SQUARE, 
                                                                               FabType.FAB_TYPE_ROUNDED_SQUARE */     
    floatingActionLayout.fabElevation = 7f                  // Change elevation
    floatingActionLayout.fabColor = myFabColor              // Change background color
    floatingActionButton.fabRippleColor = myFabRippleColor  // Change ripple color
```

```kotlin
        floatingLayout.fabAnimationStyle = FabMenuAnimation.ANIMATION_POP_UP /* Set the pop animation of items 
                                                                               FabMenuAnimation.ANIMATION_POP_UP, 
                                                                               FabMenuAnimation.ANIMATION_POP_DOWN,
                                                                               FabMenuAnimation.ANIMATION_POP_RIGHT,
                                                                               FabMenuAnimation.ANIMATION_POP_LEFT */
```
### Setting Listeners

```java
    //Java
    floatingLayout.setOnMenuExpandedListener(new FloatingLayout.OnMenuExpandedListener() {
            @Override
            public void onMenuExpanded() {
                // Do stuff when expanded...
            }

            @Override
            public void onMenuCollapsed() {
                // Do stuff when collapsed...
            }
        });
```

```kotlin
    //Kotlin
    floatingLayout.doOnExpand {
        // Do stuff when expanded...
    }
    
    floatingLayout.doOnCollapse {
        // Do stuff when collapsed...
    }
```
## Versions

### 3.1.1 - 3.1.4

Update to Java 11
SDK 31 ready
Minor updates

#### 3.1.0

Migration to mavenCentral

####  3.0.1 - 3.0.6

Small updates

###  3.0.0

New version fully rewritten in Kotlin with several bugfixes

####  2.1.0

Some bug fixing

###  2.0.0 - 2.0.4

Second version of library.
Totaly rewritten.
Added Floating action layout and Floating layout

###  1.0.0 - 1.0.3

First version of library with some bugfixes

## Contact

- **Email**: me@robertlevonyan.com
- **Website**: https://robertlevonyan.com/
- **Medium**: https://medium.com/@RobertLevonyan
- **Twitter**: https://twitter.com/@RobertLevonyan
- **Facebook**: https://facebook.com/robert.levonyan
- **Google Play**: https://play.google.com/store/apps/dev?id=5477562049350283357

<a href="https://www.buymeacoffee.com/robertlevonyan">
  <img src="https://github.com/robertlevonyan/custom-floating-action-button/blob/master/Images/coffee.jpeg"  width="300" />
</a>

## Licence

```
    Custom Floating Action Button©
    Copyright 2017 Robert Levonyan
    Url: https://github.com/robertlevonyan/materialChipView
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
```
    
