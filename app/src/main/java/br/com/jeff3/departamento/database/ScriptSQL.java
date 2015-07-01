package br.com.jeff3.departamento.database;

/**
 * Created by jefferson on 28/06/2015.
 */
public class ScriptSQL {

    public static String getCreateDepartamento(){
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS DEPARTAMENTO (");
        sqlBuilder.append("_id INTEGER       NOT NULL");
        sqlBuilder.append("      PRIMARY KEY AUTOINCREMENT,");
        sqlBuilder.append("NOME               VARCHAR (255),");
        sqlBuilder.append("SIGLA             VARCHAR (10)");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getCreateProfessor(){
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS PROFESSOR (");
        sqlBuilder.append("_id INTEGER       NOT NULL");
        sqlBuilder.append("      PRIMARY KEY AUTOINCREMENT,");
        sqlBuilder.append("NOME               VARCHAR (255),");
        sqlBuilder.append("DISCIPLINA             VARCHAR (255),");
        sqlBuilder.append("DEPARTAMENTO      VARCHAR(10)");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getCreateAgenda(){
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS AGENDA (");
        sqlBuilder.append("_id INTEGER       NOT NULL");
        sqlBuilder.append("      PRIMARY KEY AUTOINCREMENT,");
        sqlBuilder.append("DEPARTAMENTO               VARCHAR (255),");
        sqlBuilder.append("PROFESSOR             VARCHAR (255),");
        sqlBuilder.append("DATA                 DATE,");
        sqlBuilder.append("RECURSO              VARCHAR(50),");
        sqlBuilder.append("HORARIO              VARCHAR(5),");
        sqlBuilder.append("TURNO                VARCHAR(50),");
        sqlBuilder.append("OBSERVACOES          VARCHAR(255)");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }
}
