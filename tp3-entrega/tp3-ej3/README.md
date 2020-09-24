# TP3 - EJ2 - Multilayer Perceptron

# Configuration

To modify each value, you must change corresponding value from `config.json` file inside `tp3-ej3` folder.

# Config.json values

They are all *string* values.

- `mlp_lrate_xor`: rate value for xor.

- `mlp_lrate_even`: rate value for even.

- `mlp_iter_xor`: iterations for xor.

- `mlp_iter_even`: iterations for even.

- `mlp_even_partition`: even partition value.


Example: 

```
{
  "mlp_lrate_xor": "0.3f",
  "mlp_lrate_even": "0.1f",
  "mlp_iter_xor": "100",
  "mlp_iter_even": "100",
  "mlp_even_partition": "9"
}
``` 

# Run

To create and execute the jar file use the following command:

```
mvn clean install -U  && mvn package && java -jar target/tp3-ej3-jar-with-dependencies.jar
```
