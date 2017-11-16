for f in *.java
do
    echo "***Building $f***"
    javac $f
done

echo "*Running Controller*"
java Controller
