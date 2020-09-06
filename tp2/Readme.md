# TP2 - Genetics Algorithm

# Configuration

To modify each method values, you must change corresponding values from `config.json` file inside `tp2` folder.

# Config.json values

They are all *string* values.

- *population*: [REQUIRED] the size of the population to start.

- *class*: [REQUIRED] the selected class for the population. It possible values can be `warrior`, `archer`, `defender` or `infilitrate`.

- *k*: [REQUIRED] the amount of people that is going to be selected.

*Cross*


*Selection*

Let's first remember that we have to provide 2 selection methods for the selection and two other for the implementation.

- *a*: percentage amount of k for the selection. It must be a value between 0.0 and 1.0.

- *b*: percentage amount of k for the implementation. It must be a value between 0.0 and 1.0.

- *method1*: The selection method that will be implemented for the selection with size a * k.

- *method2*: The selection method that will be implemented for the selection with size (1 - a) * k.

- *method3*: The selection method that will be implemented for the implementation with size b * k.

- *method4*: The selection method that will be implemented for the implementation with size (1 - b) * k.

*Mutation*


Example: 

```
{
  "population": "300",
  "class": "archer",
  "k": "150",
  "mutation": "complete",
  "pm": "0.8",
  "limitm": "6",
  "method1": "ranking",
  "method2": "ranking",
  "method3": "elite",
  "method4": "elite",
  "a": "0.5",
  "b": "0.5",
  "matingType": "uniform",
  "t0": "100",
  "tc": "100000",
  "implementation": "fillParent",
  "m": "5",
  "cut": "generations",
  "generations": "1",
  "time": "1000000000",
  "accepted": "20.0",
  "structure": "10",
  "content": "10"
}
``` 

# Run

To create and execute the jar file use the following command:

```
mvn package && java -jar target/tp2-jar-with-dependencies.jar
```
