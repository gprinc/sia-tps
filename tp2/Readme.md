# TP2 - Genetics Algorithm

# Configuration

To modify each method values, you must change corresponding values from `config.json` file inside `tp2` folder.

# Config.json values

They are all *string* values.

- `population`: [REQUIRED] the size of the population to start.

- `class`: [REQUIRED] the selected class for the population. It possible values can be `warrior`, `archer`, `defender` or `infilitrate`.

- `k`: [REQUIRED] the amount of people that is going to be selected.

- `t0` and `tc`: are the temperature values.

## Selection ## 

Let's first remember that we have to provide 2 selection methods for the selection and two other for the implementation.

The possible selection methods are `elite`, `roulette`, `universal`, `ranking`, `boltzmann`, `dTournament` and `pTournament`. The default method is `roulette`.

- `a`: percentage amount of k for the selection. It must be a value between 0.0 and 1.0. By default will be 0.5.

- `b`: percentage amount of k for the implementation. It must be a value between 0.0 and 1.0. By default will be 0.5.

- `method1`: The selection method that will be implemented for the selection with size a * k.

- `method2`: The selection method that will be implemented for the selection with size (1 - a) * k.

-  `method3`: The selection method that will be implemented for the implementation with size b * k.

- `method4`: The selection method that will be implemented for the implementation with size (1 - b) * k.


## Mating

- `matingType`: The mating type that will be used. It possible values are `onePoint`, `twoPoints`, `anular` and `uniform`. By default will be `uniform`. 

- `pm`: the percenttage value for the possible mutation. By default will be 0.5.

## Mutation

- `mutation`: The mutation type that will be used. It possible values are `gene`, `limitedMultigene`, `uniformMultigene` and `complete`. By default will be `gene`. 

- `pm`: the percenttage value for the possible mutation. By default will be 0.5.

- `limitm`: the limit used by `limitedMultigene`.

## Implementation

- `implementation`: implementation type that is going to be used. Its possible values are `fillAll` and `fillParent`. By default will be `fillAll`.

- `m`: m value for `dTournament`

## Cut

- `cut`: the way that the algorithm will cut. Its possible values are `generations`, `time`, `accepted`, `structure` and `content`. By default will be `generations`.

- `generations`: the amount of generations that will have to pass to cut. It's also used with `structure` to set the generations' amount to wait until cut. The dault value is 10.

- `time`: the amount of seconds to cut. The default is 1000000 seconds.

- `accepted`: the minimum accepted value to cut. The default is 20.0.

- `structure`: the percentage of change that is required to check if it's needed to cut until `generations` amount. The default value is 0.5.

-  `content`: the generations to wait until cut if max value doesn't change. The default is 5.

Example: 

```
{
  "population": "300",
  "class": "archer",
  "k": "150",
  "method1": "ranking",
  "method2": "ranking",
  "method3": "elite",
  "method4": "elite",
  "a": "0.5",
  "b": "0.5",
  "mutation": "complete",
  "pm": "0.8",
  "limitm": "6",
  "implementation": "fillParent",
  "matingType": "uniform",
  "t0": "100",
  "tc": "100000",
  "m": "5",
  "cut": "generations",
  "generations": "1",
  "time": "1000000000",
  "accepted": "20.0",
  "structure": "0.5",
  "content": "10"
}
``` 

# Run

To create and execute the jar file use the following command:

```
mvn package && java -jar target/tp2-jar-with-dependencies.jar
```
