# Reflective

Reflective is a collection of classes and functions that simplify working with a subset of the Java Reflection API to instantiate interfaces using Map instances, without the need for implementing classes of the interface.

## Code example

Given the following interface:
```java
public interface UserProfile extends ReflectiveMapBacked {
  @ReflectiveRequired
  String getId();
  @ReflectiveRequired
  String getEmail();
  ...
}
```

We can create an instance of it without creating a new class or an anonymous inner class:
```java
// Create Map instance (this can be changed to parsing JSON into a Map instance using GSON for example)
Mapper mapper = Mapper.obj(
    "id", "abc123",
    "email", "jonny@example.com",
    ...
);

// Create UserProfile instance backed by the `mapper` Map, all keys will be get/set by the methods in UserProfile
UserProfile userProfile = mapper.toReflectiveValidated(UserProfile.class);
userProfile.getId();// = "abc123"
userProfile.getEmail();// = "jonny@example.com"

// This will return the same Mapper instance that was used to create the UserProfile instance
Mapper sameMapperInstance = Mapper.fromReflective(userProfile);
mapper == sameMapperInstance;// true

// Provided you have setup GSON as described in Step 3, we can get a JSON-String from toString()
String jsonString = mapper.toString();// {"id":"abc123","email":"jonny@example.com", ...

Mapper mapperFromJsonString = Mapper.fromString(jsonString);
```

## Functionality

The most powerful functionality of Reflective is to move seamlessly between `interface`, `Mapper` and `String` ([GSON](https://github.com/google/gson) ftw) instances.
You can checkout the repo and look at the tests for more usage examples.

### Mapper
Mapper is a convenience class including much of the goodies of the Reflective functionality.
If you have setup UtilMapper with GSON (described below in `Step 3`) then you can move between `interface`, `Mapper` and `String` with the following Mapper functions:
- `Mapper.fromString()` - JSON-String param
- `Mapper.fromReflective()` - ReflectiveMapBacked param (extend ReflectiveMapBacked in your interfaces to make this work seamlessly)
- `Mapper.toString()` - returns JSON-String
- `Mapper.toReflective()` - returns an interface instance of the type passed as parameter

### MapperList
If you need a List as your root object then you can use MapperList in much the same way as you would use Mapper to move between `interface`, `Mapper` and `String` instances.

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
compile 'com.ztory.lib.reflective:reflective_module:1.6.0'
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
NOTE: You can extend `ReflectiveMapBacked` in your interfaces that you instantiate with Reflective to easily move between `interface`, `Mapper` and `String` instances.

## What else?

If you have any problems or suggestions just create one of those [GitHub Issues](https://github.com/ztory/reflective/issues)!

Happy coding! =]
