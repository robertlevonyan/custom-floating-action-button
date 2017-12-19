# Custom Floating Action Button
This view is for replacement of standard Floating Action Button from Google Support Library. It is easy to use, customizable and you can also add text to button

<!--- [![Android Arsenal](https://github.com/robertlevonyan/customFloatingActionButton/blob/master/Images/aa.png)](https://android-arsenal.com/details/1/5396)--->

## Setup

#### Gradle:

Add following line of code to your module(app) level gradle file

```java
    compile 'com.robertlevonyan.view:CustomFloatingActionButton:2.0.2'
```

#### Maven:

```xml
  <dependency>
    <groupId>com.robertlevonyan.view</groupId>
    <artifactId>CustomFloatingActionButton</artifactId>
    <version>2.0.2</version>
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
|----------------------|-----------------------------------------------------------------------------------------|

|![FAB](https://github.com/robertlevonyan/customFloatingActionButton/blob/master/Images/fab_circle.jpg)|![FAB](https://github.com/robertlevonyan/customFloatingActionButton/blob/master/Images/fab_circle_text.jpg)|

|![FAB](https://github.com/robertlevonyan/customFloatingActionButton/blob/master/Images/fab_rounded_square.jpg)|![FAB](https://github.com/robertlevonyan/customFloatingActionButton/blob/master/Images/fab_rounded_square_text.jpg)|

|![FAB](https://github.com/robertlevonyan/customFloatingActionButton/blob/master/Images/fab_square.jpg)|![FAB](https://github.com/robertlevonyan/customFloatingActionButton/blob/master/Images/fab_square_text.jpg)|

```xml
<com.robertlevonyan.examples.customfloatingactionbutton.view.FloatingActionLayout
        android:id="@+id/customFABL"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal">
        
        <!--Add any layout here-->
        
</com.robertlevonyan.examples.customfloatingactionbutton.view.FloatingActionLayout>        
```

|----------------------------------|----------------------------------|----------------------------------|
|![FAB](https://github.com/robertlevonyan/customFloatingActionButton/blob/master/Images/fal_circle.jpg)|![FAB](https://github.com/robertlevonyan/customFloatingActionButton/blob/master/Images/fal_rounded_square.jpg)|
|![FAB](https://github.com/robertlevonyan/customFloatingActionButton/blob/master/Images/fal_square.jpg)|


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


### Customizing



### Attributes

|Custom Atributes      |Description                                                                              |
|----------------------|-----------------------------------------------------------------------------------------|
|`app:fabType`         |Visual style of Floating Action Button (values: circle (default), square, roundedSquare) |
|`app:fabSizes`        |Sizes of Floating Action Button (values: normal (default), mini)                         |
|`app:fabText`         |Text label of Floating Action Button                                                     |
|`app:fabTextAllCaps`  |Set text label all capitals                                                              |
|`app:fabTextColor`    |Set custom color for text label                                                          |
|`app:fabElevation`    |Change the elevation of view                                                             |
|`app:fabColor`        |Custom color for Floating Action Button (default value is accent color)                  |
|`app:fabIcon`         |Custom icon for Floating Action Button                                                   |
|`app:fabIconColor`    |Custom color for icon                                                                    |
|`app:fabIconPosition` |Icon position for icon (values: start (default), top, end, bottom)                       |
|                      |                                                                                         |
|`app:fabMenuStyle`    |Animation style for items appearing (values: popUp (default), popDown, popRight, popLeft)|
|`app:fabMenuGravity`  |Gravity of menu and items (values: start (default), end, bottom, top, center_horizontal, center_vertical, center)|
|`app:fabAnimateMenu`  |Flag to animate menu toggle                                                              |

### Setting Listeners

```java
    CustomFloatingActionButton customFAB = (CustomFloatingActionButton) findViewById(R.id.custom_fab);
    
    customFab.setOnFabClickListener(new OnFabClickListener() {
        @Override
        public void onFabClick(View v) {
            //Your action here...
        }
    });
```

### Customizing Floating Action Button from Java

```java
    customFAB.setFabType(); /* Set FAB type | CustomFloatingActionButton.FAB_TYPE_CIRCLE, 
                                              CustomFloatingActionButton.FAB_TYPE_SQUARE, 
                                              CustomFloatingActionButton.FAB_TYPE_ROUNDED_SQUARE */                                              
    customFAB.setFabSize(); /* Set FAB size | CustomFloatingActionButton.FAB_SIZE_NORMAL, 
                                              CustomFloatingActionButton.FAB_SIZE_MINI */
    customFAB.setFabText(); // Set text label
    customFAB.setFabTextAllCaps(); // Set label all capitals
    customFAB.setFabTextColor(); // Change color of label
    customFAB.setFabElevation(); // Change drop shadow size
    customFAB.setFabColor(); // Change FAB color
    customFAB.setFabIcon(); // Change FAB icon
    customFAB.setFabIconColor(); // Change FAB icon color
    customFAB.setFabIconPosition(); /* Change FAB icon position | CustomFloatingActionButton.FAB_ICON_START, 
                                                                  CustomFloatingActionButton.FAB_ICON_TOP,
                                                                  CustomFloatingActionButton.FAB_ICON_END, 
                                                                  CustomFloatingActionButton.FAB_ICON_BOTTOM */
```

## Versions

###  2.0.0 - 2.0.2

Second version of library.
Totaly rewritten.
Added Floating action layout and Floating layout

###  1.0.0 - 1.0.3

First version of library with some bugfixes

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
    
