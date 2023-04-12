package it.prova.manytomanybranogeneremaven.service;

import java.time.LocalDate;
import java.util.List;

import it.prova.manytomanybranogeneremaven.dao.genere.GenereDAO;
import it.prova.manytomanybranogeneremaven.model.Brano;
import it.prova.manytomanybranogeneremaven.model.Genere;

public interface GenereService {

	public List<Genere> listAll() throws Exception;

	public Genere caricaSingoloElemento(Long id) throws Exception;

	public void aggiorna(Genere genereInstance) throws Exception;

	public void inserisciNuovo(Genere genereInstance) throws Exception;

	public void rimuovi(Long idGenere) throws Exception;

	public void aggiungiBrano(Genere genereInstance, Brano branoInstance) throws Exception;
	
	public Genere cercaPerDescrizione(String descrizione) throws Exception;

	public List<Genere> listaGeneriDiBraniPubblicatiTra (LocalDate primaData, LocalDate secondaData) throws Exception;

	
	// per injection
	public void setGenereDAO(GenereDAO genereDAO);

}
