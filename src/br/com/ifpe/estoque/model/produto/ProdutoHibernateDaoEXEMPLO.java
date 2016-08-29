package br.com.ifpe.estoque.model.produto;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ProdutoHibernateDaoEXEMPLO {

    private static final String PERSISTENCE_UNIT = "estoque";

    public void salvar(Produto produto) {

	EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
	EntityManager manager = factory.createEntityManager();

	manager.getTransaction().begin();
	manager.persist(produto);
	manager.getTransaction().commit();

	manager.close();
	factory.close();
    }

    public void alterar(Produto produto) {

	EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
	EntityManager manager = factory.createEntityManager();

	manager.getTransaction().begin();
	manager.merge(produto);
	manager.getTransaction().commit();

	manager.close();
	factory.close();
    }

    public void remover(int id) {

	EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
	EntityManager manager = factory.createEntityManager();
	Produto produto = manager.find(Produto.class, id);

	manager.getTransaction().begin();
	manager.remove(produto);
	manager.getTransaction().commit();

	manager.close();
	factory.close();
    }

    public Produto buscarPorId(int id) {

	Produto obj = null;

	EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
	EntityManager manager = factory.createEntityManager();
	obj = manager.find(Produto.class, id);
	manager.close();
	factory.close();

	return obj;
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

	    query = manager
		    .createQuery("SELECT p FROM Produto p WHERE p.descricao LIKE :paramDescricao ORDER BY p.descricao");
	    query.setParameter("paramDescricao", "%" + descricao + "%");

	} else if (descricao.equals("") && idCategoria != null) {

	    query = manager.createQuery(
		    "SELECT p FROM Produto p WHERE p.categoriaProduto.id = :paramIdCategoria ORDER BY p.descricao");
	    query.setParameter("paramIdCategoria", idCategoria);

	} else if (!descricao.equals("") && idCategoria != null) {

	    query = manager.createQuery(
		    "SELECT p FROM Produto p WHERE p.descricao LIKE :paramDescricao AND p.categoriaProduto.id = :paramIdCategoria ORDER BY p.descricao");
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
