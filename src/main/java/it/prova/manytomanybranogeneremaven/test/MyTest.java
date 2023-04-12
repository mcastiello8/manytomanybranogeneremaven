package it.prova.manytomanybranogeneremaven.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import it.prova.manytomanybranogeneremaven.dao.EntityManagerUtil;
import it.prova.manytomanybranogeneremaven.model.Brano;
import it.prova.manytomanybranogeneremaven.model.Genere;
import it.prova.manytomanybranogeneremaven.service.BranoService;
import it.prova.manytomanybranogeneremaven.service.GenereService;
import it.prova.manytomanybranogeneremaven.service.MyServiceFactory;

public class MyTest {

	public static void main(String[] args) {
		BranoService branoServiceInstance = MyServiceFactory.getBranoServiceInstance();
		GenereService genereServiceInstance = MyServiceFactory.getGenereServiceInstance();

		try {

			System.out.println("In tabella Genere ci sono " + genereServiceInstance.listAll().size() + " elementi.");
			System.out.println("In tabella Brano ci sono " + branoServiceInstance.listAll().size() + " elementi.");
			System.out.println(
					"**************************** inizio batteria di test ********************************************");
			System.out.println(
					"*************************************************************************************************");

//			testInserimentoNuovoBrano(branoServiceInstance);

//			testModificaECheckDateBrano(branoServiceInstance);

//			testInserimentoNuovoGenereERicercaPerDescrizione(genereServiceInstance);

//			testCollegaGenereABrano(branoServiceInstance, genereServiceInstance);

//			testCreazioneECollegamentoBranoInUnSoloColpo(branoServiceInstance, genereServiceInstance);

//			testEstraiListaDescrizioneGeneriAssociateAdUnBrano(branoServiceInstance, genereServiceInstance);

			// *********************************************************************************
			// RIMUOVIAMO UN BRANO E VEDIAMO COSA ACCADE AI GENERI
			// ********************************
			// per eseguire questo test dobbiamo prendere un brano esistente collegato a due
			// generi
			// il risultato atteso è la rimozione dalla tabella brano, la rimozione dalla
			// tabella
			// di legame lasciando inalterate le voci nella tabella genere. Tutto ciò
			// a prescindere della presenza dei Cascade (funziona in quanto brano risulta
			// owner della relatzione). Se mettiamo CascadeType.ALL o
			// REMOVE...
			// DISASTRO!!! Perché prova a rimuovere anche i generi collegati!!!
			// *********************************************************************************
//			testRimozioneBranoECheckGeneri(branoServiceInstance, genereServiceInstance);

			// questo usa direttamente sql nativo che in moltissimi casi risulta la
			// strategia migliore,
			// più performante, esente da problemi di query sovrabbondanti
//			testRimozioneBranoECheckGeneriAttraversoNativeSql(branoServiceInstance, genereServiceInstance);

//			testListaGeneriDiBraniPubblicatiTra(genereServiceInstance);
			
//			testListaBraniConGeneriConPiuDiDieciCaratteri(branoServiceInstance);

			// TODO: TESTARE TUTTO IL CRUD

			System.out.println(
					"****************************** fine batteria di test ********************************************");
			System.out.println(
					"*************************************************************************************************");
			System.out.println("In tabella Genere ci sono " + genereServiceInstance.listAll().size() + " elementi.");
			System.out.println("In tabella Brano ci sono " + branoServiceInstance.listAll().size() + " elementi.");

		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			// questa è necessaria per chiudere tutte le connessioni quindi rilasciare il
			// main
			EntityManagerUtil.shutdown();
		}

	}

	private static void testInserimentoNuovoBrano(BranoService branoServiceInstance) throws Exception {
		System.out.println(".......testInserimentoNuovoBrano inizio.............");

		Brano branoInstance = new Brano("Revenge", "xxxtentacion", LocalDate.parse("2017-08-25"));
		branoServiceInstance.inserisciNuovo(branoInstance);
		if (branoInstance.getId() == null)
			throw new RuntimeException("testInserimentoNuovoBrano fallito ");

		System.out.println(".......testInserimentoNuovoBrano fine: PASSED.............");
	}

