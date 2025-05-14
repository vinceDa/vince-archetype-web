# vince-archetype-web
vince's java base archetype, include jwt token, cola and some interceptor...

# how to use
```shell
mvn archetype:generate \                                                                    
  -DarchetypeGroupId=com.vince \
  -DarchetypeArtifactId=vince-archetype-web \
  -DarchetypeVersion=1.0.0 \
  -DgroupId=com.test(replace it with yours) \
  -DartifactId=test(replace it with yours) \
  -Dversion=1.0-SNAPSHOT(replace it with yours) \
  -DinteractiveMode=false
```
