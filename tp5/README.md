# TP5 - Encoder and AutoEncoder

# Configuration

To modify each value, you must change corresponding value from `config.json` file inside `tp5` folder.

Check in generated json map for exercise 2.

# Config.json values

They are all *string* values.

- `threshold`: threshold value for the larning step. Its default value is 0.1f.

- `mlp_lrate_even`: leraning rate value. Its default value is 0.1f.

- `mlp_iter_even`: iterations for the mlp. Its default value is 5.

- `mlp_even_partition`: mlp partition value. Its default value is 5.

- `noise_percentage`: the percentage of noise. Its default value is 1.

- `ej`: the exercise to run. Its default value is 1. Its possible values are `1`, `1-noise` and  `2`.

- `font`: the number of the font to be used. Its default value is 1. Its possible values are 1, 2 or 3.

- `letters`: amount of items. Its default value is 5.

- `activation_method`: the activation method to be used. If its 0 then sigmoid, if its 1 then Relu, if its 2 then LeakyRelu, other values is SoftPlus . Its default value is 0.

- `layer_size`: the amount of layers to use. Its default value is 5.

- `middelLayer`: the size of the middle layer. Its default value is 2.

- `kohonen_k`: k value for kohonen. Its default value is 3.

- `kohonnen_lr`: learning rate for kohonen. Its default value is 0.1f.

- `kohonen_delta`: delta value for kohonen. Its default value is 2.

- `error`: the acceptable error to finish when learning. Its default value is 1.5f.

- `lr_a`: the value for `a` for the learning rate optimization. Its default value is  0.01f.

- `lr_b`: the value for `b` for the learning rate optimization. Its default value is 0.01f.

- `iterations`: amount of epochs. Its default value is 20.

- `mapSize`: the height and width of the map. Its default value is 8.

Example: 

```
{
  "threshold": "0.1",
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
