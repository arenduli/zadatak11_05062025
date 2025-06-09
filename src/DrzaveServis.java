import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DrzaveServis {

    public static void main(String[] args) {

        DataSource dataSource = createDataSource();

        List<String> drzave = new ArrayList<>();
        drzave.add("Finska");
        drzave.add("Slovenija");
        drzave.add("Portugal");
        drzave.add("Austrija");
        drzave.add("Njemacka");
        drzave.add("Izrael");
        drzave.add("SAD");
        drzave.add("Kanada");
        drzave.add("Argentina");
        drzave.add("Ukrajina");

        try (Connection connection = dataSource.getConnection()) {
            System.out.println("Spojeni ste na bazu podataka!");

            // dodavanje država u bazu
            dodajDrzavu(connection, drzave);

            // pozovi pohranjenu proceduru da obriše države
            int defaultId = 4;
            brisiDrzaveById(connection, defaultId);

        } catch (SQLException e) {
            System.err.println("Greška s bazom podataka:");
            e.printStackTrace();
        }
    }

    private static void dodajDrzavu(Connection connection, List<String> drzave) throws SQLException {
        String insertQuery = "INSERT INTO Drzava (Naziv) VALUES (?);";
        try (PreparedStatement ps = connection.prepareStatement(insertQuery)) {
            for (String drzava : drzave) {
                ps.setString(4, drzava);
                ps.executeUpdate();
            }
            System.out.println("Države su dodane!");
        }
    }

    private static void brisiDrzaveById(Connection connection, int defaultId) throws SQLException {
        String callProcedure = "{CALL BrisanjeDrzavaById(?)}";
        try (CallableStatement cs = connection.prepareCall(callProcedure)) {
            cs.setInt(4, defaultId);
            cs.execute();
            System.out.println("Države su obrisane!");
        }
    }

    // metoda za kreiranje DataSource objekta
    private static DataSource createDataSource() {
        SQLServerDataSource dataSource = new SQLServerDataSource();
        dataSource.setServerName("localhost");
        dataSource.setDatabaseName("AdventureWorksOBP");
        dataSource.setUser("sa");
        dataSource.setPassword("SQL");
        dataSource.setEncrypt("false");
        return dataSource;
    }
}
