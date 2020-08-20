# sia-tps

# TP1 - Sokoban Game

# Configuration

To modify the algorithm, heuristic and other values, you must change corresponding values from `config.json` file inside `tp1` folder.

# Config.json values

They are all string values.

 - `algorithm`: [REQUIRED] it must be either `BFS`, `DFS`, `IDDFS`, `A*`, `IDA*` or `GG`.
 
 - `map`: [REQUIRED] it must be a  number between 1 and 10 to select and existing map.
 
 - `depth`: if you use the `IDDFS` algorithm, you may set its depth. The default value is 100.
 
 - `limit`: if you use the `IDA*` algorithm, you may set its maximum limit. The default value is 100.
 
 - `heuristic`: you can select which heuristic to use for `A*`, `IDA*` and `GG`. The possible values are `bg`, `pb`, `bgw`. The default value is bg.
 
 `bg`'s heuristic ...
 `pb`'s heuristic ...
 `bgw`'s heuristic ...

# Run

To create and execute the jar file use the following command:

```
mvn package && java -jar target/tp1-jar-with-dependencies.jar
```
