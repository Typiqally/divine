printf '\033[8;35;100t'
cd C:/Users/Typically/Documents/Projects/Java/Divine
./gradlew build
java -jar ./build/libs/divine-1.0-SNAPSHOT.jar -d C:/Users/Typically/.divine/gamepack.jar C:/Users/Typically/.divine/gamepack_out.jar
printf "\033[0m"
./gradlew clean
