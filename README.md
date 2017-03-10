# Custom Floating Action Button
This view is for replacement of standard Floating Action Button from Google Support Library. It is easy to use, customizable and you can also add text to button

[![Android Arsenal](https://github.com/robertlevonyan/materialChipView/blob/master/Images/aa.png)](https://android-arsenal.com/details/1/5396)

## Setup

#### Gradle:

Add following line of code to your module(app) level gradle file

```java
    compile 'com.robertlevonyan.view:CustomFloatingActionButton:1.0.2'
```

#### Maven:

```xml
  <dependency>
    <groupId>com.robertlevonyan.view</groupId>
    <artifactId>CustomFloatingActionButton</artifactId>
    <version>1.0.2</version>
    <type>pom</type>
  </dependency>
```

## Usage

```xml
  <com.robertlevonyan.views.customfloatingactionbutton.CustomFloatingActionButton
    android:id="@+id/custom_fab"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="bottom|end" />
```

### Cutomizing Floating Action Button

```xml
    app:fabIcon="@drawable/customIcon"
    app:fabIconColor="@color/customColor"
```

![alt text](https://github.com/robertlevonyan/materialChipView/blob/master/Images/closable.png)

