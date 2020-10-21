# TP4 - Kohonen, Oja and Hopfield

# Configuration

To modify each value, you must change corresponding value from `config.json` file.

# Config.json values

They are all *string* values.


- `ej`: defines what exercise wil execute. The possible values are `Kohonen`, `Oja` and `Hopfield`. The default value is `Kohonen`.

- `rate`: the learning rate used in `Oja`. The default value is `0.01`.

- `iterations`: the iterations used in `Oja`. The default value is `1000`.

- `hopfieldIterations`: the iterations used in `Hopfield`. The default value is `1`.

- `k`: k value for `Kohonen` matrix. The default value is `5`.

- `delta`: delta used in `Kohonen`. The default value is `2`.

- `hopfieldBits`: the amount of bits to be modified in the first part for `Hopfield`. The default value is `1`.

- `initialized`: a boolean value to used values to initialize weights for `Kohonen`. The default value is `true`.

- `letter1`: first letter to train `Hopfield`. The default value is `a`.

- `letter2`: second letter to train `Hopfield`. The default value is `b`.

- `letter3`: third letter to train `Hopfield`. The default value is `c`.

- `letter4`: forth letter to train `Hopfield`. The default value is `d`.

- `letter5`: letter used outside from the ones learned for `Hopfield` exercise. The default value is `z`.

Example: 

```
{
  "ej": "Kohonen",
  "rate": "0.01",
  "iterations": "1000",
  "letter1": "a",
  "letter2": "b",
  "letter3": "c",
  "letter4": "d",
  "letter5": "t",
  "hopfieldIterations": "2",
  "k": "2",
  "delta": "2",
  "hopfieldBits": "3",
  "initialized": "true"
}
``` 

# Run

To create and execute the jar file use the following command:

```
mvn clean install -U  && mvn package && java -jar target/tp4-jar-with-dependencies.jar
```