	private static void testModificaECheckDateBrano(BranoService branoServiceInstance) throws Exception {
		System.out.println(".......testModificaECheckDateBrano inizio.............");

		Brano branoInstance = new Brano("San Siro", "Franco122", LocalDate.parse("2019-01-25"));
		branoServiceInstance.inserisciNuovo(branoInstance);
		if (branoInstance.getId() == null)
			throw new RuntimeException("testModificaECheckDateBrano fallito ");

		// ora mi salvo da parte le date di creazione ed update
		LocalDateTime createDateTimeIniziale = branoInstance.getCreateDateTime();
		LocalDateTime updateDateTimeIniziale = branoInstance.getUpdateDateTime();

		// **************************************************************************************************
		// **************************************************************************************************
		// all'inizio DOVREBBERO essere uguali, infatti a volte non lo sono per
		// questione di 10^-4 secondi
		// soluzione: riprovare!!! Se diventa sistematico commentare le due righe
		// successive
//		if (!createDateTimeIniziale.equals(updateDateTimeIniziale))
//			throw new RuntimeException("testModificaECheckDateBrano fallito: le date non coincidono ");
		// **************************************************************************************************
		// **************************************************************************************************

		// ora modifico il record
		branoInstance.setAutore("Franco126");
		branoServiceInstance.aggiorna(branoInstance);

		// se la nuova data aggiornamento risulta precedente a quella iniziale: errore
		if (branoInstance.getUpdateDateTime().isAfter(updateDateTimeIniziale))
			throw new RuntimeException("testModificaECheckDateBrano fallito: le date di modifica sono disallineate ");

		// la data creazione deve essere uguale a quella di prima
		if (!branoInstance.getCreateDateTime().equals(createDateTimeIniziale))
			throw new RuntimeException("testModificaECheckDateBrano fallito: la data di creazione è cambiata ");

		System.out.println(".......testModificaECheckDateBrano fine: PASSED.............");
	}

	private static void testInserimentoNuovoGenereERicercaPerDescrizione(GenereService genereServiceInstance)
			throws Exception {
		System.out.println(".......testInserimentoNuovoGenereERicercaPerDescrizione inizio.............");

		// creo una cosa del tipo rock1634630578974 così ogni volta sarà diverso
		String descrizioneGenere = "Indie";
		Genere nuovoGenere = new Genere(descrizioneGenere);
		genereServiceInstance.inserisciNuovo(nuovoGenere);
		if (nuovoGenere.getId() == null)
			throw new RuntimeException(
					"testInserimentoNuovoGenereERicercaPerDescrizione fallito: genere non inserito ");

		if (genereServiceInstance.cercaPerDescrizione(descrizioneGenere) == null)
			throw new RuntimeException(
					"testInserimentoNuovoGenereERicercaPerDescrizione fallito: cercaPerDescrizione di genere non ha ritrovato elementi ");

		System.out.println(".......testInserimentoNuovoGenereERicercaPerDescrizione fine: PASSED.............");
	}

	private static void testCollegaGenereABrano(BranoService branoServiceInstance, GenereService genereServiceInstance)
			throws Exception {
		System.out.println(".......testCollegaGenereABrano inizio.............");

		// inserisco un brano
		Brano branoInstance = new Brano("Rockstar", "Sfera Ebbasta", LocalDate.parse("2018-01-19"));
		branoServiceInstance.inserisciNuovo(branoInstance);
		if (branoInstance.getId() == null)
			throw new RuntimeException("testCollegaGenereABrano fallito: inserimento brano non riuscito ");

		// inserisco un genere
		Genere nuovoGenere = new Genere("Trap");
		genereServiceInstance.inserisciNuovo(nuovoGenere);
		if (nuovoGenere.getId() == null)
			throw new RuntimeException("testCollegaGenereABrano fallito: genere non inserito ");

		// collego
		branoServiceInstance.aggiungiGenere(branoInstance, nuovoGenere);

		// ricarico eager per forzare il test
		Brano branoReloaded = branoServiceInstance.caricaSingoloElementoEagerGeneri(branoInstance.getId());
		if (branoReloaded.getGeneri().isEmpty())
			throw new RuntimeException("testCollegaGenereABrano fallito: genere non collegato ");

		System.out.println(".......testCollegaGenereABrano fine: PASSED.............");
	}

