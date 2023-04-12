package it.prova.manytomanybranogeneremaven.service;

import java.util.List;

import it.prova.manytomanybranogeneremaven.dao.brano.BranoDAO;
import it.prova.manytomanybranogeneremaven.model.Brano;
import it.prova.manytomanybranogeneremaven.model.Genere;

public interface BranoService {

	public List<Brano> listAll() throws Exception;

	public Brano caricaSingoloElemento(Long id) throws Exception;

	public Brano caricaSingoloElementoEagerGeneri(Long id) throws Exception;

	public void aggiorna(Brano branoInstance) throws Exception;

	public void inserisciNuovo(Brano branoInstance) throws Exception;

	public void rimuovi(Long idBrano) throws Exception;

	public void rimuoviMaPrimaScollegaGeneri(Long idBrano) throws Exception;

	public void aggiungiGenere(Brano branoInstance, Genere genereInstance) throws Exception;

	public void creaECollegaBranoEGenere(Brano branoTransientInstance, Genere genereTransientInstance) throws Exception;

	public List<String> estraiListaDescrizioneGeneriAssociateAdUnBrano(Long idBranoInput) throws Exception;

	public List<Brano> listaBraniConGeneriConPiuDiDieciCaratteri();
	
	// per injection
	public void setBranoDAO(BranoDAO branoDAO);
	
//	voglio i generi di brani pubblicati tra due date scelte a piacere
//	voglio i brani legati ai generi con descrizioni che superano i 10 caratteri

}
