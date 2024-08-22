package jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Aluno;

public class AlunoJDBC {

    public void salvar(Aluno a) throws SQLException, IOException {
        String sql = "INSERT INTO aluno (nome, sexo, dt_nasc) VALUES (?, ?, ?)";

        try (Connection con = db.getConexao();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, a.getNome());
            pst.setString(2, a.getSexo());
            Date dataSql = Date.valueOf(a.getDt_nasc());
            pst.setDate(3, dataSql);
            pst.executeUpdate();
            System.out.println("\nCadastro do aluno realizado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao salvar aluno: " + e.getMessage());
        }
    }

    public List<Aluno> listar() throws IOException {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM aluno";

        try (Connection con = db.getConexao();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setSexo(rs.getString("sexo"));
                aluno.setDt_nasc(rs.getDate("dt_nasc").toLocalDate());
                alunos.add(aluno);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar alunos: " + e.getMessage());
        }

        return alunos;
    }

    public void apagar(int id) throws IOException {
        String sql = "DELETE FROM aluno WHERE id = ?";

        try (Connection con = db.getConexao();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("\n\nAluno apagado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao apagar aluno: " + e.getMessage());
        }
    }

    public void alterar(Aluno a) throws IOException {
        String sql = "UPDATE aluno SET nome = ?, sexo = ?, dt_nasc = ? WHERE id = ?";

        try (Connection con = db.getConexao();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, a.getNome());
            pst.setString(2, a.getSexo());
            Date dataSql = Date.valueOf(a.getDt_nasc());
            pst.setDate(3, dataSql);
            pst.setInt(4, a.getId());
            pst.executeUpdate();
            System.out.println("\n\nAluno atualizado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao alterar aluno: " + e.getMessage());
        }
    }
}
