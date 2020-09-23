# TP3 - EJ1 - Simple Perceptron

# Configuration

To modify each value, you must change corresponding value from `config.json` file inside `tp3-ej1` folder.

# Config.json values

They are all *string* values.

- `logicOperator`: the logic operator that will be learned. The possible values are `AND` or `XOR`. By default it will be `AND`.

- `iterations`: the maximum amount of iterations that will cycle. The default value is 1000.


Example: 

```
{
  "logicOperator": "XOR",
  "iterations": "1000"
}
``` 

# Run

To create and execute the jar file use the following command:

```
mvn clean install -U  && mvn package && java -jar target/tp3-ej1-jar-with-dependencies.jar
```
