package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import db.DB;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DB.getConnection();
			// Espera como argumento uma string que ir� ser o comando SQL
			st = conn.prepareStatement(
					"INSERT INTO seller" 
			         + "(Name, Email, BirthDate, BaseSalary, DepartmentId) " 
					 + "VALUES"
					 // O interroga��o � um local que � chamado de place holder, aonde voc� depois vai inserir o valor depois
					 + "(?, ?, ?, ?, ?)",
					 // � um tipo enumerado, voc� vai colocar esse valor depois de fazer a string total.		 
					 Statement.RETURN_GENERATED_KEYS);


			// Voc� vai trocar o n�mero de acordo com a ordem dos interroga��es. Por
			// exemplo: Voc� quer inserir o valor no primeiro interroga��o que seria o Name,
			// voc� coloca setString, pois o Name � um valor tipo String e vai colocar a
			// posi��o dele, que no caso � a primeira posi��o.
			st.setString(1, "Carl Purple");
			st.setString(2, "carl@gmail.com");
			st.setDate(3, new java.sql.Date(sdf.parse("22/04/1985").getTime()));
			st.setDouble(4, 3000.0);
			st.setInt(5, 4);

			// � uma opera��o que voc� vai alterar os dados. O resultado dessa opera��o � um
			// n�mero inteiro indicando quantas linhas foram alteradas
			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				// Retorna um objeto do tipo resultSet
				ResultSet rs = st.getGeneratedKeys();
				while (rs.next()) {
					// Vou colocar o valor 1 para indicar que quero o valor da primeira coluna
					int id = rs.getInt(1);  
					System.out.println("Done! Id = " + id);
				}
			} else {
				System.out.println("No Rows affected");
			}
		}
		catch (SQLException SQL) {
			System.out.println(SQL.getMessage());
		} 
		catch (ParseException P) {
			System.out.println(P.getMessage());
		} 
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
}
