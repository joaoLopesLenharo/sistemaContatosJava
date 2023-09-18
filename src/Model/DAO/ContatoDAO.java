package Model.DAO;

import Connection.ConnectionFactory;
import Model.Bean.Contato;
import Model.Bean.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ContatoDAO {

    private Connection conexao;

    public ContatoDAO() {
        this.conexao = ConnectionFactory.getConnection();
    }

    public void adiciona(Contato contato) {
        String sql = "INSERT INTO contatos (nome, email, rua, bairro, cep, cidade, numCasa, telefone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = this.conexao.prepareStatement(sql);
            stmt.setString(1, contato.getNome()); //Substitui o 1º ? pelo nome
            stmt.setString(2, contato.getEmail()); //Substitui o 2º ? pelo email
            stmt.setString(3, contato.getRua()); //Substitui o 3º ? pelo endereco
            stmt.setString(4, contato.getBairro());
            stmt.setString(5, contato.getCep());
            stmt.setString(6, contato.getCidade());
            stmt.setString(7, contato.getNumCasa());
            stmt.setString(8, contato.getTelefone());
            stmt.execute(); //Executa a instrução SQL
            stmt.close(); //Fecha o statement
            this.conexao.close(); //Fecha a conexão
            JOptionPane.showMessageDialog(null, "Contato adicionado com sucesso!", "Caixa de Diálogo", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha ao adicionar contato!\n Erro: " + e.getMessage(), "Caixa de Diálogo", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<Contato> getLista() {
        String sql = "SELECT * FROM contatos";
        List<Contato> contatos = new ArrayList<>();

        try {
            PreparedStatement stmt = this.conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Contato c = new Contato();
                c.setId(rs.getLong("id"));
                c.setNome(rs.getString("nome"));
                c.setEmail(rs.getString("email"));
                c.setRua(rs.getString("rua"));
                c.setBairro(rs.getString("bairro"));
                c.setCep(rs.getString("cep"));
                c.setCidade(rs.getString("cidade"));
                c.setNumCasa(rs.getString("numCasa"));
                c.setTelefone(rs.getString("telefone"));
                contatos.add(c);
            }

            rs.close();
            stmt.close();
            this.conexao.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha ao listar contatos!\nErro: " + e.getMessage());
        }
        return contatos;
    }

    public void atualiza(Contato contato) {
        try {
            String sql = "UPDATE contatos SET nome = ?, email = ?, rua = ?, bairro = ?, cep = ?, cidade = ?, numCasa = ?, telefone = ?  WHERE id = ?";
            PreparedStatement stmt = this.conexao.prepareStatement(sql);
            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getEmail());
            stmt.setString(3, contato.getRua());
            stmt.setString(4, contato.getBairro());
            stmt.setString(5, contato.getCep());
            stmt.setString(6, contato.getCidade());
            stmt.setString(7, contato.getNumCasa());
            stmt.setString(8, contato.getTelefone());
            stmt.setString(9, String.valueOf((int) contato.getId()));
            stmt.executeUpdate();
            stmt.close();
            this.conexao.close();
            JOptionPane.showMessageDialog(null, "Atualização efetuada com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha ao atualizar usuario!\nErro: " + e);
        }
    }

    public List<Contato> pesquisaNome(String nome) {
        String sql = "SELECT * FROM contatos WHERE nome LIKE ?";
        List<Contato> contatos = new ArrayList<>();
        nome = "%" + nome.replace(' ', '%') + "%";
        try {
            PreparedStatement stmt = this.conexao.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Contato c = new Contato();
                c.setId(rs.getLong("id"));
                c.setNome(rs.getString("nome"));
                c.setEmail(rs.getString("email"));
                c.setRua(rs.getString("rua"));
                c.setBairro(rs.getString("bairro"));
                c.setCep(rs.getString("cep"));
                c.setCidade(rs.getString("cidade"));
                c.setNumCasa(rs.getString("numCasa"));
                c.setTelefone(rs.getString("telefone"));
                contatos.add(c);
            }

            rs.close();
            stmt.close();
            this.conexao.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha ao pesquisar contatos!\nErro: " + e.getMessage());
        }
        return contatos;
    }

    public void deletaUsuario(long id) {
        String sql = "DELETE FROM contatos WHERE id = ?";
        try {
            PreparedStatement stmt = this.conexao.prepareStatement(sql);
            stmt.setLong(1, id);
            stmt.execute();
            stmt.close();
            this.conexao.close();
            JOptionPane.showMessageDialog(null, "Contato removido com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover contato!\nErro: " + e);
        }
    }
}
