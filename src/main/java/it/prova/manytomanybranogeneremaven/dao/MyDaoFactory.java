package it.prova.manytomanybranogeneremaven.dao;

import it.prova.manytomanybranogeneremaven.dao.brano.BranoDAO;
import it.prova.manytomanybranogeneremaven.dao.brano.BranoDAOImpl;
import it.prova.manytomanybranogeneremaven.dao.genere.GenereDAO;
import it.prova.manytomanybranogeneremaven.dao.genere.GenereDAOImpl;

public class MyDaoFactory {

	private static BranoDAO branoDaoInstance = null;
	private static GenereDAO genereDaoInstance = null;

	public static BranoDAO getBranoDAOInstance() {
		if (branoDaoInstance == null)
			branoDaoInstance = new BranoDAOImpl();

		return branoDaoInstance;
	}

	public static GenereDAO getGenereDAOInstance() {
		if (genereDaoInstance == null)
			genereDaoInstance = new GenereDAOImpl();

		return genereDaoInstance;
	}

}
