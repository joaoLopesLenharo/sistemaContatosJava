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

public class UsuarioDAO {

    private Connection conexao;

    public UsuarioDAO() {
        this.conexao = ConnectionFactory.getConnection();
    }

    public void adiciona(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, usuario, senha) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = this.conexao.prepareStatement(sql);
            stmt.setString(1, usuario.getNome()); //Substitui o 1º ? pelo nome
            stmt.setString(2, usuario.getUsuario()); //Substitui o 2º ? pelo usuario
            stmt.setString(3, usuario.getSenha()); //Substitui o 3º ? pelo senha
            stmt.execute(); //Executa a instrução SQL
            stmt.close(); //Fecha o statement
            this.conexao.close(); //Fecha a conexão
            JOptionPane.showMessageDialog(null, "Usuário adicionado com sucesso!", "Caixa de Diálogo", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha ao adicionar usuário!\n Erro: " + e.getMessage(), "Caixa de Diálogo", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<Usuario> getLista() {
        String sql = "SELECT * FROM usuarios";
        List<Usuario> users = new ArrayList<>();

        try {
            PreparedStatement stmt = this.conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getLong("id"));
                u.setNome(rs.getString("nome"));
                u.setSenha(rs.getString("senha"));
                u.setUsuario(rs.getString("usuario"));
                users.add(u);
            }

            rs.close();
            stmt.close();
            this.conexao.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha ao listar contatos!\nErro: " + e.getMessage());
        }
        return users;
    }

    public List<Usuario> pesquisaNome(String nome) {
        String sql = "SELECT * FROM usuarios WHERE nome LIKE ?";
        List<Usuario> usuarios = new ArrayList<>();
        nome = "%" + nome.replace(' ', '%') + "%";
        try {
            PreparedStatement stmt = this.conexao.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getLong("id"));
                u.setNome(rs.getString("nome"));
                u.setUsuario(rs.getString("usuario"));
                u.setSenha(rs.getString("senha"));
                usuarios.add(u);
            }

            rs.close();
            stmt.close();
            this.conexao.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha ao pesquisar usuario!\nErro: " + e.getMessage());
        }
        return usuarios;
    }

    public void deletaUsuario(long id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
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

    public void atualiza(Usuario user) {
        try {
            String sql = "UPDATE usuarios SET nome = ?, usuario = ?, senha = ? WHERE id = ?";
            PreparedStatement stmt = this.conexao.prepareStatement(sql);
            stmt.setString(2, user.getUsuario());
            stmt.setString(1, user.getNome());
            stmt.setString(3, user.getSenha());
            stmt.setString(4, String.valueOf(user.getId()));
            stmt.executeUpdate();
            stmt.close();
            this.conexao.close();
            JOptionPane.showMessageDialog(null, "Atualização efetuada com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha ao atualizar usuario!\nErro: " + e);
        }
    }
}
