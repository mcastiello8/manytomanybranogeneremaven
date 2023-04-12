package it.prova.manytomanybranogeneremaven.dao.brano;

import java.util.List;

import it.prova.manytomanybranogeneremaven.dao.IBaseDAO;
import it.prova.manytomanybranogeneremaven.model.Brano;

public interface BranoDAO extends IBaseDAO<Brano>{
	
	public Brano findByIdFetchingGeneri(Long id) throws Exception;
	public List<String> loadListaDescrizioneGeneriAssociateAdUnBrano(Long idBranoInput) throws Exception;
	public void deleteBranoAndUnlinkGeneri(Long idBranoInput) throws Exception;
	
	public List<Brano> listAllBraniConGeneriConPiuDiDieciCaratteri();
	
	

}
