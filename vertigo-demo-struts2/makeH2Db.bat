java -cp src/dev/lib/h2-1.3.176.jar org.h2.tools.RunScript -url jdbc:h2:database/demo -script src/main/sqlgen/crebas.sql

java -cp src/dev/lib/h2-1.3.176.jar org.h2.tools.RunScript -url jdbc:h2:database/demo -script src/main/bdd/script/installation/init_data.sql 

pause