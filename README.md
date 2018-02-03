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
compile 'com.ztory.lib.reflective:reflective_module:1.5.2'
```

#### Step 3
If you want to use Mapper and MapperList classes in a simple way with [GSON](https://github.com/google/gson) you should initialize the following in your `Application` class:
```
// Used to interpret method-names to actual keys in underlying Map.
UtilMapper.initDefaultKeyParser(Reflective.CAMELCASE);

// Defines how Mapper instances should be serialized to a String
UtilMapper.initDefaultMapperSerializer(
    new MapperSerializer() {
        @Override
        public String getMapperString(Mapper mapper) {
            return GSON.toJson(mapper, Mapper.class);
        }
    }
);

// Defines how Mapper instances should be deserialized from a String
UtilMapper.initDefaultMapperDeserializer(
    new MapperDeserializer() {
        @Override
        public Mapper getMapper(String mapperString) {
            return GSON.fromJson(mapperString, Mapper.class);
        }
    }
);

// Defines how MapperList instances should be serialized to a String
UtilMapper.initDefaultMapperListSerializer(
    new MapperListSerializer() {
        @Override
        public String getMapperListString(MapperList mapper) {
            return GSON.toJson(mapper, MapperList.class);
        }
    }
);

// Defines how MapperList instances should be deserialized from a String
UtilMapper.initDefaultMapperListDeserializer(
    new MapperListDeserializer() {
        @Override
        public MapperList getMapperList(String mapperString) {
            return GSON.fromJson(mapperString, MapperList.class);
        }
    }
);
```
NOTE: You can extend `ReflectiveMapBacked` in your interfaces that you instantiate with Reflective to easily move between POJO<->Reflective<->String types.

## What else?

If you have any problems or suggestions just create one of those [GitHub Issues](https://github.com/ztory/reflective/issues)!

Happy coding! =]
