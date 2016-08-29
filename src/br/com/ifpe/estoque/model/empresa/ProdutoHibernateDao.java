package br.com.ifpe.estoque.model.empresa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.ifpe.estoque.model.comum.HibernateDao;
import br.com.ifpe.estoque.model.produto.Produto;

public class ProdutoHibernateDao extends HibernateDao {
    
    public Class getClassEntidade() {
	
	return Produto.class;
    }
    
    public List<Produto> listar() {

	List<Produto> lista = null;

	EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
	EntityManager manager = factory.createEntityManager();
	lista = manager.createQuery("SELECT p FROM Produto p ORDER BY p.descricao").getResultList();
	manager.close();
	factory.close();

	return lista;
    }

    public List<Produto> pesquisar(String descricao, Integer idCategoria) {

	List<Produto> lista = null;
	Query query = null;

	EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
	EntityManager manager = factory.createEntityManager();

	if (!descricao.equals("") && idCategoria == null) {

	    query = manager.createQuery("SELECT p FROM Produto p WHERE p.descricao LIKE :paramDescricao ORDER BY p.descricao");
	    query.setParameter("paramDescricao", "%" + descricao + "%");

	} else if (descricao.equals("") && idCategoria != null) {

	    query = manager.createQuery("SELECT p FROM Produto p WHERE p.categoriaProduto.id = :paramIdCategoria ORDER BY p.descricao");
	    query.setParameter("paramIdCategoria", idCategoria);

	} else if (!descricao.equals("") && idCategoria != null) {

	    query = manager.createQuery("SELECT p FROM Produto p WHERE p.descricao LIKE :paramDescricao AND p.categoriaProduto.id = :paramIdCategoria ORDER BY p.descricao");
	    query.setParameter("paramDescricao", "%" + descricao + "%");
	    query.setParameter("paramIdCategoria", idCategoria);

	} else {

	    query = manager.createQuery("SELECT p FROM Produto p ORDER BY p.descricao");
	}

	lista = query.getResultList();
	manager.close();
	factory.close();

	return lista;
    }

}
