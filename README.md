# vince-archetype-web
vince's java base archetype, include jwt token, cola and some interceptor...

# how to use
mvn archetype:generate \                                                                    
  -DarchetypeGroupId=com.vince \
  -DarchetypeArtifactId=vince-archetype-web \
  -DarchetypeVersion=1.0.0 \
  -DgroupId=yourgroupid(like com.test) \
  -DartifactId=yourartifactId(like test) \
  -Dversion=your version(like 1.0-SNAPSHOT) \
  -DinteractiveMode=false
