# Reflective

Reflective is a collection of classes and functions that simplify working with a subset of the Java Reflection API.

## Code example
Instantiate a reflective interface instance from a JSONObject, all the methods will get and put values in the JSONObject used to create the interface instance:
```java
String theName = "jonny";
JSONObject jsonObject = new JSONObject();
jsonObject.put("name", theName);
ProfileInterface profile = Reflective.getReflectiveInstance(
        ProfileInterface.class,
        jsonObject,
        Reflective.LOWERCASE_UNDERSCORE,
        null
);
Log.d(
        "Reflective",
        "Reflective interface name: " + profile.getName()
        + " | theName: " + theName
        + " | theName equals the interface name: " + theName.equals(profile.getName())
);
```

## Enough! I want to use it, tell me how!

#### Step 1
In your base `gradle.build` file (the one in project root), add this:
```
maven { url "https://github.com/ztory/reflective/raw/master/maven-repository/" }
```
So that it will look something like this:
```
allprojects {
    repositories {
        jcenter()
        mavenCentral()
        maven { url "https://github.com/ztory/reflective/raw/master/maven-repository/" }
    }
}
```

#### Step 2
In your module `build.gradle` add this:
```
compile 'com.ztory.lib.reflective:reflective_module:1.1.0'
```

## What else?

If you have any problems or suggestions just create one of those [GitHub Issues](https://github.com/ztory/reflective/issues)!

Happy coding! =]
