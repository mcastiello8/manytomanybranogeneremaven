package it.prova.manytomanybranogeneremaven.dao.brano;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.manytomanybranogeneremaven.model.Brano;

public class BranoDAOImpl implements BranoDAO {

	private EntityManager entityManager;

	@Override
	public List<Brano> list() throws Exception {
		return entityManager.createQuery("from Brano", Brano.class).getResultList();
	}

	@Override
	public Brano get(Long id) throws Exception {
		return entityManager.find(Brano.class, id);
	}

	@Override
	public void update(Brano input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		input = entityManager.merge(input);
	}

	@Override
	public void insert(Brano input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(input);
	}

	@Override
	public void delete(Brano input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(input));
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Brano findByIdFetchingGeneri(Long id) throws Exception {
		TypedQuery<Brano> query = entityManager
				.createQuery("select c FROM Brano c left join fetch c.generi g where c.id = :idBrano", Brano.class);
		query.setParameter("idBrano", id);
		return query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public List<String> loadListaDescrizioneGeneriAssociateAdUnBrano(Long idBranoInput) throws Exception {
		TypedQuery<String> query = entityManager.createQuery(
				"select g.descrizione FROM Genere g left join g.brani b where b.id = :idBrano", String.class);
		query.setParameter("idBrano", idBranoInput);
		return query.getResultList();
	}

	@Override
	public void deleteBranoAndUnlinkGeneri(Long idBranoInput) throws Exception {
		entityManager.createNativeQuery("delete from brano_genere c where c.brano_id = ?1").setParameter(1, idBranoInput)
				.executeUpdate();
		entityManager.createNativeQuery("delete from brano c where c.id = ?1").setParameter(1, idBranoInput).executeUpdate();
	}

	@Override
	public List<Brano> listAllBraniConGeneriConPiuDiDieciCaratteri() {
		return entityManager.createQuery("select distinct b from Brano b join b.generi g where length (g.descrizione) > 10", Brano.class).getResultList();
				
	}

	

}