	private static void testCreazioneECollegamentoBranoInUnSoloColpo(BranoService branoServiceInstance,
			GenereService genereServiceInstance) throws Exception {
		System.out.println(".......testCreazioneECollegamentoBranoInUnSoloColpo inizio.............");

		Brano branoInstanceX = new Brano("Cenere", "Lazza", LocalDate.parse("2023-02-09"));
		Genere genereX = new Genere("Pop");
		branoServiceInstance.creaECollegaBranoEGenere(branoInstanceX, genereX);

		if (branoInstanceX.getId() == null)
			throw new RuntimeException("testCreazioneECollegamentoBranoInUnSoloColpo fallito: brano non inserito ");

		if (genereX.getId() == null)
			throw new RuntimeException("testCreazioneECollegamentoBranoInUnSoloColpo fallito: genere non inserito ");

		// ricarico eager per forzare il test
		Brano branoReloaded = branoServiceInstance.caricaSingoloElementoEagerGeneri(branoInstanceX.getId());
		if (branoReloaded.getGeneri().isEmpty())
			throw new RuntimeException(
					"testCreazioneECollegamentoBranoInUnSoloColpo fallito: genere e brano non collegati ");

		System.out.println(".......testCreazioneECollegamentoBranoInUnSoloColpo fine: PASSED.............");
	}

	private static void testEstraiListaDescrizioneGeneriAssociateAdUnBrano(BranoService branoServiceInstance,
			GenereService genereServiceInstance) throws Exception {
		System.out.println(".......testEstraiListaDescrizioneGeneriAssociateAdUnBrano inizio.............");

		// creo un brano e tre generi
		Brano branoInstanceX = new Brano("BODY PARTS", "Marracash", LocalDate.parse("2019-10-31"));
		branoServiceInstance.inserisciNuovo(branoInstanceX);
		Genere genere1 = new Genere("Rap");
		genereServiceInstance.inserisciNuovo(genere1);
		Genere genere2 = new Genere("Hip Hop" + 1);
		genereServiceInstance.inserisciNuovo(genere2);
		Genere genere3 = new Genere("Alternative Rap" + 2);
		genereServiceInstance.inserisciNuovo(genere3);
		branoServiceInstance.aggiungiGenere(branoInstanceX, genere1);
		branoServiceInstance.aggiungiGenere(branoInstanceX, genere2);
		branoServiceInstance.aggiungiGenere(branoInstanceX, genere3);

		// ricarico eager per forzare il test
		Brano branoReloaded = branoServiceInstance.caricaSingoloElementoEagerGeneri(branoInstanceX.getId());
		if (branoReloaded.getGeneri().size() != 3)
			throw new RuntimeException(
					"testEstraiListaDescrizioneGeneriAssociateAdUnBrano fallito: 2 generi e brano non collegati ");

		// vediamo se estrae 3 descrizioni
		List<String> listaGeneriAssociatiAlBranoReloaded = branoServiceInstance
				.estraiListaDescrizioneGeneriAssociateAdUnBrano(branoReloaded.getId());
		if (listaGeneriAssociatiAlBranoReloaded == null || listaGeneriAssociatiAlBranoReloaded.isEmpty()
				|| listaGeneriAssociatiAlBranoReloaded.size() != 3)
			throw new RuntimeException(
					"testEstraiListaDescrizioneGeneriAssociateAdUnBrano fallito: nessuna descrizione caricata ");

		// adesso un test più stringente
		for (String descrizioneItem : Arrays.asList(genere1.getDescrizione(), genere2.getDescrizione(),
				genere3.getDescrizione())) {
			if (!listaGeneriAssociatiAlBranoReloaded.contains(descrizioneItem))
				throw new RuntimeException("testEstraiListaDescrizioneGeneriAssociateAdUnBrano fallito: descrizione "
						+ descrizioneItem + " non contenuta nella lista estratta");
		}

		System.out.println(".......testEstraiListaDescrizioneGeneriAssociateAdUnBrano fine: PASSED.............");
	}

