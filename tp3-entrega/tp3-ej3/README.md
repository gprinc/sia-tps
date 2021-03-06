# TP3 - EJ2 - Multilayer Perceptron

# Configuration

To modify each value, you must change corresponding value from `config.json` file inside `tp3-ej3` folder.

Check in generated csv files for more output.

# Config.json values

They are all *string* values.

- `threshold`: threshold value for the larning step. Its default value is 0.1f.

- `accuracy`: accuracy value for the errors. Its default value is 0.001f.

- `mlp_lrate_xor`: learning rate value for xor. Its default value is 0.3f.

- `mlp_lrate_even`: leraning rate value for even. Its default value is 0.1f.

- `mlp_iter_xor`: iterations for xor. Its default value is 100.

- `mlp_iter_even`: iterations for even. Its default value is 5.

- `mlp_even_partition`: even partition value. Its default value is 5.


Example: 

```
{
  "threshold": "0.1f",
  "accuracy": "0.001f",
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
