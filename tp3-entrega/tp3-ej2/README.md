# TP3 - EJ2 - Lineal and NonLineal Simple Perceptron

# Configuration

To modify each value, you must change corresponding value from `config.json` file inside `tp3-ej2` folder.

# Config.json values

They are all *string* values.

- `subtask`: sub task asked on the tp to run. By default it will be `2`.

- `beta`: beta value for non lineal perceptron. By default it will be `5`.

- `threshold`: error threshold. By default it will be `0.01`.

- `rate`: learning rate value. By default it will be `0.01`.


Example: 

```
{
  "beta": "5",
  "threshold": "0.01d",
  "subtask": "2",
  "rate": "0.01d"
}
``` 

# Run

To create and execute the jar file use the following command:

```
mvn clean install -U  && mvn package && java -jar target/tp3-ej2-jar-with-dependencies.jar
```