	private static void testRimozioneBranoECheckGeneri(BranoService branoServiceInstance,
			GenereService genereServiceInstance) throws Exception {
		System.out.println(".......testRimozioneBranoECheckGeneri inizio.............");

		// creo un brano e due generi
		Brano branoInstanceX = new Brano("Punkabbestia", "NASKA", LocalDate.parse("2022-08-10"));
		branoServiceInstance.inserisciNuovo(branoInstanceX);
		Genere genere1 = new Genere("Rock");
		genereServiceInstance.inserisciNuovo(genere1);
		Genere genere2 = new Genere("Punk");
		genereServiceInstance.inserisciNuovo(genere2);
		branoServiceInstance.aggiungiGenere(branoInstanceX, genere1);
		branoServiceInstance.aggiungiGenere(branoInstanceX, genere2);

		// ricarico eager per forzare il test
		Brano branoReloaded = branoServiceInstance.caricaSingoloElementoEagerGeneri(branoInstanceX.getId());
		if (branoReloaded.getGeneri().size() != 2)
			throw new RuntimeException("testRimozioneBranoECheckGeneri fallito: 2 generi e brano non collegati ");

		// rimuovo. Da notare che funziona anche se tolgo i cascade da Brano
		// nel log troverò, infatti:
		// Hibernate: delete from brano_genere where brano_id=?
		// Hibernate: delete from brano where id=?
		// cioè prima rimuove dalla tabella di legame e poi il brano vero e proprio
		branoServiceInstance.rimuovi(branoReloaded.getId());

		// ricarico
		Brano branoSupposedToBeRemoved = branoServiceInstance.caricaSingoloElementoEagerGeneri(branoInstanceX.getId());
		if (branoSupposedToBeRemoved != null)
			throw new RuntimeException("testRimozioneBranoECheckGeneri fallito: rimozione non avvenuta ");

		System.out.println(".......testRimozioneBranoECheckGeneri fine: PASSED.............");
	}

	private static void testRimozioneBranoECheckGeneriAttraversoNativeSql(BranoService branoServiceInstance,
			GenereService genereServiceInstance) throws Exception {
		System.out.println(".......testRimozioneBranoECheckGeneriAttraversoNativeSql inizio.............");

		// creo un brano e due generi
		Brano branoInstanceX = new Brano("After hours", "TheWeeknd", LocalDate.parse("2020-03-20"));
		branoServiceInstance.inserisciNuovo(branoInstanceX);
		Genere genere1 = new Genere("Alternative Rock");
		genereServiceInstance.inserisciNuovo(genere1);
		Genere genere2 = new Genere("Alternative Pop");
		genereServiceInstance.inserisciNuovo(genere2);
		branoServiceInstance.aggiungiGenere(branoInstanceX, genere1);
		branoServiceInstance.aggiungiGenere(branoInstanceX, genere2);

		// ricarico eager per forzare il test
		Brano branoReloaded = branoServiceInstance.caricaSingoloElementoEagerGeneri(branoInstanceX.getId());
		if (branoReloaded.getGeneri().size() != 2)
			throw new RuntimeException(
					"testRimozioneBranoECheckGeneriAttraversoNativeSql fallito: 2 generi e brano non collegati ");

		// rimuovo. Da notare che nel log troverò:
		// Hibernate: delete from brano_genere c where c.brano_id = ?
		// Hibernate: delete from brano c where c.id = ?
		branoServiceInstance.rimuoviMaPrimaScollegaGeneri(branoReloaded.getId());

		// ricarico
		Brano branoSupposedToBeRemoved = branoServiceInstance.caricaSingoloElementoEagerGeneri(branoInstanceX.getId());
		if (branoSupposedToBeRemoved != null)
			throw new RuntimeException(
					"testRimozioneBranoECheckGeneriAttraversoNativeSql fallito: rimozione non avvenuta ");

		System.out.println(".......testRimozioneBranoECheckGeneriAttraversoNativeSql fine: PASSED.............");
	}

//	voglio i generi di brani pubblicati tra due date scelte a piacere
	private static void testListaGeneriDiBraniPubblicatiTra(GenereService genereServiceInstance) throws Exception {
		System.out.println(".......testListaGeneriDiBraniPubblicatiTra inizio.............");

		LocalDate primaData = LocalDate.parse("2017-01-01");
		LocalDate secondaData = LocalDate.parse("2020-12-31");

		List<Genere> result = genereServiceInstance.listaGeneriDiBraniPubblicatiTra(primaData, secondaData);

		System.out.println(result);
		System.out.println(".......testListaGeneriDiBraniPubblicatiTra fine: PASSED.............");

	}

//	voglio i brani legati ai generi con descrizioni che superano i 10 caratteri
	private static void testListaBraniConGeneriConPiuDiDieciCaratteri(BranoService branoServiceInstance) throws Exception {
		System.out.println(".......testListaBraniConGeneriConPiuDiDieciCaratteri inizio.............");

		List<Brano> result = branoServiceInstance.listaBraniConGeneriConPiuDiDieciCaratteri();

		System.out.println(result);
		System.out.println(".......testListaBraniConGeneriConPiuDiDieciCaratteri fine: PASSED.............");

	}
}
