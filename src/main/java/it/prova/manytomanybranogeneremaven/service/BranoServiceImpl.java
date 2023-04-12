package it.prova.manytomanybranogeneremaven.service;

import java.util.List;

import javax.persistence.EntityManager;

import it.prova.manytomanybranogeneremaven.dao.EntityManagerUtil;
import it.prova.manytomanybranogeneremaven.dao.brano.BranoDAO;
import it.prova.manytomanybranogeneremaven.model.Brano;
import it.prova.manytomanybranogeneremaven.model.Genere;

public class BranoServiceImpl implements BranoService {

	private BranoDAO branoDAO;

	@Override
	public void setBranoDAO(BranoDAO branoDAO) {
		this.branoDAO = branoDAO;
	}

	@Override
	public List<Brano> listAll() throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			branoDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return branoDAO.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public Brano caricaSingoloElemento(Long id) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			branoDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return branoDAO.get(id);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public Brano caricaSingoloElementoEagerGeneri(Long id) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			branoDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return branoDAO.findByIdFetchingGeneri(id);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void aggiorna(Brano branoInstance) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			branoDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			branoDAO.update(branoInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void inserisciNuovo(Brano branoInstance) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			branoDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			branoDAO.insert(branoInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void rimuovi(Long idBrano) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			branoDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			branoDAO.delete(branoDAO.get(idBrano));

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void rimuoviMaPrimaScollegaGeneri(Long idBrano) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			branoDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			branoDAO.deleteBranoAndUnlinkGeneri(idBrano);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void aggiungiGenere(Brano branoInstance, Genere genereInstance) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			branoDAO.setEntityManager(entityManager);

			// 'attacco' alla sessione di hibernate i due oggetti
			// così jpa capisce che se risulta presente quel brano non deve essere inserito
			branoInstance = entityManager.merge(branoInstance);
			// attenzione che genereInstance deve essere già presente (lo verifica dall'id)
			// se così non è viene lanciata un'eccezione
			genereInstance = entityManager.merge(genereInstance);

			branoInstance.getGeneri().add(genereInstance);
			// l'update non viene richiamato a mano in quanto
			// risulta automatico, infatti il contesto di persistenza
			// rileva che branoInstance ora è dirty vale a dire che una sua
			// proprieta ha subito una modifica (vale anche per i Set ovviamente)
			// inoltre se risultano già collegati lo capisce automaticamente grazie agli id

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}

	}

	@Override
	public void creaECollegaBranoEGenere(Brano branoTransientInstance, Genere genereTransientInstance) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			branoDAO.setEntityManager(entityManager);

			// collego le due entità: questa cosa funziona grazie al fatto che ho
			// CascadeType.PERSIST, CascadeType.MERGE dentro l'owner della relazione (Brano in
			// questo caso)
			branoTransientInstance.getGeneri().add(genereTransientInstance);

			// ********************** IMPORTANTE ****************************
			// se io rimuovo i cascade dalla classe Brano, non funziona più e si deve prima
			// salvare il genere
			// (tramite genereDAO iniettando anch'esso) e poi
			// sfruttare i metodi addTo o removeFrom dentro Brano:
			// GenereDAO genereDAO = MyDaoFactory.getGenereDAOInstance();
			// genereDAO.setEntityManager(entityManager);
			// genereDAO.insert(genereTransientInstance);
			// branoTransientInstance.addToGeneri(genereTransientInstance);
			// in questo caso però se il genere è già presente non ne tiene conto e
			// inserirebbe duplicati, ma è logico
			// ****************************************************************

			// inserisco il brano ed automaticamente inserisce anche i generi
			// ma se tolgo i cascade non funziona: leggi commento in alto.
			branoDAO.insert(branoTransientInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}

	}

	@Override
	public List<String> estraiListaDescrizioneGeneriAssociateAdUnBrano(Long idBranoInput) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			branoDAO.setEntityManager(entityManager);

			return branoDAO.loadListaDescrizioneGeneriAssociateAdUnBrano(idBranoInput);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public List<Brano> listaBraniConGeneriConPiuDiDieciCaratteri() {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			branoDAO.setEntityManager(entityManager);

			return branoDAO.listAllBraniConGeneriConPiuDiDieciCaratteri();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	

	

}
