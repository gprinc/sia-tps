# TP5 - Encoder and AutoEncoder

# Configuration

To modify each value, you must change corresponding value from `config.json` file inside `tp5` folder.

Check in generated json map for exercise 2.

# Config.json values

They are all *string* values.

- `threshold`: threshold value for the larning step. Its default value is 0.1f.

- `accuracy`: accuracy value for the errors. Its default value is 0.001f.

- `mlp_lrate_even`: leraning rate value for even. Its default value is 0.1f.

- `mlp_iter_even`: iterations for even. Its default value is 5.

- `mlp_even_partition`: even partition value. Its default value is 5.


Example: 

```
{
  "threshold": "0.1",
  "accuracy": "0.001",
  "mlp_lrate_even": "0.001",
  "mlp_iter_even": "200",
  "noise_percentage": "2",
  "ej": "2",
  "font": "1",
  "letters": "5",
  "activation_method": "0",
  "layer_size": "10",
  "middleLayer": "80",
  "kohonen_k": "3",
  "kohonen_lr": "0.01",
  "kohonen_delta": "2",
  "error": "2",
  "lr_a": "0.0001",
  "lr_b": "0.1",
  "iterations": "20",
  "mapSize": "8"
}
``` 

# Run

To create and execute the jar file use the following command:

```
mvn clean install -U  && mvn package && java -jar target/tp5-jar-with-dependencies.jar
```
