## Aquí defines el contrato del package.
```
CREATE OR REPLACE PACKAGE pkg_states AS

    -- Cursor para devolver listas
    TYPE t_cursor IS REF CURSOR;

    -- Procedimiento que devuelve todos los states
    PROCEDURE listar_states(
        p_states OUT t_cursor
    );

END pkg_states;
/
```

## implementacion
```
CREATE OR REPLACE PACKAGE BODY pkg_states AS

    PROCEDURE listar_states(
        p_states OUT t_cursor
    ) IS
    BEGIN
        OPEN p_states FOR
            SELECT id, name, code
            FROM states;
    END listar_states;

END pkg_states;

```


## probar codigo

```
VARIABLE rc REFCURSOR;

BEGIN
    pkg_states.listar_states(:rc);
END;
/

PRINT rc;

```