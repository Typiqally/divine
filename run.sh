printf '\033[8;35;100t'
./gradlew build
#java -cp ".\build\libs\divine-1.0-SNAPSHOT.jar;.\deobfuscator\build\libs\deobfuscator-1.0-SNAPSHOT.jar;.\divine-api\build\libs\divine-api-1.0-SNAPSHOT.jar;.\divine-client\build\libs\divine-client-1.0-SNAPSHOT.jar" divine.boot.Boot
java -jar ./build/libs/divine-1.0-SNAPSHOT.jar -d C:/Users/Typically/.divine/gamepack.jar C:/Users/Typically/.divine/gamepack_out.jar
printf "\033[0m"
./gradlew clean
